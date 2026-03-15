package space.lasf.associados.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;
import space.lasf.associados.core.util.ObjectsValidator;
import space.lasf.associados.domain.model.Associado;
import space.lasf.associados.domain.repository.AssociadoRepository;
import space.lasf.associados.dto.AssociadoDto;
import space.lasf.associados.dto.VotoDto;
import space.lasf.associados.http.SessaoClient;
import space.lasf.associados.service.impl.AssociadoServiceImpl;

@ExtendWith(MockitoExtension.class)
class AssociadoServiceTest {

    @Mock
    private AssociadoRepository repository;

    @Mock
    private ObjectsValidator<Associado> validator;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private SessaoClient sessaoClient;

    @InjectMocks
    private AssociadoServiceImpl service;

    private void wireAutowiredFields() {
        ReflectionTestUtils.setField(service, "modelMapper", modelMapper);
        ReflectionTestUtils.setField(service, "sessaoClient", sessaoClient);
    }

    @Test
    void criarAssociadoDeveSalvarQuandoEmailValido() {
        wireAutowiredFields();
        AssociadoDto input = new AssociadoDto();
        input.setNome("Joao");
        input.setEmail("joao@example.com");
        Associado entity = Associado.builder()
                .id(10L)
                .nome("Joao")
                .email("joao@example.com")
                .build();
        Associado saved = Associado.builder()
                .id(11L)
                .nome("Joao")
                .email("joao@example.com")
                .build();
        AssociadoDto output = new AssociadoDto();
        output.setId(11L);
        output.setNome("Joao");
        output.setEmail("joao@example.com");

        when(modelMapper.map(input, Associado.class)).thenReturn(entity);
        when(validator.validate(entity)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(saved);
        when(modelMapper.map(saved, AssociadoDto.class)).thenReturn(output);

        AssociadoDto result = service.criarAssociado(input);

        assertNotNull(result);
        assertEquals(11L, result.getId());
        verify(repository).save(entity);
    }

    @Test
    void criarAssociadoDeveLancarErroQuandoEmailInvalido() {
        wireAutowiredFields();
        AssociadoDto input = new AssociadoDto();
        input.setNome("Joao");
        input.setEmail("email-invalido");
        Associado entity =
                Associado.builder().nome("Joao").email("email-invalido").build();
        when(modelMapper.map(input, Associado.class)).thenReturn(entity);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> service.criarAssociado(input));

        assertEquals("Email inválido: email-invalido", ex.getMessage());
        verify(repository, never()).save(any(Associado.class));
    }

    @Test
    void buscarAssociadoPorIdDeveAnexarVotos() {
        wireAutowiredFields();
        Long associadoId = 22L;
        Associado entity = Associado.builder()
                .id(associadoId)
                .nome("Maria")
                .email("maria@example.com")
                .build();
        AssociadoDto dto = new AssociadoDto();
        dto.setId(associadoId);
        dto.setNome("Maria");
        dto.setEmail("maria@example.com");
        Map<Long, VotoDto> votos = Map.of(
                100L,
                VotoDto.builder().id(1L).idAssociado(associadoId).idSessao(100L).build());

        when(repository.findById(associadoId)).thenReturn(Optional.of(entity));
        when(modelMapper.map(entity, AssociadoDto.class)).thenReturn(dto);
        when(sessaoClient.pesquisarVotosAssociado(associadoId)).thenReturn(votos);

        AssociadoDto result = service.buscarAssociadoPorId(associadoId);

        assertEquals(1, result.getVotacaoAssociado().size());
        assertEquals(100L, result.getVotacaoAssociado().keySet().iterator().next());
    }

    @Test
    void removerAssociadoDeveLancarErroQuandoNaoEncontrado() {
        wireAutowiredFields();
        Long associadoId = 999L;
        when(repository.findById(eq(associadoId))).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.removerAssociado(associadoId));
    }

    @Test
    void removerAssociadoDeveExecutarQuandoEncontrado() {
        wireAutowiredFields();
        Long associadoId = 5L;
        Associado entity = Associado.builder().id(associadoId).nome("Teste").email("t@t.com").build();
        when(repository.findById(associadoId)).thenReturn(Optional.of(entity));

        service.removerAssociado(associadoId);

        verify(repository).deleteById(associadoId);
    }

    @Test
    void alterarAssociadoDeveSalvarQuandoEmailValido() {
        wireAutowiredFields();
        AssociadoDto input = new AssociadoDto();
        input.setId(7L);
        input.setNome("Carlos");
        input.setEmail("carlos@example.com");
        Associado entity = Associado.builder().id(7L).nome("Carlos").email("carlos@example.com").build();
        Associado saved = Associado.builder().id(7L).nome("Carlos").email("carlos@example.com").build();
        AssociadoDto output = new AssociadoDto();
        output.setId(7L);

        when(modelMapper.map(input, Associado.class)).thenReturn(entity);
        when(validator.validate(entity)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(saved);
        when(modelMapper.map(saved, AssociadoDto.class)).thenReturn(output);

        AssociadoDto result = service.alterarAssociado(input);

        assertNotNull(result);
        verify(repository).save(entity);
    }

    @Test
    void alterarAssociadoDeveLancarErroQuandoEmailInvalido() {
        wireAutowiredFields();
        AssociadoDto input = new AssociadoDto();
        input.setNome("Carlos");
        input.setEmail("invalido");
        Associado entity = Associado.builder().nome("Carlos").email("invalido").build();
        when(modelMapper.map(input, Associado.class)).thenReturn(entity);

        assertThrows(IllegalArgumentException.class, () -> service.alterarAssociado(input));
        verify(repository, never()).save(any(Associado.class));
    }

    @Test
    void buscarAssociadoPorEmailDeveRetornarQuandoValido() {
        wireAutowiredFields();
        String email = "ana@example.com";
        Associado entity = Associado.builder().id(3L).nome("Ana").email(email).build();
        AssociadoDto dto = new AssociadoDto();
        dto.setId(3L);

        when(repository.findByEmail(email)).thenReturn(Optional.of(entity));
        when(modelMapper.map(entity, AssociadoDto.class)).thenReturn(dto);

        AssociadoDto result = service.buscarAssociadoPorEmail(email);

        assertNotNull(result);
        assertEquals(3L, result.getId());
    }

    @Test
    void buscarAssociadoPorEmailDeveLancarQuandoEmailInvalido() {
        wireAutowiredFields();
        assertThrows(IllegalArgumentException.class, () -> service.buscarAssociadoPorEmail("semdominio"));
    }

    @Test
    void buscarTodosAssociadosDeveRetornarLista() {
        wireAutowiredFields();
        Associado a1 = Associado.builder().id(1L).nome("X").email("x@x.com").build();
        Associado a2 = Associado.builder().id(2L).nome("Y").email("y@y.com").build();
        AssociadoDto d1 = new AssociadoDto();
        d1.setId(1L);
        AssociadoDto d2 = new AssociadoDto();
        d2.setId(2L);

        when(repository.findAll()).thenReturn(List.of(a1, a2));
        when(modelMapper.map(a1, AssociadoDto.class)).thenReturn(d1);
        when(modelMapper.map(a2, AssociadoDto.class)).thenReturn(d2);

        List<AssociadoDto> result = service.buscarTodosAssociados();

        assertEquals(2, result.size());
    }

    @Test
    void buscarAssociadosPorNomeDeveRetornarFiltrado() {
        wireAutowiredFields();
        Associado a = Associado.builder().id(1L).nome("Pedro").email("pedro@example.com").build();
        AssociadoDto dto = new AssociadoDto();
        dto.setId(1L);

        when(repository.findByNomeContaining("Pedro")).thenReturn(List.of(a));
        when(modelMapper.map(a, AssociadoDto.class)).thenReturn(dto);

        List<AssociadoDto> result = service.buscarAssociadosPorNome("Pedro");

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }
}
