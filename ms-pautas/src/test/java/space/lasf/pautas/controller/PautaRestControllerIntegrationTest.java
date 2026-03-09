package space.lasf.pautas.controller;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import space.lasf.pautas.dto.PautaDto;
import space.lasf.pautas.service.PautaService;

@WebMvcTest(PautaController.class)
class PautaRestControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private PautaService service;

    @Test
    void testePautaQuandoConsultarTodosPautasEntaoRetornaPautasComSucesso() throws Exception {
        PautaDto pauta1 = PautaDto.builder().nome("Pauta1").build();
        PautaDto pauta2 = PautaDto.builder().nome("Pauta2").build();
        List<PautaDto> todasPautas = List.of(pauta1, pauta2);

        when(service.buscarTodasPautas()).thenReturn(todasPautas);

        mvc.perform(get("/v1/pautas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome", is("Pauta1")))
                .andExpect(jsonPath("$[1].nome", is("Pauta2")));
    }
}
