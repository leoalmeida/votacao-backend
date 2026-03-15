package space.lasf.sessoes.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;
import space.lasf.sessoes.domain.model.Sessao;
import space.lasf.sessoes.domain.model.SessaoStatus;
import space.lasf.sessoes.domain.model.Voto;
import space.lasf.sessoes.domain.model.VotoOpcao;
import space.lasf.sessoes.domain.repository.SessaoRepository;
import space.lasf.sessoes.domain.repository.VotoRepository;
import space.lasf.sessoes.dto.PautaDto;
import space.lasf.sessoes.dto.SessaoDto;
import space.lasf.sessoes.dto.VotoDto;

@ExtendWith(MockitoExtension.class)
class SessaoServiceImplTest {

    @Mock
    private SessaoRepository sessaoRepository;

    @Mock
    private VotoRepository votacaoRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private SessaoServiceImpl service;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(service, "modelMapper", modelMapper);
    }

    @Test
    void criarSessaoDeveCriarComStatusOpenToVoteQuandoIniciarSessaoTrue() {
        PautaDto pautaDto = new PautaDto();
        pautaDto.setId(10L);

        Sessao saved = Sessao.builder().id(1L).idPauta(10L).build();
        saved.iniciarSessao();
        SessaoDto dto = new SessaoDto();
        dto.setId(1L);

        when(sessaoRepository.save(any(Sessao.class))).thenReturn(saved);
        when(modelMapper.map(saved, SessaoDto.class)).thenReturn(dto);

        SessaoDto result = service.criarSessao(pautaDto, true);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(sessaoRepository).save(any(Sessao.class));
    }

    @Test
    void criarSessaoDeveCriarComStatusCreatedQuandoNaoIniciar() {
        PautaDto pautaDto = new PautaDto();
        pautaDto.setId(20L);

        Sessao saved = Sessao.builder().id(2L).idPauta(20L).build();
        SessaoDto dto = new SessaoDto();
        dto.setId(2L);

        when(sessaoRepository.save(any(Sessao.class))).thenReturn(saved);
        when(modelMapper.map(saved, SessaoDto.class)).thenReturn(dto);

        SessaoDto result = service.criarSessao(pautaDto, false);

        assertNotNull(result);
        assertEquals(2L, result.getId());
    }

    @Test
    void criarSessaoDeveLancarIllegalArgumentQuandoPautaNull() {
        assertThrows(IllegalArgumentException.class, () -> service.criarSessao(null, false));
    }

    @Test
    void criarSessaoDeveLancarIllegalArgumentQuandoIdPautaNull() {
        PautaDto pautaDto = new PautaDto();
        pautaDto.setId(null);
        assertThrows(IllegalArgumentException.class, () -> service.criarSessao(pautaDto, false));
    }

    @Test
    void isSessaoAbertaDeveRetornarTrueQuandoSessaoAberta() {
        Sessao sessao = Sessao.builder().id(5L).idPauta(1L).build();
        sessao.iniciarSessao();
        when(sessaoRepository.existSessaoOnStatus(SessaoStatus.OPEN_TO_VOTE, 5L)).thenReturn(sessao);

        boolean result = service.isSessaoAbertaParaVotacao(5L);

        assertTrue(result);
    }

    @Test
    void isSessaoAbertaDeveRetornarFalseQuandoSessaoNull() {
        when(sessaoRepository.existSessaoOnStatus(SessaoStatus.OPEN_TO_VOTE, 99L)).thenReturn(null);

        boolean result = service.isSessaoAbertaParaVotacao(99L);

        assertFalse(result);
    }

    @Test
    void buscarSessaoPorIdDeveRetornarDtoQuandoEncontrado() {
        Sessao sessao = Sessao.builder().id(3L).idPauta(1L).build();
        SessaoDto dto = new SessaoDto();
        dto.setId(3L);

        when(sessaoRepository.findById(3L)).thenReturn(Optional.of(sessao));
        when(modelMapper.map(sessao, SessaoDto.class)).thenReturn(dto);

        SessaoDto result = service.buscarSessaoPorId(3L);

        assertEquals(3L, result.getId());
    }

    @Test
    void buscarSessaoPorIdDeveLancarEntityNotFoundQuandoNaoEncontrado() {
        when(sessaoRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.buscarSessaoPorId(999L));
    }

    @Test
    void buscarSessaoPorIdComAssociadoDeveRetornarDtoComVoto() {
        Sessao sessao = Sessao.builder().id(4L).idPauta(1L).build();
        SessaoDto dto = new SessaoDto();
        dto.setId(4L);
        Voto voto = Voto.builder().id(1L).idAssociado(10L).idSessao(4L).opcao(VotoOpcao.SIM).build();
        VotoDto votoDto = VotoDto.builder().id(1L).build();

        when(sessaoRepository.findById(4L)).thenReturn(Optional.of(sessao));
        when(modelMapper.map(sessao, SessaoDto.class)).thenReturn(dto);
        when(votacaoRepository.findVotosByIdSessaoAndIdAssociado(4L, 10L)).thenReturn(Optional.of(voto));
        when(modelMapper.map(voto, VotoDto.class)).thenReturn(votoDto);

        SessaoDto result = service.buscarSessaoPorId(4L, 10L);

        assertNotNull(result);
        assertEquals(4L, result.getId());
    }

    @Test
    void buscarTodasSessoesDeveRetornarListaMapeada() {
        Sessao s1 = Sessao.builder().id(1L).idPauta(1L).build();
        Sessao s2 = Sessao.builder().id(2L).idPauta(2L).build();
        SessaoDto d1 = new SessaoDto();
        d1.setId(1L);
        SessaoDto d2 = new SessaoDto();
        d2.setId(2L);

        when(sessaoRepository.findAll()).thenReturn(List.of(s1, s2));
        when(modelMapper.map(s1, SessaoDto.class)).thenReturn(d1);
        when(modelMapper.map(s2, SessaoDto.class)).thenReturn(d2);

        List<SessaoDto> result = service.buscarTodasSessoes();

        assertEquals(2, result.size());
    }

    @Test
    void buscarResultadoDeVotacaoDeveRetornarDtoQuandoSessaoFechada() {
        Sessao sessao = Sessao.builder().id(6L).idPauta(1L).build();
        sessao.setStatus(SessaoStatus.CLOSED);
        SessaoDto dto = new SessaoDto();
        dto.setId(6L);

        when(sessaoRepository.findById(6L)).thenReturn(Optional.of(sessao));
        when(modelMapper.map(sessao, SessaoDto.class)).thenReturn(dto);

        SessaoDto result = service.buscarResultadoDeVotacao(6L);

        assertNotNull(result);
        assertEquals(6L, result.getId());
    }

    @Test
    void buscarResultadoDeVotacaoDeveLancarIllegalStateQuandoSessaoNaoFechada() {
        Sessao sessao = Sessao.builder().id(7L).idPauta(1L).build();
        // status = CREATED by default (not CLOSED)
        when(sessaoRepository.findById(7L)).thenReturn(Optional.of(sessao));

        assertThrows(IllegalStateException.class, () -> service.buscarResultadoDeVotacao(7L));
    }

    @Test
    void buscarResultadoDeVotacaoDeveLancarIllegalArgumentQuandoIdNull() {
        assertThrows(IllegalArgumentException.class, () -> service.buscarResultadoDeVotacao(null));
    }

    @Test
    void iniciarSessaoDeveRetornarDtoComStatusOpenToVote() {
        Sessao sessao = Sessao.builder().id(8L).idPauta(1L).build();
        // CREATED status, can be started
        Sessao saved = Sessao.builder().id(8L).idPauta(1L).build();
        saved.iniciarSessao();
        SessaoDto dto = new SessaoDto();
        dto.setId(8L);
        dto.setStatus(SessaoStatus.OPEN_TO_VOTE);

        when(sessaoRepository.findById(8L)).thenReturn(Optional.of(sessao));
        when(sessaoRepository.save(any(Sessao.class))).thenReturn(saved);
        when(modelMapper.map(saved, SessaoDto.class)).thenReturn(dto);

        SessaoDto result = service.iniciarSessao(8L);

        assertEquals(SessaoStatus.OPEN_TO_VOTE, result.getStatus());
    }

    @Test
    void iniciarSessaoDeveLancarEntityNotFoundQuandoNaoEncontrado() {
        when(sessaoRepository.findById(555L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.iniciarSessao(555L));
    }

    @Test
    void finalizarSessaoDeveRetornarDtoComStatusClosed() {
        Sessao sessao = mock(Sessao.class);
        Sessao saved = mock(Sessao.class);
        SessaoDto dto = new SessaoDto();
        dto.setId(9L);
        dto.setStatus(SessaoStatus.CLOSED);

        when(sessaoRepository.findById(9L)).thenReturn(Optional.of(sessao));
        when(sessao.finalizarSessao()).thenReturn(sessao);
        when(sessao.totalizarVotos(anyList())).thenReturn(saved);
        when(votacaoRepository.findVotosByIdSessao(9L)).thenReturn(List.of());
        when(sessaoRepository.save(saved)).thenReturn(saved);
        when(modelMapper.map(saved, SessaoDto.class)).thenReturn(dto);

        SessaoDto result = service.finalizarSessao(9L);

        assertEquals(SessaoStatus.CLOSED, result.getStatus());
    }

    @Test
    void cancelarSessaoDeveExecutarQuandoSessaoAberta() {
        Sessao sessao = Sessao.builder().id(14L).idPauta(1L).build();
        sessao.iniciarSessao();
        Sessao saved = Sessao.builder().id(14L).idPauta(1L).build();
        saved.iniciarSessao();
        saved.cancelarSessao();

        when(sessaoRepository.findById(14L)).thenReturn(Optional.of(sessao));
        when(sessaoRepository.save(any(Sessao.class))).thenReturn(saved);

        service.cancelarSessao(14L);

        verify(sessaoRepository).save(any(Sessao.class));
    }
}
