package space.lasf.pautas.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
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
        PautaDto input = new PautaDto();
        input.setNome("Pauta 1");
        input.setDescricao("Descricao");
        Pauta entityIn = Pauta.builder().nome("Pauta 1").descricao("Descricao").build();
        Pauta entityOut =
                Pauta.builder().id(10L).nome("Pauta 1").descricao("Descricao").build();
        SessaoDto sessao = new SessaoDto();
        sessao.setId(88L);
        PautaDto dtoOut = new PautaDto();
        dtoOut.setId(10L);
        dtoOut.setNome("Pauta 1");
        dtoOut.setDescricao("Descricao");

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
        PautaDto dto = new PautaDto();
        dto.setId(7L);
        dto.setNome("Pauta");
        SessaoDto sessao = new SessaoDto();
        sessao.setId(44L);

        when(repository.findById(7L)).thenReturn(Optional.of(entity));
        when(sessaoClient.buscarSessaoPorId(44L)).thenReturn(sessao);
        when(modelMapper.map(entity, PautaDto.class)).thenReturn(dto);

        PautaDto result = service.buscarPautaPorId(7L);

        assertEquals(44L, result.getSessaoDto().getId());
        verify(sessaoClient).buscarSessaoPorId(44L);
    }

    @Test
    void testeAlterarPautaQuandoNaoExiste() {
        PautaDto input = new PautaDto();
        input.setId(5L);
        input.setNome("Nova");
        Pauta entity = Pauta.builder().id(5L).nome("Nova").build();

        when(modelMapper.map(input, Pauta.class)).thenReturn(entity);
        when(validator.validate(entity)).thenReturn(entity);
        when(repository.findById(5L)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> service.alterarPauta(5L, input));
        verify(repository, never()).save(any(Pauta.class));
    }

    @Test
    void testeAlterarPautaComSucesso() {
        PautaDto input = new PautaDto();
        input.setId(8L);
        input.setNome("Atualizada");
        Pauta entity = Pauta.builder().id(8L).nome("Atualizada").idSessao(11L).build();
        Pauta found = Pauta.builder().id(8L).nome("Antiga").idSessao(11L).build();
        Pauta saved = Pauta.builder().id(8L).nome("Atualizada").idSessao(11L).build();
        PautaDto foundDto = new PautaDto();
        foundDto.setId(8L);
        SessaoDto sessao = new SessaoDto();
        sessao.setId(11L);
        PautaDto savedDto = new PautaDto();
        savedDto.setId(8L);
        savedDto.setNome("Atualizada");

        when(modelMapper.map(input, Pauta.class)).thenReturn(entity);
        when(validator.validate(entity)).thenReturn(entity);
        when(repository.findById(8L)).thenReturn(Optional.of(found));
        when(sessaoClient.buscarSessaoPorId(11L)).thenReturn(sessao);
        when(modelMapper.map(found, PautaDto.class)).thenReturn(foundDto);
        when(repository.save(entity)).thenReturn(saved);
        when(modelMapper.map(saved, PautaDto.class)).thenReturn(savedDto);

        PautaDto result = service.alterarPauta(8L, input);

        assertNotNull(result);
        assertEquals(8L, result.getId());
        verify(repository).save(entity);
    }

    @Test
    void testeBuscarTodasPautas() {
        Pauta p1 = Pauta.builder().id(1L).idSessao(10L).nome("P1").build();
        Pauta p2 = Pauta.builder().id(2L).idSessao(20L).nome("P2").build();
        SessaoDto s1 = new SessaoDto();
        s1.setId(10L);
        SessaoDto s2 = new SessaoDto();
        s2.setId(20L);
        PautaDto d1 = new PautaDto();
        d1.setId(1L);
        PautaDto d2 = new PautaDto();
        d2.setId(2L);

        when(repository.findAll()).thenReturn(List.of(p1, p2));
        when(sessaoClient.buscarSessaoPorId(10L)).thenReturn(s1);
        when(sessaoClient.buscarSessaoPorId(20L)).thenReturn(s2);
        when(modelMapper.map(p1, PautaDto.class)).thenReturn(d1);
        when(modelMapper.map(p2, PautaDto.class)).thenReturn(d2);

        List<PautaDto> result = service.buscarTodasPautas();

        assertEquals(2, result.size());
    }

    @Test
    void testeBuscarTodasPautasComAssociado() {
        Pauta p = Pauta.builder().id(3L).idSessao(30L).nome("P3").build();
        SessaoDto s = new SessaoDto();
        s.setId(30L);
        PautaDto d = new PautaDto();
        d.setId(3L);

        when(repository.findAll()).thenReturn(List.of(p));
        when(sessaoClient.buscarSessaoPorId(30L, 99L)).thenReturn(s);
        when(modelMapper.map(p, PautaDto.class)).thenReturn(d);

        List<PautaDto> result = service.buscarTodasPautas(99L);

        assertEquals(1, result.size());
    }

    @Test
    void testeRemoverPauta() {
        doNothing().when(repository).deleteById(12L);

        service.removerPauta(12L);

        verify(repository).deleteById(12L);
    }

    @Test
    void testeBuscarPautaPorIdComAssociado() {
        Pauta entity = Pauta.builder().id(9L).idSessao(50L).nome("Pauta9").build();
        PautaDto dto = new PautaDto();
        dto.setId(9L);
        SessaoDto sessao = new SessaoDto();
        sessao.setId(50L);

        when(repository.findById(9L)).thenReturn(Optional.of(entity));
        when(sessaoClient.buscarSessaoPorId(50L, 77L)).thenReturn(sessao);
        when(modelMapper.map(entity, PautaDto.class)).thenReturn(dto);

        PautaDto result = service.buscarPautaPorId(9L, 77L);

        assertEquals(50L, result.getSessaoDto().getId());
        verify(sessaoClient).buscarSessaoPorId(50L, 77L);
    }
}
