package space.lasf.pautas.service;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.modelmapper.ModelMapper;
import org.mockito.junit.jupiter.MockitoExtension;

import space.lasf.pautas.core.exception.BusinessException;
import space.lasf.pautas.core.util.ObjectsValidator;
import space.lasf.pautas.domain.model.Pauta;
import space.lasf.pautas.domain.repository.PautaRepository;
import space.lasf.pautas.dto.PautaDto;
import space.lasf.pautas.dto.SessaoDto;
import space.lasf.pautas.http.SessaoClient;
import space.lasf.pautas.service.impl.PautaServiceImpl;

@ExtendWith(MockitoExtension.class)
class PautaServiceTest {

    @Mock
    private PautaRepository repository;

    @Mock
    private ObjectsValidator<Pauta> validator;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private SessaoClient sessaoClient;

    @InjectMocks
    private PautaServiceImpl service;

    @Test
    void testeCriarPautaComSessao() {
        PautaDto input = PautaDto.builder().nome("Pauta 1").descricao("Descricao").build();
        Pauta entityIn = Pauta.builder().nome("Pauta 1").descricao("Descricao").build();
        Pauta entityOut = Pauta.builder().id(10L).nome("Pauta 1").descricao("Descricao").build();
        SessaoDto sessao = SessaoDto.builder().id(88L).build();
        PautaDto dtoOut = PautaDto.builder().id(10L).nome("Pauta 1").descricao("Descricao").build();

        when(modelMapper.map(input, Pauta.class)).thenReturn(entityIn);
        when(validator.validate(entityIn)).thenReturn(entityIn);
        when(repository.save(entityIn)).thenReturn(entityOut);
        when(modelMapper.map(entityOut, PautaDto.class)).thenReturn(dtoOut);
        when(sessaoClient.criarSessao(dtoOut, true)).thenReturn(sessao);
        when(modelMapper.map(dtoOut, PautaDto.class)).thenReturn(dtoOut);

        PautaDto result = service.criarPauta(input, true);

        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals(88L, result.getSessaoDto().getId());
        verify(repository).save(entityIn);
        verify(repository).save(entityOut);
    }

    @Test
    void testeBuscarPautaPorIdQuandoNaoEncontrada() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> service.buscarPautaPorId(99L));
    }

    @Test
    void testeBuscarPautaPorIdComDadosDaSessao() {
        Pauta entity = Pauta.builder().id(7L).idSessao(44L).nome("Pauta").build();
        PautaDto dto = PautaDto.builder().id(7L).nome("Pauta").build();
        SessaoDto sessao = SessaoDto.builder().id(44L).build();

        when(repository.findById(7L)).thenReturn(Optional.of(entity));
        when(sessaoClient.buscarSessaoPorId(44L)).thenReturn(sessao);
        when(modelMapper.map(entity, PautaDto.class)).thenReturn(dto);

        PautaDto result = service.buscarPautaPorId(7L);

        assertEquals(44L, result.getSessaoDto().getId());
        verify(sessaoClient).buscarSessaoPorId(44L);
    }

    @Test
    void testeAlterarPautaQuandoNaoExiste() {
        PautaDto input = PautaDto.builder().id(5L).nome("Nova").build();
        Pauta entity = Pauta.builder().id(5L).nome("Nova").build();

        when(modelMapper.map(input, Pauta.class)).thenReturn(entity);
        when(validator.validate(entity)).thenReturn(entity);
        when(repository.findById(5L)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> service.alterarPauta(5L, input));
        verify(repository, never()).save(any(Pauta.class));
    }
}
