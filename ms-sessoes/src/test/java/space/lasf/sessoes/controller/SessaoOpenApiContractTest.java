package space.lasf.sessoes.controller;

import static com.atlassian.oai.validator.mockmvc.OpenApiValidationMatchers.openApi;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import space.lasf.sessoes.core.exception.BusinessException;
import space.lasf.sessoes.domain.model.SessaoStatus;
import space.lasf.sessoes.domain.model.TotalizadorOpcao;
import space.lasf.sessoes.domain.model.VotoOpcao;
import space.lasf.sessoes.dto.PautaDto;
import space.lasf.sessoes.dto.SessaoDto;
import space.lasf.sessoes.dto.VotoDto;
import space.lasf.sessoes.service.SessaoService;

@WebMvcTest(SessaoController.class)
class SessaoOpenApiContractTest {

    private static final String OPEN_API_SPEC = "/openapi/sessoes-openapi.yaml";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SessaoService sessaoService;

    @MockitoBean(name = "messageSource")
    private MessageSource messageSource;

    @Test
    void getTodasSessoesShouldMatchContract() throws Exception {
        when(sessaoService.buscarTodasSessoes()).thenReturn(List.of(buildSessaoDto(1L), buildSessaoDto(2L)));

        mockMvc.perform(get("/v1/sessoes"))
                .andExpect(status().isOk())
                .andExpect(openApi().isValid(OPEN_API_SPEC));
    }

    @Test
    void getSessaoPorIdShouldMatchContract() throws Exception {
        when(sessaoService.buscarSessaoPorId(5L)).thenReturn(buildSessaoDto(5L));

        mockMvc.perform(get("/v1/sessoes/5"))
                .andExpect(status().isOk())
                .andExpect(openApi().isValid(OPEN_API_SPEC));
    }

    @Test
    void getSessaoPorIdEAssociadoShouldMatchContract() throws Exception {
        when(sessaoService.buscarSessaoPorId(7L, 22L)).thenReturn(buildSessaoDto(7L));

        mockMvc.perform(get("/v1/sessoes/7/associado/22"))
                .andExpect(status().isOk())
                .andExpect(openApi().isValid(OPEN_API_SPEC));
    }

    @Test
    void postCriarSessaoShouldMatchContract() throws Exception {
        PautaDto pautaDto = new PautaDto();
        pautaDto.setId(10L);
        pautaDto.setNome("Pauta 10");
        pautaDto.setDescricao("Descricao");
        pautaDto.setCategoria("GERAL");

        when(sessaoService.criarSessao(any(PautaDto.class), eq(true))).thenReturn(buildSessaoDto(10L));

        mockMvc.perform(post("/v1/sessoes")
                        .queryParam("iniciarSessao", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pautaDto)))
                .andExpect(status().isCreated())
                .andExpect(openApi().isValid(OPEN_API_SPEC));
    }

    @Test
    void postIniciarSessaoShouldMatchContract() throws Exception {
        SessaoDto sessao = buildSessaoDto(11L);
        sessao.setStatus(SessaoStatus.OPEN_TO_VOTE);
        when(sessaoService.iniciarSessao(11L)).thenReturn(sessao);

        mockMvc.perform(post("/v1/sessoes/11/iniciar"))
                .andExpect(status().isOk())
                .andExpect(openApi().isValid(OPEN_API_SPEC));
    }

    @Test
    void postFinalizarSessaoShouldMatchContract() throws Exception {
        SessaoDto sessao = buildSessaoDto(12L);
        sessao.setStatus(SessaoStatus.CLOSED);
        when(sessaoService.finalizarSessao(12L)).thenReturn(sessao);

        mockMvc.perform(post("/v1/sessoes/12/finalizar"))
                .andExpect(status().isOk())
                .andExpect(openApi().isValid(OPEN_API_SPEC));
    }

    @Test
    void postCancelarSessaoShouldMatchContract() throws Exception {
        doNothing().when(sessaoService).cancelarSessao(13L);

        mockMvc.perform(post("/v1/sessoes/13/cancelar"))
                .andExpect(status().isOk())
                .andExpect(openApi().isValid(OPEN_API_SPEC));
    }

    @Test
    void postValidarSessaoShouldMatchContract() throws Exception {
        SessaoDto sessao = buildSessaoDto(14L);
        sessao.setStatus(SessaoStatus.OPEN_TO_VOTE);
        when(sessaoService.buscarSessaoPorId(14L)).thenReturn(sessao);

        mockMvc.perform(post("/v1/sessoes/14/validar"))
                .andExpect(status().isOk())
                .andExpect(openApi().isValid(OPEN_API_SPEC));
    }

    @Test
    void businessErrorShouldMatchContract() throws Exception {
        when(sessaoService.buscarTodasSessoes()).thenThrow(new BusinessException("regra violada"));

        mockMvc.perform(get("/v1/sessoes"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(openApi().isValid(OPEN_API_SPEC));
    }

    private static SessaoDto buildSessaoDto(Long id) {
        SessaoDto dto = new SessaoDto();
        dto.setId(id);
        dto.setStatus(SessaoStatus.OPEN_TO_VOTE);
        dto.setResultado("EM_ANDAMENTO");
        dto.setDataInicioSessao(LocalDateTime.of(2026, 3, 15, 10, 0));
        dto.setDataFimSessao(LocalDateTime.of(2026, 3, 15, 10, 30));
        dto.setTotalizadores(List.of(new TotalizadorOpcao(VotoOpcao.SIM, 3L)));

        PautaDto pautaDto = new PautaDto();
        pautaDto.setId(100L + id);
        pautaDto.setNome("Pauta " + id);
        pautaDto.setDescricao("Descricao " + id);
        pautaDto.setCategoria("GERAL");
        dto.setPautaDto(pautaDto);

        dto.setVotoAssociado(VotoDto.builder()
                .id(200L + id)
                .idAssociado(50L)
                .idSessao(id)
                .opcao(VotoOpcao.SIM)
                .dataVoto(LocalDateTime.of(2026, 3, 15, 10, 5))
                .build());
        return dto;
    }
}