package space.lasf.pautas.controller;

import static com.atlassian.oai.validator.mockmvc.OpenApiValidationMatchers.openApi;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import space.lasf.pautas.core.exception.BusinessException;
import space.lasf.pautas.dto.PautaDto;
import space.lasf.pautas.dto.SessaoDto;
import space.lasf.pautas.dto.SessaoStatus;
import space.lasf.pautas.dto.TotalizadorOpcao;
import space.lasf.pautas.dto.VotoDto;
import space.lasf.pautas.dto.VotoOpcao;
import space.lasf.pautas.service.PautaService;

@WebMvcTest(PautaController.class)
class PautaOpenApiContractTest {

    private static final String OPEN_API_SPEC = "/openapi/pautas-openapi.yaml";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PautaService pautaService;

    @MockitoBean(name = "messageSource")
    private MessageSource messageSource;

    @Test
    void getTodasPautasShouldMatchContract() throws Exception {
        when(pautaService.buscarTodasPautas()).thenReturn(List.of(buildPautaDto(1L, 10L), buildPautaDto(2L, 20L)));

        mockMvc.perform(get("/v1/pautas"))
                .andExpect(status().isOk())
                .andExpect(openApi().isValid(OPEN_API_SPEC));
    }

    @Test
    void getPautasPorAssociadoShouldMatchContract() throws Exception {
        when(pautaService.buscarTodasPautas(15L)).thenReturn(List.of(buildPautaDto(3L, 15L)));

        mockMvc.perform(get("/v1/pautas/associado/15"))
                .andExpect(status().isOk())
                .andExpect(openApi().isValid(OPEN_API_SPEC));
    }

    @Test
    void getPautaPorIdShouldMatchContract() throws Exception {
        when(pautaService.buscarPautaPorId(5L)).thenReturn(buildPautaDto(5L, 30L));

        mockMvc.perform(get("/v1/pautas/5"))
                .andExpect(status().isOk())
                .andExpect(openApi().isValid(OPEN_API_SPEC));
    }

    @Test
    void getPautaPorIdEAssociadoShouldMatchContract() throws Exception {
        when(pautaService.buscarPautaPorId(7L, 44L)).thenReturn(buildPautaDto(7L, 44L));

        mockMvc.perform(get("/v1/pautas/7/associado/44"))
                .andExpect(status().isOk())
                .andExpect(openApi().isValid(OPEN_API_SPEC));
    }

    @Test
    void postCriarPautaShouldMatchContract() throws Exception {
        PautaDto request = new PautaDto();
        request.setNome("Nova pauta");
        request.setDescricao("Descricao da pauta");
        request.setCategoria("GERAL");
        request.setIdAssociado(99L);

        when(pautaService.criarPauta(any(PautaDto.class), eq(true))).thenReturn(buildPautaDto(99L, 99L));

        mockMvc.perform(post("/v1/pautas")
                        .queryParam("iniciarSessao", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(openApi().isValid(OPEN_API_SPEC));
    }

    @Test
    void putAlterarPautaShouldMatchContract() throws Exception {
        PautaDto request = new PautaDto();
        request.setId(8L);
        request.setNome("Pauta atualizada");
        request.setDescricao("Descricao atualizada");
        request.setCategoria("GERAL");
        request.setIdAssociado(22L);

        when(pautaService.alterarPauta(eq(8L), any(PautaDto.class))).thenReturn(buildPautaDto(8L, 22L));

        mockMvc.perform(put("/v1/pautas/8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(openApi().isValid(OPEN_API_SPEC));
    }

    @Test
    void deleteRemoverPautaShouldMatchContract() throws Exception {
        doNothing().when(pautaService).removerPauta(12L);

        mockMvc.perform(delete("/v1/pautas/12"))
                .andExpect(status().isNoContent())
                .andExpect(openApi().isValid(OPEN_API_SPEC));
    }

    @Test
    void businessErrorShouldMatchContract() throws Exception {
        when(pautaService.buscarPautaPorId(404L)).thenThrow(new BusinessException("Pauta nao encontrada"));

        mockMvc.perform(get("/v1/pautas/404"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(openApi().isValid(OPEN_API_SPEC));
    }

    private static PautaDto buildPautaDto(Long pautaId, Long associadoId) {
        PautaDto pautaDto = new PautaDto();
        pautaDto.setId(pautaId);
        pautaDto.setNome("Pauta " + pautaId);
        pautaDto.setDescricao("Descricao da pauta " + pautaId);
        pautaDto.setCategoria("GERAL");
        pautaDto.setIdAssociado(associadoId);
        pautaDto.setSessaoDto(buildSessaoDto(pautaId + 100));
        return pautaDto;
    }

    private static SessaoDto buildSessaoDto(Long sessaoId) {
        SessaoDto sessaoDto = new SessaoDto();
        sessaoDto.setId(sessaoId);
        sessaoDto.setStatus(SessaoStatus.OPEN_TO_VOTE);
        sessaoDto.setResultado("EM_ANDAMENTO");
        sessaoDto.setDataInicioSessao(LocalDateTime.of(2026, 3, 15, 10, 0));
        sessaoDto.setDataFimSessao(LocalDateTime.of(2026, 3, 15, 10, 30));
        sessaoDto.setTotalizadores(List.of(new TotalizadorOpcao(VotoOpcao.SIM, 3L)));
        sessaoDto.setVotoAssociado(VotoDto.builder()
                .id(1L)
                .idAssociado(50L)
                .idSessao(sessaoId)
                .opcao(VotoOpcao.SIM)
                .dataVoto(LocalDateTime.of(2026, 3, 15, 10, 5))
                .build());
        return sessaoDto;
    }
}