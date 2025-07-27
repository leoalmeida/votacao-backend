package space.lasf.votacao_backend.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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

import space.lasf.votacao_backend.basicos.TestFactory;
import space.lasf.votacao_backend.core.util.ObjectsValidator;
import space.lasf.votacao_backend.domain.model.Pauta;
import space.lasf.votacao_backend.dto.PautaDto;
import space.lasf.votacao_backend.dto.mapper.PautaMapper;
import space.lasf.votacao_backend.service.PautaService;


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

    private Pauta pauta1;
    private Pauta pauta2;
    private List<Pauta> pautaAssets = new ArrayList<Pauta>();

    @BeforeEach
    public void setUp() {
        
        // Cria pautas para testes
        pauta1 = gerarPauta();
        pauta2 = gerarPauta();
        Pauta pauta3 = gerarPauta();
        pautaAssets.addAll(Arrays.asList(pauta1,pauta2,pauta3));
    }

    @Test
    public void deveRetornarNovoPautaCriado() throws Exception {
        // Cria pautas para testes
        Pauta novoPauta = gerarPauta();
        PautaDto dto = PautaMapper.toPautaDto(novoPauta);
        
        // Configura o mock
        doReturn(novoPauta).when(pautaService).criarPauta(novoPauta);

        // Executa e verifica
        mockMvc.perform(post("/api/pautas")
                            .content(mapper.writeValueAsString(dto))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("id").value(is(novoPauta.getId())))
                .andExpect(jsonPath("nome").value(is(novoPauta.getNome())));

        // Verifica se o método do serviço foi chamado
        verify(pautaService, times(1)).criarPauta(novoPauta);
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
        Pauta pautaSelecionado = pautaAssets.get(new Random().nextInt(this.pautaAssets.size()));
        // Configura o mock
        doReturn(Optional.of(pautaSelecionado)).when(pautaService).buscarPautaPorId(pautaSelecionado.getId());
		// Executa e verifica
        mockMvc.perform(get("/api/pautas/{id}",pautaSelecionado.getId()))
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
        Pauta pautaAlterada = pautaAssets.get(new Random().nextInt(this.pautaAssets.size()));
        pautaAlterada.setNome("Pauta Alterada");
        // Configura o mock
        doReturn(pautaAlterada).when(pautaService)
                .alterarPauta(pautaAlterada);
        doReturn(Optional.of(pautaAlterada)).when(pautaService)
                .buscarPautaPorId(pautaAlterada.getId());
		// Executa e verifica
        mockMvc.perform(put("/api/pautas/{id}",pautaAlterada.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(PautaMapper.toPautaDto(pautaAlterada))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(pautaAlterada.getId())))
                .andExpect(jsonPath("$.nome", is(pautaAlterada.getNome())))
                .andExpect(jsonPath("$.descricao", is(pautaAlterada.getDescricao())));

        // Verifica se o método do serviço foi chamado com os parâmetros corretos
        verify(pautaService, times(1))
                .alterarPauta(pautaAlterada);
        verify(pautaService, times(1))
                .buscarPautaPorId(pautaAlterada.getId());
	}

	@Test
    public void deveRemoverOPautaComIdSolicitado() throws Exception {
		// Configura entidade utilizada
        Pauta pautaRemovido = pautaAssets.get(new Random().nextInt(this.pautaAssets.size()));
        // Configura o mock
        doNothing().when(pautaService)
            .removerPauta(pautaRemovido.getId());
		// Executa e verifica
        mockMvc.perform(delete("/api/pautas/{id}",pautaRemovido.getId()))
                .andExpect(status().isNoContent());

        // Verifica se o método do serviço foi chamado com os parâmetros corretos
        verify(pautaService, times(1))
            .removerPauta(pautaRemovido.getId());
	}

}
