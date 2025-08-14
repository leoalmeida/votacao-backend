package space.lasf.associados.controller;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import space.lasf.associados.basicos.TestFactory;
import space.lasf.associados.core.util.ObjectsValidator;
import space.lasf.associados.dto.AssociadoDto;
import space.lasf.associados.service.AssociadoService;


//@ExtendWith(SpringExtension.class)
//@SpringBootTest
@WebMvcTest(AssociadoController.class) 
public class AssociadoControllerTest extends TestFactory{

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ObjectsValidator<AssociadoDto> associadoValidator;

    @MockitoBean
    private AssociadoService associadoService;

    private ObjectMapper mapper = new ObjectMapper();

    List<AssociadoDto> associadosAssets = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        
        // Cria associados para testes
        // Prepara Mock associado1
        AssociadoDto associado1 = gerarAssociadoDto("João Silva","(11) 99999-1111");
                            
        // Prepara Mock associado2
        AssociadoDto associado2 = gerarAssociadoDto("Maria Santos","(11) 99999-2222");
        
        // Prepara Mock associado3
        AssociadoDto associado3 = gerarAssociadoDto("Pedro Oliveira","(11) 99999-3333");

        associadosAssets.add(associado1);
        associadosAssets.add(associado2);
        associadosAssets.add(associado3);
    }

    @Test
    public void deveRetornarNovoAssociadoCriado() throws Exception {
        
        // Cria associados para testes
        AssociadoDto dto = gerarAssociadoDto("Associado Novo","(33) 9999-9999");

        // Configura o mock
        doReturn(dto).when(associadoService).criarAssociado(dto);

        // Executa e verifica
        mockMvc.perform(post(ASSOCIADOS_API_ENDPOINT)
                            .content(mapper.writeValueAsString(dto))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("id").value(is(dto.getId())))
                .andExpect(jsonPath("nome").value(is(dto.getNome())));

        // Verifica se o método do serviço foi chamado
        verify(associadoService, times(1)).criarAssociado(dto);
    }

    @Test
    public void deveRetornarTodosAssociados() throws Exception {
        // Configura o mock
        doReturn(associadosAssets)
            .when(associadoService).buscarTodosAssociados();

        // Executa e verifica
        mockMvc.perform(get(ASSOCIADOS_API_ENDPOINT))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(associadosAssets.get(0).getId())))
                .andExpect(jsonPath("$[0].nome", is(associadosAssets.get(0).getNome())))
                .andExpect(jsonPath("$[1].id", is(associadosAssets.get(1).getId())))
                .andExpect(jsonPath("$[1].nome", is(associadosAssets.get(1).getNome())))
                .andExpect(jsonPath("$[2].id", is(associadosAssets.get(2).getId())))
                .andExpect(jsonPath("$[2].nome", is(associadosAssets.get(2).getNome())));

        // Verifica se o método do serviço foi chamado
        verify(associadoService, times(1)).buscarTodosAssociados();
    }

    @Test
    public void deveRetornarUmAssociadoAPartirDoIdAssociado() throws Exception {
        AssociadoDto associadoConsultado = associadosAssets.get(new Random().nextInt(this.associadosAssets.size()));
		// Configura o mock
        doReturn(Optional.of(associadoConsultado)).when(associadoService).buscarAssociadoPorId(associadoConsultado.getId());
		// Executa e verifica
        mockMvc.perform(get("/api/associados/{id}",associadoConsultado.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(associadoConsultado.getId())))
                .andExpect(jsonPath("$.nome", is(associadoConsultado.getNome())))
                .andExpect(jsonPath("$.email", is(associadoConsultado.getEmail())));

        // Verifica se o método do serviço foi chamado com os parâmetros corretos
        verify(associadoService, times(1))
                .buscarAssociadoPorId(associadoConsultado.getId());
	}

    @Test
    public void deveRetornarUmAssociadoAPartirDoEmailAssociado() throws Exception {
		// Configura o mock
        AssociadoDto associadoConsultado = associadosAssets.get(new Random().nextInt(this.associadosAssets.size()));
        doReturn(Optional.of(associadoConsultado)).when(associadoService)
                .buscarAssociadoPorEmail(associadoConsultado.getEmail());
        doReturn(true).when(associadoService)
                .validarEmailAssociado(associadoConsultado.getEmail());
		// Executa e verifica
        mockMvc.perform(get("/api/associados/email/{email}", associadoConsultado.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(associadoConsultado.getId())))
                .andExpect(jsonPath("$.nome", is(associadoConsultado.getNome())))
                .andExpect(jsonPath("$.email", is(associadoConsultado.getEmail())));

        // Verifica se o método do serviço foi chamado com os parâmetros corretos
        verify(associadoService, times(1))
            .buscarAssociadoPorEmail(associadoConsultado.getEmail());
        verify(associadoService, times(1))
            .validarEmailAssociado(associadoConsultado.getEmail());
	}

    @Test
    public void devePesquisarAssociadosAPartirDoNomeAssociado() throws Exception {
		// Configura o mock
        AssociadoDto associadoConsultado = associadosAssets.get(new Random().nextInt(this.associadosAssets.size()));
		String pesquisa = associadoConsultado.getNome().split(" ")[0];
        doReturn(Arrays.asList(associadoConsultado)).when(associadoService)
            .buscarAssociadosPorNome(pesquisa);
		// Executa e verifica
        mockMvc.perform(get("/api/associados/pesquisar")
                    .param("nome",pesquisa))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(associadoConsultado.getId())))
                .andExpect(jsonPath("$[0].nome", is(associadoConsultado.getNome())))
                .andExpect(jsonPath("$[0].email", is(associadoConsultado.getEmail())));

        // Verifica se o método do serviço foi chamado com os parâmetros corretos
        verify(associadoService, times(1))
            .buscarAssociadosPorNome(pesquisa);
	}
    
    @Test
    public void deveAlterarAssociadoAPartirDoIdAssociado() throws Exception {
    	// Configura o mock
        AssociadoDto associadoAlterado = associadosAssets.get(new Random().nextInt(this.associadosAssets.size()));
		associadoAlterado.setNome("Nome Alterado");
        associadoAlterado.setTelefone("(55) 88888-6565");
        doReturn(associadoAlterado).when(associadoService)
            .alterarAssociado(associadoAlterado);
		// Executa e verifica
        mockMvc.perform(put("/api/associados/{id}",associadoAlterado.getId())
                            .content(mapper.writeValueAsString(associadoAlterado))
                            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(associadoAlterado.getId())))
                .andExpect(jsonPath("$.nome", is(associadoAlterado.getNome())))
                .andExpect(jsonPath("$.email", is(associadoAlterado.getEmail())));

        // Verifica se o método do serviço foi chamado com os parâmetros corretos
        verify(associadoService, times(1)).alterarAssociado(associadoAlterado);
	}

    @Test
    public void deveRemoverAssociadoAPartirDoIdAssociado() throws Exception {
    	// Configura o mock
        AssociadoDto associadoRemovido = associadosAssets.get(new Random().nextInt(this.associadosAssets.size()));
		doNothing().when(associadoService)
            .removerAssociado(associadoRemovido.getId());
		// Executa e verifica
        mockMvc.perform(delete("/api/associados/{id}",associadoRemovido.getId()))
                .andExpect(status().isNoContent());

        // Verifica se o método do serviço foi chamado com os parâmetros corretos
        verify(associadoService, times(1)).removerAssociado(associadoRemovido.getId());
	}

    @Test
    public void deveValidarSeUmEmailPossuiFormatoValido() throws Exception {
		// Configura o mock
        AssociadoDto associadoValido = associadosAssets.get(new Random().nextInt(this.associadosAssets.size()));
		doReturn(true).when(associadoService)
            .validarEmailAssociado(associadoValido.getEmail());
		// Executa e verifica
        mockMvc.perform(post("/api/associados/validar-email")
                            .param("email", associadoValido.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", is(true)));

        // Verifica se o método do serviço foi chamado com os parâmetros corretos
        verify(associadoService, times(1))
            .validarEmailAssociado(associadoValido.getEmail());
	}

}
