package space.lasf.sessoes.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import space.lasf.sessoes.core.exception.BusinessException;
import space.lasf.sessoes.domain.model.SessaoStatus;
import space.lasf.sessoes.dto.PautaDto;
import space.lasf.sessoes.dto.SessaoDto;
import space.lasf.sessoes.service.SessaoService;

@WebMvcTest(SessaoController.class)
class SessaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SessaoService sessaoService;

    @MockitoBean(name = "messageSource")
    private MessageSource messageSource;

    @Test
    void buscarTodasSessoesDeveRetornarLista() throws Exception {
        SessaoDto s1 = new SessaoDto();
        s1.setId(1L);
        SessaoDto s2 = new SessaoDto();
        s2.setId(2L);
        when(sessaoService.buscarTodasSessoes()).thenReturn(List.of(s1, s2));

        mockMvc.perform(get("/v1/sessoes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }

    @Test
    void buscarSessaoPorIdDeveRetornarOk() throws Exception {
        SessaoDto s = new SessaoDto();
        s.setId(5L);
        when(sessaoService.buscarSessaoPorId(5L)).thenReturn(s);

        mockMvc.perform(get("/v1/sessoes/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(5)));
    }

    @Test
    void buscarSessaoPorIdComAssociadoDeveRetornarOk() throws Exception {
        SessaoDto s = new SessaoDto();
        s.setId(8L);
        when(sessaoService.buscarSessaoPorId(8L, 30L)).thenReturn(s);

        mockMvc.perform(get("/v1/sessoes/8/associado/30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(8)));
    }

    @Test
    void criarSessaoDeveRetornarCreated() throws Exception {
        PautaDto pautaDto = new PautaDto();
        pautaDto.setId(10L);
        SessaoDto output = new SessaoDto();
        output.setId(50L);
        when(sessaoService.criarSessao(any(PautaDto.class), eq(true))).thenReturn(output);

        mockMvc.perform(post("/v1/sessoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pautaDto))
                        .param("iniciarSessao", "true"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(50)));
    }

    @Test
    void iniciarSessaoDeveRetornarOk() throws Exception {
        SessaoDto s = new SessaoDto();
        s.setId(3L);
        s.setStatus(SessaoStatus.OPEN_TO_VOTE);
        when(sessaoService.iniciarSessao(3L)).thenReturn(s);

        mockMvc.perform(post("/v1/sessoes/3/iniciar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(3)));
    }

    @Test
    void finalizarSessaoDeveRetornarOk() throws Exception {
        SessaoDto s = new SessaoDto();
        s.setId(4L);
        s.setStatus(SessaoStatus.CLOSED);
        when(sessaoService.finalizarSessao(4L)).thenReturn(s);

        mockMvc.perform(post("/v1/sessoes/4/finalizar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(4)));
    }

    @Test
    void cancelarSessaoDeveRetornarOk() throws Exception {
        doNothing().when(sessaoService).cancelarSessao(7L);

        mockMvc.perform(post("/v1/sessoes/7/cancelar")).andExpect(status().isOk());
    }

    @Test
    void validarSessaoAbertaDeveRetornarTrue() throws Exception {
        SessaoDto s = new SessaoDto();
        s.setId(9L);
        s.setStatus(SessaoStatus.OPEN_TO_VOTE);
        when(sessaoService.buscarSessaoPorId(9L)).thenReturn(s);

        mockMvc.perform(post("/v1/sessoes/9/validar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.9", is(true)));
    }

    @Test
    void validarSessaoFechadaDeveRetornarFalse() throws Exception {
        SessaoDto s = new SessaoDto();
        s.setId(11L);
        s.setStatus(SessaoStatus.CLOSED);
        when(sessaoService.buscarSessaoPorId(11L)).thenReturn(s);

        mockMvc.perform(post("/v1/sessoes/11/validar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.11", is(false)));
    }

    // --- GlobalExceptionHandler coverage ---

    @Test
    void deveRetornar422QuandoBusinessExceptionLancada() throws Exception {
        when(sessaoService.buscarTodasSessoes()).thenThrow(new BusinessException("regra violada"));

        mockMvc.perform(get("/v1/sessoes")).andExpect(status().isUnprocessableEntity());
    }

    @Test
    void deveRetornar404QuandoEntityNotFoundExceptionLancada() throws Exception {
        when(sessaoService.buscarTodasSessoes()).thenThrow(new EntityNotFoundException("nao encontrado"));

        mockMvc.perform(get("/v1/sessoes")).andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar404QuandoNoSuchElementExceptionLancada() throws Exception {
        when(sessaoService.buscarTodasSessoes()).thenThrow(new NoSuchElementException("sem elemento"));

        mockMvc.perform(get("/v1/sessoes")).andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar400QuandoIllegalArgumentExceptionLancada() throws Exception {
        when(sessaoService.buscarTodasSessoes()).thenThrow(new IllegalArgumentException("arg invalido"));

        mockMvc.perform(get("/v1/sessoes")).andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornar500QuandoExceptionGenericaLancada() throws Exception {
        when(sessaoService.buscarTodasSessoes()).thenThrow(new RuntimeException("erro inesperado"));
        when(messageSource.getMessage(any(String.class), any(Object[].class), any(java.util.Locale.class)))
                .thenReturn("Erro interno: erro inesperado");

        mockMvc.perform(get("/v1/sessoes")).andExpect(status().isInternalServerError());
    }
}
