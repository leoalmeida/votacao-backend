package space.lasf.associados.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import space.lasf.associados.dto.AssociadoDto;
import space.lasf.associados.service.AssociadoService;

@WebMvcTest(AssociadoController.class)
class AssociadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AssociadoService associadoService;

    @Test
    void buscarAssociadosDeveRetornarLista() throws Exception {
        AssociadoDto a = AssociadoDto.builder()
                .id(1L)
                .nome("Joao")
                .email("joao@example.com")
                .build();
        AssociadoDto b = AssociadoDto.builder()
                .id(2L)
                .nome("Maria")
                .email("maria@example.com")
                .build();
        when(associadoService.buscarTodosAssociados()).thenReturn(List.of(a, b));

        mockMvc.perform(get("/v1/associados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }

    @Test
    void criarAssociadoDeveRetornarCreated() throws Exception {
        AssociadoDto input = AssociadoDto.builder()
                .nome("Novo")
                .email("novo@example.com")
                .telefone("(11)99999-9999")
                .build();
        AssociadoDto output = AssociadoDto.builder()
                .id(10L)
                .nome("Novo")
                .email("novo@example.com")
                .telefone("(11)99999-9999")
                .build();
        when(associadoService.criarAssociado(any(AssociadoDto.class))).thenReturn(output);

        mockMvc.perform(post("/v1/associados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(10)));
    }

    @Test
    void validateEmailDeveRetornarBadRequestQuandoInvalido() throws Exception {
        when(associadoService.validarEmailAssociado("email-invalido")).thenReturn(false);

        mockMvc.perform(post("/v1/associados/validar-email").param("email", "email-invalido"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(false)));
    }

    @Test
    void removerAssociadoDeveRetornarNoContent() throws Exception {
        doNothing().when(associadoService).removerAssociado(3L);

        mockMvc.perform(delete("/v1/associados/3")).andExpect(status().isNoContent());
    }
}
