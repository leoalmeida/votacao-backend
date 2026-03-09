package space.lasf.sessoes.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import space.lasf.sessoes.core.exception.BusinessException;
import space.lasf.sessoes.core.util.ObjectsValidator;
import space.lasf.sessoes.domain.model.Voto;
import space.lasf.sessoes.domain.model.VotoOpcao;
import space.lasf.sessoes.domain.repository.VotoRepository;
import space.lasf.sessoes.dto.VotoDto;
import space.lasf.sessoes.service.SessaoService;

@ExtendWith(MockitoExtension.class)
class VotoServiceImplTest {

    @Mock
    private ObjectsValidator<Voto> validadorDeVoto;

    @Mock
    private VotoRepository repository;

    @Mock
    private SessaoService sessaoService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private VotoServiceImpl service;

    @Test
    void criarVotoDeveSalvarQuandoSessaoAberta() {
        VotoDto input = VotoDto.builder()
                .idAssociado(1L)
                .idSessao(2L)
                .opcao(VotoOpcao.SIM)
                .build();
        Voto entity =
                Voto.builder().idAssociado(1L).idSessao(2L).opcao(VotoOpcao.SIM).build();
        Voto saved = Voto.builder()
                .id(9L)
                .idAssociado(1L)
                .idSessao(2L)
                .opcao(VotoOpcao.SIM)
                .build();
        VotoDto output = VotoDto.builder()
                .id(9L)
                .idAssociado(1L)
                .idSessao(2L)
                .opcao(VotoOpcao.SIM)
                .build();

        when(modelMapper.map(input, Voto.class)).thenReturn(entity);
        when(validadorDeVoto.validate(entity)).thenReturn(entity);
        when(sessaoService.isSessaoAbertaParaVotacao(2L)).thenReturn(true);
        when(repository.save(entity)).thenReturn(saved);
        when(modelMapper.map(saved, VotoDto.class)).thenReturn(output);

        VotoDto result = service.criarVoto(input);

        assertNotNull(result);
        assertEquals(9L, result.getId());
        verify(repository).save(entity);
    }

    @Test
    void criarVotoDeveLancarErroQuandoSessaoFechada() {
        VotoDto input = VotoDto.builder()
                .idAssociado(1L)
                .idSessao(2L)
                .opcao(VotoOpcao.NAO)
                .build();
        Voto entity =
                Voto.builder().idAssociado(1L).idSessao(2L).opcao(VotoOpcao.NAO).build();

        when(modelMapper.map(input, Voto.class)).thenReturn(entity);
        when(validadorDeVoto.validate(entity)).thenReturn(entity);
        when(sessaoService.isSessaoAbertaParaVotacao(2L)).thenReturn(false);

        assertThrows(BusinessException.class, () -> service.criarVoto(input));
        verify(repository, never()).save(any(Voto.class));
    }

    @Test
    void buscarVotoPorIdDeveRetornarDto() {
        Voto entity = Voto.builder()
                .id(7L)
                .idAssociado(1L)
                .idSessao(2L)
                .opcao(VotoOpcao.SIM)
                .build();
        VotoDto dto = VotoDto.builder()
                .id(7L)
                .idAssociado(1L)
                .idSessao(2L)
                .opcao(VotoOpcao.SIM)
                .build();

        when(repository.findById(7L)).thenReturn(Optional.of(entity));
        when(modelMapper.map(entity, VotoDto.class)).thenReturn(dto);

        VotoDto result = service.buscarVotoPorId(7L);

        assertEquals(7L, result.getId());
    }

    @Test
    void buscarVotosSessaoDeveRetornarListaMapeada() {
        Voto voto1 = Voto.builder().id(1L).idSessao(10L).build();
        Voto voto2 = Voto.builder().id(2L).idSessao(10L).build();
        when(repository.findVotosByIdSessao(10L)).thenReturn(List.of(voto1, voto2));
        when(modelMapper.map(voto1, VotoDto.class))
                .thenReturn(VotoDto.builder().id(1L).idSessao(10L).build());
        when(modelMapper.map(voto2, VotoDto.class))
                .thenReturn(VotoDto.builder().id(2L).idSessao(10L).build());

        List<VotoDto> result = service.buscarVotosSessao(10L);

        assertEquals(2, result.size());
    }

    @Test
    void buscarVotosAssociadoDeveRetornarMapaPorSessao() {
        Voto voto = Voto.builder().id(1L).idAssociado(4L).idSessao(77L).build();
        VotoDto dto = VotoDto.builder().id(1L).idAssociado(4L).idSessao(77L).build();

        when(repository.findVotosByIdAssociado(4L)).thenReturn(Stream.of(voto));
        when(modelMapper.map(voto, VotoDto.class)).thenReturn(dto);

        Map<Long, VotoDto> result = service.buscarVotosAssociado(4L);

        assertEquals(1, result.size());
        assertEquals(77L, result.keySet().iterator().next());
    }

    @Test
    void removerVotoDeveLancarErroQuandoNaoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.removerVoto(99L));
    }
}
