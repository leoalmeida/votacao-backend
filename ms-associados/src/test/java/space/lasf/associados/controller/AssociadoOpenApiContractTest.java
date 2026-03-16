package space.lasf.associados.controller;

import static com.atlassian.oai.validator.mockmvc.OpenApiValidationMatchers.openApi;
import static org.mockito.ArgumentMatchers.any;
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
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import space.lasf.associados.core.exception.BusinessException;
import space.lasf.associados.dto.AssociadoDto;
import space.lasf.associados.dto.VotoDto;
import space.lasf.associados.dto.VotoOpcao;
import space.lasf.associados.service.AssociadoService;

@WebMvcTest(AssociadoController.class)
class AssociadoOpenApiContractTest {

    private static final String OPEN_API_SPEC = "/openapi/associados-openapi.yaml";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AssociadoService associadoService;

    @MockitoBean(name = "messageSource")
    private MessageSource messageSource;

    @Test
    void getAssociadosShouldMatchContract() throws Exception {
        when(associadoService.buscarTodosAssociados()).thenReturn(List.of(buildAssociado(1L), buildAssociado(2L)));

        mockMvc.perform(get("/v1/associados"))
                .andExpect(status().isOk())
                .andExpect(openApi().isValid(OPEN_API_SPEC));
    }

    @Test
    void getAssociadoPorIdShouldMatchContract() throws Exception {
        when(associadoService.buscarAssociadoPorId(1L)).thenReturn(buildAssociado(1L));

        mockMvc.perform(get("/v1/associados/1"))
                .andExpect(status().isOk())
                .andExpect(openApi().isValid(OPEN_API_SPEC));
    }

    @Test
    void getAssociadoExisteShouldMatchContract() throws Exception {
        when(associadoService.buscarAssociadoPorId(1L)).thenReturn(buildAssociado(1L));

        mockMvc.perform(get("/v1/associados/1/existe"))
                .andExpect(status().isOk())
                .andExpect(openApi().isValid(OPEN_API_SPEC));
    }

    @Test
    void getAssociadoPorEmailShouldMatchContract() throws Exception {
        when(associadoService.validarEmailAssociado("ana@test.com")).thenReturn(true);
        when(associadoService.buscarAssociadoPorEmail("ana@test.com")).thenReturn(buildAssociado(3L));

        mockMvc.perform(get("/v1/associados/email/ana@test.com"))
                .andExpect(status().isOk())
                .andExpect(openApi().isValid(OPEN_API_SPEC));
    }

    @Test
    void getAssociadoPorEmailNaoEncontradoShouldMatchContract() throws Exception {
        when(associadoService.validarEmailAssociado("invalido@test.com")).thenReturn(false);

        mockMvc.perform(get("/v1/associados/email/invalido@test.com"))
                .andExpect(status().isNotFound())
                .andExpect(openApi().isValid(OPEN_API_SPEC));
    }

    @Test
    void pesquisarAssociadosPorNomeShouldMatchContract() throws Exception {
        when(associadoService.buscarAssociadosPorNome("Ana")).thenReturn(List.of(buildAssociado(4L)));

        mockMvc.perform(get("/v1/associados/pesquisar").queryParam("nome", "Ana"))
                .andExpect(status().isOk())
                .andExpect(openApi().isValid(OPEN_API_SPEC));
    }

    @Test
    void postAssociadoShouldMatchContract() throws Exception {
        AssociadoDto request = new AssociadoDto();
        request.setNome("Novo Associado");
        request.setEmail("novo@test.com");
        request.setTelefone("11999999999");

        when(associadoService.criarAssociado(any(AssociadoDto.class))).thenReturn(buildAssociado(10L));

        mockMvc.perform(post("/v1/associados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(openApi().isValid(OPEN_API_SPEC));
    }

    @Test
    void putAssociadoShouldMatchContract() throws Exception {
        AssociadoDto request = buildAssociado(20L);

        when(associadoService.alterarAssociado(any(AssociadoDto.class))).thenReturn(buildAssociado(20L));

        mockMvc.perform(put("/v1/associados/20")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(openApi().isValid(OPEN_API_SPEC));
    }

    @Test
    void deleteAssociadoShouldMatchContract() throws Exception {
        doNothing().when(associadoService).removerAssociado(30L);

        mockMvc.perform(delete("/v1/associados/30"))
                .andExpect(status().isNoContent())
                .andExpect(openApi().isValid(OPEN_API_SPEC));
    }

    @Test
    void validarEmailValidoShouldMatchContract() throws Exception {
        when(associadoService.validarEmailAssociado("ok@test.com")).thenReturn(true);

        mockMvc.perform(post("/v1/associados/validar-email").queryParam("email", "ok@test.com"))
                .andExpect(status().isOk())
                .andExpect(openApi().isValid(OPEN_API_SPEC));
    }

    @Test
    void validarEmailInvalidoShouldMatchContract() throws Exception {
        when(associadoService.validarEmailAssociado("ruim")).thenReturn(false);

        mockMvc.perform(post("/v1/associados/validar-email").queryParam("email", "ruim"))
                .andExpect(status().isBadRequest())
                .andExpect(openApi().isValid(OPEN_API_SPEC));
    }

    @Test
    void businessErrorShouldMatchContract() throws Exception {
        when(associadoService.buscarTodosAssociados()).thenThrow(new BusinessException("regra violada"));

        mockMvc.perform(get("/v1/associados"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(openApi().isValid(OPEN_API_SPEC));
    }

    private static AssociadoDto buildAssociado(Long id) {
        AssociadoDto dto = new AssociadoDto();
        dto.setId(id);
        dto.setNome("Associado " + id);
        dto.setEmail("associado" + id + "@test.com");
        dto.setTelefone("1199999000" + id);
        dto.setVotacaoAssociado(Map.of(
                10L,
                VotoDto.builder()
                        .id(100L + id)
                        .idAssociado(id)
                        .idSessao(10L)
                        .opcao(VotoOpcao.SIM)
                        .dataVoto(LocalDateTime.of(2026, 3, 15, 9, 0))
                        .build()));
        return dto;
    }
}