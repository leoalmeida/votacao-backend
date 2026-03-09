package space.lasf.associados.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
class AssociadoRestControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private AssociadoService service;

    @Test
    void testeAssociadoQuandoConsultarAssociadosThenStatus200() throws Exception {
        AssociadoDto joao = AssociadoDto.builder()
                .id(1L)
                .nome("Joao Silva")
                .email("joao@example.com")
                .build();
        AssociadoDto maria = AssociadoDto.builder()
                .id(2L)
                .nome("Maria Santos")
                .email("maria@example.com")
                .build();
        AssociadoDto pedro = AssociadoDto.builder()
                .id(3L)
                .nome("Pedro Oliveira")
                .email("pedro@example.com")
                .build();
        List<AssociadoDto> todosAssociados = List.of(joao, maria, pedro);

        when(service.buscarTodosAssociados()).thenReturn(todosAssociados);

        mvc.perform(get("/v1/associados"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].nome", is("Joao Silva")))
                .andExpect(jsonPath("$[1].nome", is("Maria Santos")))
                .andExpect(jsonPath("$[2].nome", is("Pedro Oliveira")));
    }
}
