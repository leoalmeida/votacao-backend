package space.lasf.sessoes.controller;

import static com.atlassian.oai.validator.mockmvc.OpenApiValidationMatchers.openApi;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import space.lasf.sessoes.core.exception.BusinessException;
import space.lasf.sessoes.domain.model.VotoOpcao;
import space.lasf.sessoes.dto.VotoDto;
import space.lasf.sessoes.service.VotoService;

@WebMvcTest(VotosController.class)
class VotosOpenApiContractTest {

    private static final String OPEN_API_SPEC = "/openapi/sessoes-openapi.yaml";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private VotoService votoService;

    @MockitoBean(name = "messageSource")
    private MessageSource messageSource;

    @Test
    void getVotosPorSessaoShouldMatchContract() throws Exception {
        when(votoService.buscarVotosSessao(10L)).thenReturn(List.of(buildVoto(1L, 10L, 2L)));

        mockMvc.perform(get("/v1/votos/sessao/10"))
                .andExpect(status().isOk())
                .andExpect(openApi().isValid(OPEN_API_SPEC));
    }

    @Test
    void getVotosPorAssociadoShouldMatchContract() throws Exception {
        when(votoService.buscarVotosAssociado(2L)).thenReturn(Map.of(10L, buildVoto(1L, 10L, 2L)));

        mockMvc.perform(get("/v1/votos/associado/2"))
                .andExpect(status().isOk())
                .andExpect(openApi().isValid(OPEN_API_SPEC));
    }

    @Test
    void getVotoPorIdShouldMatchContract() throws Exception {
        when(votoService.buscarVotoPorId(5L)).thenReturn(buildVoto(5L, 10L, 2L));

        mockMvc.perform(get("/v1/votos/5"))
                .andExpect(status().isOk())
                .andExpect(openApi().isValid(OPEN_API_SPEC));
    }

    @Test
    void postCriarVotoShouldMatchContract() throws Exception {
        VotoDto request = buildVoto(null, 15L, 3L);
        when(votoService.criarVoto(any(VotoDto.class))).thenReturn(buildVoto(50L, 15L, 3L));

        mockMvc.perform(post("/v1/votos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(openApi().isValid(OPEN_API_SPEC));
    }

    @Test
    void deleteVotoShouldMatchContract() throws Exception {
        doNothing().when(votoService).removerVoto(9L);

        mockMvc.perform(delete("/v1/votos/9"))
                .andExpect(status().isNoContent())
                .andExpect(openApi().isValid(OPEN_API_SPEC));
    }

    @Test
    void businessErrorShouldMatchContract() throws Exception {
        when(votoService.buscarVotoPorId(99L)).thenThrow(new BusinessException("voto invalido"));

        mockMvc.perform(get("/v1/votos/99"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(openApi().isValid(OPEN_API_SPEC));
    }

    private static VotoDto buildVoto(Long id, Long idSessao, Long idAssociado) {
        return VotoDto.builder()
                .id(id)
                .idSessao(idSessao)
                .idAssociado(idAssociado)
                .opcao(VotoOpcao.SIM)
                .dataVoto(LocalDateTime.of(2026, 3, 15, 11, 0))
                .build();
    }
}