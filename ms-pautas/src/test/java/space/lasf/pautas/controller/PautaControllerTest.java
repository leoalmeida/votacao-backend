package space.lasf.pautas.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import space.lasf.pautas.core.exception.BusinessException;
import space.lasf.pautas.dto.PautaDto;
import space.lasf.pautas.service.PautaService;

@WebMvcTest(PautaController.class)
class PautaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PautaService pautaService;

    @MockitoBean(name = "messageSource")
    private MessageSource messageSource;

    // --- Controller endpoints ---

    @Test
    void buscarTodasPautasDeveRetornarLista() throws Exception {
        PautaDto p1 = new PautaDto();
        p1.setId(1L);
        p1.setNome("Pauta A");
        PautaDto p2 = new PautaDto();
        p2.setId(2L);
        p2.setNome("Pauta B");
        when(pautaService.buscarTodasPautas()).thenReturn(List.of(p1, p2));

        mockMvc.perform(get("/v1/pautas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }

    @Test
    void buscarTodasPautasPorAssociadoDeveRetornarLista() throws Exception {
        PautaDto p = new PautaDto();
        p.setId(3L);
        p.setNome("Pauta C");
        when(pautaService.buscarTodasPautas(10L)).thenReturn(List.of(p));

        mockMvc.perform(get("/v1/pautas/associado/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(3)));
    }

    @Test
    void buscarPautaPorIdDeveRetornarOk() throws Exception {
        PautaDto p = new PautaDto();
        p.setId(5L);
        p.setNome("Pauta 5");
        when(pautaService.buscarPautaPorId(5L)).thenReturn(p);

        mockMvc.perform(get("/v1/pautas/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(5)));
    }

    @Test
    void buscarPautaPorIdComAssociadoDeveRetornarOk() throws Exception {
        PautaDto p = new PautaDto();
        p.setId(7L);
        when(pautaService.buscarPautaPorId(7L, 20L)).thenReturn(p);

        mockMvc.perform(get("/v1/pautas/7/associado/20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(7)));
    }

    @Test
    void criarPautaDeveRetornarCreated() throws Exception {
        PautaDto input = new PautaDto();
        input.setNome("Nova Pauta");
        PautaDto output = new PautaDto();
        output.setId(99L);
        output.setNome("Nova Pauta");
        when(pautaService.criarPauta(any(PautaDto.class), eq(true))).thenReturn(output);

        mockMvc.perform(post("/v1/pautas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                        .param("iniciarSessao", "true"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(99)));
    }

    @Test
    void alterarPautaDeveRetornarOk() throws Exception {
        PautaDto input = new PautaDto();
        input.setId(4L);
        input.setNome("Atualizada");
        PautaDto output = new PautaDto();
        output.setId(4L);
        output.setNome("Atualizada");
        when(pautaService.alterarPauta(eq(4L), any(PautaDto.class))).thenReturn(output);

        mockMvc.perform(put("/v1/pautas/4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(4)));
    }

    @Test
    void removerPautaDeveRetornarNoContent() throws Exception {
        doNothing().when(pautaService).removerPauta(6L);

        mockMvc.perform(delete("/v1/pautas/6")).andExpect(status().isNoContent());
    }

    // --- GlobalExceptionHandler coverage ---

    @Test
    void deveRetornar422QuandoBusinessExceptionLancada() throws Exception {
        when(pautaService.buscarTodasPautas()).thenThrow(new BusinessException("regra violada"));

        mockMvc.perform(get("/v1/pautas")).andExpect(status().isUnprocessableEntity());
    }

    @Test
    void deveRetornar404QuandoEntityNotFoundExceptionLancada() throws Exception {
        when(pautaService.buscarTodasPautas()).thenThrow(new EntityNotFoundException("nao encontrado"));

        mockMvc.perform(get("/v1/pautas")).andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar404QuandoNoSuchElementExceptionLancada() throws Exception {
        when(pautaService.buscarTodasPautas()).thenThrow(new NoSuchElementException("sem elemento"));

        mockMvc.perform(get("/v1/pautas")).andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar400QuandoIllegalArgumentExceptionLancada() throws Exception {
        when(pautaService.buscarTodasPautas()).thenThrow(new IllegalArgumentException("arg invalido"));

        mockMvc.perform(get("/v1/pautas")).andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornar500QuandoExceptionGenericaLancada() throws Exception {
        when(pautaService.buscarTodasPautas()).thenThrow(new RuntimeException("erro inesperado"));
        when(messageSource.getMessage(any(String.class), any(Object[].class), any(java.util.Locale.class)))
                .thenReturn("Erro interno: erro inesperado");

        mockMvc.perform(get("/v1/pautas")).andExpect(status().isInternalServerError());
    }
}
