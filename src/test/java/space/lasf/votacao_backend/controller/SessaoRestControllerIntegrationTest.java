package space.lasf.votacao_backend.controller;

import static org.mockito.Mockito.doReturn;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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

import space.lasf.votacao_backend.basicos.TestFactory;
import space.lasf.votacao_backend.domain.model.Associado;
import space.lasf.votacao_backend.domain.model.SessaoStatus;
import space.lasf.votacao_backend.domain.repository.SessaoRepository;
import space.lasf.votacao_backend.dto.SessaoDto;
import space.lasf.votacao_backend.dto.mapper.SessaoMapper;
import space.lasf.votacao_backend.service.SessaoService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SessaoRestControllerIntegrationTest extends TestFactory{

    @Autowired
    private MockMvc mvc;

    @Autowired
    private SessaoRepository repository;

    @MockitoBean
    private SessaoService service;

    @Test
    public void testePedido_quandoConsultarTodosSessoes_entaoRetornaSessoesComSucesso()
      throws Exception {

        Associado associado = Associado.builder()
                          .id(UUID.randomUUID().toString())
                          .nome("Joaquim Nabuco")
                          .email("joaquim.nabuco@exemplo.cim")
                          .telefone("(41) 99999-4444")
                          .build();
        SessaoDto sessao1 = SessaoDto.builder()
                          .status(SessaoStatus.CLOSED.name())
                          .id(UUID.randomUUID().toString()).build();
        SessaoDto sessao2 = SessaoDto.builder()
                        .status(SessaoStatus.OPEN_TO_VOTE.name())
                        .id(UUID.randomUUID().toString()).build();
        List<SessaoDto> todasSessoes = Arrays.asList(sessao1,sessao2);

        doReturn(SessaoMapper.toListSessaoEntity(todasSessoes,associado))
          .when(service).buscarTodasSessoes();

        mvc.perform(get(SESSAO_API_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(sessao1.getId())))
                .andExpect(jsonPath("$[1].id", is(sessao2.getId())));
    }
    
}
