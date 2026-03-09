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
        AssociadoDto a = new AssociadoDto();
        a.setId(1L);
        a.setNome("Joao");
        a.setEmail("joao@example.com");
        AssociadoDto b = new AssociadoDto();
        b.setId(2L);
        b.setNome("Maria");
        b.setEmail("maria@example.com");
        when(associadoService.buscarTodosAssociados()).thenReturn(List.of(a, b));

        mockMvc.perform(get("/v1/associados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }

    @Test
    void criarAssociadoDeveRetornarCreated() throws Exception {
        AssociadoDto input = new AssociadoDto();
        input.setNome("Novo");
        input.setEmail("novo@example.com");
        input.setTelefone("(11)99999-9999");
        AssociadoDto output = new AssociadoDto();
        output.setId(10L);
        output.setNome("Novo");
        output.setEmail("novo@example.com");
        output.setTelefone("(11)99999-9999");
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
