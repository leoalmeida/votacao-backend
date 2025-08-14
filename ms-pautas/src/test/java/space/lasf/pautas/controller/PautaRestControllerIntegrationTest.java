package space.lasf.pautas.controller;

import static org.mockito.Mockito.doReturn;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import space.lasf.pautas.domain.repository.PautaRepository;
import space.lasf.pautas.dto.PautaDto;
import space.lasf.pautas.service.PautaService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PautaRestControllerIntegrationTest  {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PautaRepository repository;

    @MockitoBean
    private PautaService service;


    @Test
    public void testePauta_quandoConsultarTodosPautas_entaoRetornaPautasComSucesso()
      throws Exception {

        PautaDto pauta1 = PautaDto.builder().nome("Pauta1").build();
        PautaDto pauta2 = PautaDto.builder().nome("Pauta2").build();
        List<PautaDto> todasPautas = Arrays.asList(pauta1,pauta2);

        doReturn(todasPautas)
          .when(service).buscarTodasPautas();

        mvc.perform(get("/api/pautas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome", is("Pauta1")))
                .andExpect(jsonPath("$[1].nome", is("Pauta2")));
    }
    
}
