package space.lasf.votacao_backend.controller;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
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

import space.lasf.votacao_backend.basicos.TestFactory;
import space.lasf.votacao_backend.dto.AssociadoDto;
import space.lasf.votacao_backend.dto.mapper.AssociadoMapper;
import space.lasf.votacao_backend.service.AssociadoService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AssociadoRestControllerIntegrationTest extends TestFactory {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private AssociadoService service;

    @Test
    public void testeAssociado_quandoConsultarAssociados_thenStatus200()
      throws Exception {

        AssociadoDto joao = gerarAssociadoDto("João Silva","(11) 99999-0000");
        AssociadoDto maria = gerarAssociadoDto("Maria Santos","(21) 99999-2222");
        AssociadoDto pedro = gerarAssociadoDto("Pedro Oliveira","(31) 99999-3333");
        List<AssociadoDto> todosAssociados = Arrays.asList(joao,maria,pedro);

        when(service.buscarTodosAssociados()).thenReturn(AssociadoMapper.toListAssociadoEntity(todosAssociados));
        
        mvc.perform(get("/api/v1/associado"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].nome", is("João Silva")))
                .andExpect(jsonPath("$[1].nome", is("Maria Santos")))
                .andExpect(jsonPath("$[2].nome", is("Pedro Oliveira")));
    }
    
}
