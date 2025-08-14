package space.lasf.pautas.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import space.lasf.pautas.basicos.TestFactory;
import space.lasf.pautas.core.util.ObjectsValidator;
import space.lasf.pautas.dto.PautaDto;
import space.lasf.pautas.service.PautaService;


//@ExtendWith(SpringExtension.class)
//@SpringBootTest
@WebMvcTest(PautaController.class) 
public class PautaControllerTest extends TestFactory{

    @Autowired
    private MockMvc mockMvc;
    
    @MockitoBean
    private ObjectsValidator<PautaDto> pautaValidator;

    @MockitoBean
    private PautaService pautaService;

    private ObjectMapper mapper = new ObjectMapper();

    private PautaDto pauta1;
    private PautaDto pauta2;
    private List<PautaDto> pautaAssets = new ArrayList<PautaDto>();

    @BeforeEach
    public void setUp() {
        
        // Cria pautas para testes
        pauta1 = gerarPautaDto();
        pauta2 = gerarPautaDto();
        PautaDto pauta3 = gerarPautaDto();
        pautaAssets.addAll(Arrays.asList(pauta1,pauta2,pauta3));
    }

    @Test
    public void deveRetornarNovoPautaCriado() throws Exception {
        // Cria pautas para testes
        PautaDto dto = gerarPautaDto();
        
        // Configura o mock
        doReturn(dto).when(pautaService).criarPauta(dto, false);

        // Executa e verifica
        mockMvc.perform(post("/v1/pautas")
                            .content(mapper.writeValueAsString(dto))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("id").value(is(dto.getId())))
                .andExpect(jsonPath("nome").value(is(dto.getNome())));

        // Verifica se o método do serviço foi chamado
        verify(pautaService, times(1)).criarPauta(dto, false);
    }

    @Test
    public void deveRetornarTodasPautas() throws Exception {
        // Configura o mock
        doReturn(Arrays.asList(pauta1, pauta2))
            .when(pautaService).buscarTodasPautas();

        // Executa e verifica
        mockMvc.perform(get(PAUTA_API_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(pauta1.getId())))
                .andExpect(jsonPath("$[0].nome", is(pauta1.getNome())))
                .andExpect(jsonPath("$[1].id", is(pauta2.getId())))
                .andExpect(jsonPath("$[1].nome", is(pauta2.getNome())));

        // Verifica se o método do serviço foi chamado
        verify(pautaService, times(1)).buscarTodasPautas();
    }

	@Test
    public void deveRetornarUmPautaAPartirDoId() throws Exception {
		// Configura entidade utilizada
        PautaDto pautaSelecionado = pautaAssets.get(new Random().nextInt(this.pautaAssets.size()));
        // Configura o mock
        doReturn(pautaSelecionado).when(pautaService).buscarPautaPorId(pautaSelecionado.getId());
		// Executa e verifica
        mockMvc.perform(get("/v1/pautas/{id}",pautaSelecionado.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(pautaSelecionado.getId())))
                .andExpect(jsonPath("$.nome", is(pautaSelecionado.getNome())))
                .andExpect(jsonPath("$.descricao", is(pautaSelecionado.getDescricao())));

        // Verifica se o método do serviço foi chamado com os parâmetros corretos
        verify(pautaService, times(1))
            .buscarPautaPorId(pautaSelecionado.getId());
	}
	
	@Test
    public void deveAlterarOPautaComIdSolicitado() throws Exception {
		// Configura entidade utilizada
        PautaDto pautaAlterada = pautaAssets.get(new Random().nextInt(this.pautaAssets.size()));
        pautaAlterada.setNome("Pauta Alterada");
        // Configura o mock
        doReturn(pautaAlterada).when(pautaService)
                .alterarPauta(1L, pautaAlterada);
        doReturn(pautaAlterada).when(pautaService)
                .buscarPautaPorId(pautaAlterada.getId());
		// Executa e verifica
        mockMvc.perform(put("/v1/pautas/{id}",pautaAlterada.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(pautaAlterada)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(pautaAlterada.getId())))
                .andExpect(jsonPath("$.nome", is(pautaAlterada.getNome())))
                .andExpect(jsonPath("$.descricao", is(pautaAlterada.getDescricao())));

        // Verifica se o método do serviço foi chamado com os parâmetros corretos
        verify(pautaService, times(1))
                .alterarPauta(1L, pautaAlterada);
        verify(pautaService, times(1))
                .buscarPautaPorId(pautaAlterada.getId());
	}

	@Test
    public void deveRemoverOPautaComIdSolicitado() throws Exception {
		// Configura entidade utilizada
        PautaDto pautaRemovido = pautaAssets.get(new Random().nextInt(this.pautaAssets.size()));
        // Configura o mock
        doNothing().when(pautaService)
            .removerPauta(pautaRemovido.getId());
		// Executa e verifica
        mockMvc.perform(delete("/v1/pautas/{id}",pautaRemovido.getId()))
                .andExpect(status().isNoContent());

        // Verifica se o método do serviço foi chamado com os parâmetros corretos
        verify(pautaService, times(1))
            .removerPauta(pautaRemovido.getId());
	}

}
