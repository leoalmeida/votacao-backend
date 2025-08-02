package space.lasf.votacao_backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

import space.lasf.votacao_backend.basicos.TestFactory;
import space.lasf.votacao_backend.core.util.ObjectsValidator;
import space.lasf.votacao_backend.domain.model.Associado;
import space.lasf.votacao_backend.domain.model.Sessao;
import space.lasf.votacao_backend.domain.repository.AssociadoRepository;
import space.lasf.votacao_backend.service.AssociadoService;

//@ExtendWith(SpringExtension.class)
@DataMongoTest
@ActiveProfiles("test")
public class AssociadoServiceImplIntegrationTest extends TestFactory{

   @TestConfiguration
    static class AssociadoServiceImplTestContextConfiguration {

        @Autowired
        AssociadoRepository associadoRepository;
        
        @Bean
        public AssociadoService associadoService() {
            return new AssociadoServiceImpl(associadoRepository,validadorDeAssociado());
        }
        @Bean 
        public ObjectsValidator<Associado> validadorDeAssociado(){
            return new ObjectsValidator<Associado>();
        };
        
    }

    @Autowired
    private AssociadoService associadoService;

    @Autowired 
    private MongoTemplate mongoTemplate;

    List<Associado> baseAssets = new ArrayList<>();
    List<Associado> associadosComVotos = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        mongoTemplate.dropCollection(Associado.class);
        mongoTemplate.dropCollection(Sessao.class);
        // Prepara Mock associado1
        Associado associado1 = gerarAssociado("João Silva","(11) 99999-1111");
                            
        // Prepara Mock associado2
        Associado associado2 = gerarAssociado("Maria Santos","(11) 99999-2222");
        
        // Prepara Mock associado3
        Associado associado3 = gerarAssociado("Pedro Oliveira","(11) 99999-3333");

        baseAssets.add(associado1);
        baseAssets.add(associado2);
        baseAssets.add(associado3);
        associadosComVotos.add(associado1);
        associadosComVotos.add(associado3);
        mongoTemplate.insertAll(baseAssets);  
    }

    @AfterEach
    public void coolDown() {
        baseAssets.stream().forEach(mongoTemplate::remove);
    }

    @Test
    public void quandoTodosAssociadosConsultados_entaoAssociadoRepositoryRetornaQuantidadeEsperada() {
        List<Associado> todosAssociados = this.associadoService.buscarTodosAssociados();
        int assetsCount = todosAssociados.size();
        assertEquals(this.baseAssets.size(), assetsCount);
    }

    @Test
    public void quandoAssociadoValido_entaoAssociadoDeveSerSalvo() {
        // Cria novo associado
        Associado newAssociado = new Associado();
        newAssociado.setNome("Novo Associado");
        newAssociado.setEmail("novo.associado@example.com");
        newAssociado.setTelefone("(11) 99999-4444");
        
        // Executa o serviço testado
        Associado associadoSalvo = associadoService.criarAssociado(newAssociado);

        // Verifica o resultado
        assertNotNull(associadoSalvo, "Novo associado não deve retornar nulo");
        assertNotNull(associadoSalvo.getId(), "Novo associado deve ter um ID válido");
        assertEquals("Novo Associado", associadoSalvo.getNome(), "Novo associado deve ter o nome correto");
        assertEquals("novo.associado@example.com", associadoSalvo.getEmail(), "Novo associado deve ter o e-mail correto");

        // Verifica se o associado foi realmente salvo no banco de dados
        Optional<Associado> foundAssociado = associadoService.buscarAssociadoPorId(associadoSalvo.getId());
        assertTrue(foundAssociado.isPresent(), "Associado deveria ser encontrado no banco de dados");
    }

    @Test
    public void quandoAssociadoComEmailInvalido_entaoErroDeveSerRetornado() {
        // Novo associado com e-mail inválido
        Associado mockEmailInvalido = new Associado();
        mockEmailInvalido.setNome("Associado Inválido");
        mockEmailInvalido.setEmail("email-invalido");
        mockEmailInvalido.setTelefone("(11) 99999-5555");

        // Executa o método
        Throwable  throwable  = 
            assertThrows(IllegalArgumentException.class, () ->{
                // Executa o método - deveria falhar para emails inválidos
                associadoService.criarAssociado(mockEmailInvalido);
        
                // Uma exceção deveria ter sido lançada ao salvar um associado com email inválido
                assertTrue(true, "Exception não lançada ao salvar associado com email inválido");
            });

        
        // O teste deve falhar caso não seja gerada uma exception
        assertEquals(IllegalArgumentException.class, throwable.getClass(), 
                            "Exception gerada não é relacionada ao erro de email:" + throwable.getMessage());
        assertEquals("Email inválido: " + mockEmailInvalido.getEmail(), throwable.getMessage());
    }

    @Test
    public void fornecidoIdAssociado_aoConsultarAssociados_entaoRetornaAssociadoComSucesso() {
        Associado associado = baseAssets.get(0);
        // Executa o método
        Optional<Associado> foundAssociado = associadoService.buscarAssociadoPorId(associado.getId());

        // Verifica o resultado
        assertTrue(foundAssociado.isPresent(), "Associado deveria ser encontrado");
        assertEquals(associado.getId(), foundAssociado.get().getId(), "Associado encontrado deveria ter o ID correto");
        assertEquals(associado.getNome(), foundAssociado.get().getNome(), "Associado encontrado deveria ter o nome correto");
    }

    @Test
    public void fornecidoEmailAssociado_aoConsultarAssociados_entaoRetornaAssociadoComSucesso() {
        Associado associadoConsultado = baseAssets.get(new Random().nextInt(this.baseAssets.size()));
        
        // Executa o método
        Optional<Associado> foundAssociado = associadoService.buscarAssociadoPorEmail(associadoConsultado.getEmail());

        // Verifica o resultado
        assertTrue(foundAssociado.isPresent(), "Associado deveria ser encontrado");
        assertEquals(associadoConsultado.getId(), foundAssociado.get().getId(), "Associado encontrado deveria ter o e-mail correto");
        assertEquals(associadoConsultado.getEmail(), foundAssociado.get().getEmail(), "Associado encontrado deveria ter o e-mail correto");
        assertEquals(associadoConsultado.getNome(), foundAssociado.get().getNome(), "Associado encontrado deveria ter o nome correto");
    }

    @Test
    public void aoConsultarTodosAssociados_entaoRetornaListaAssociadosComSucesso() {
        
        // Executa o método
        List<Associado> associados = associadoService.buscarTodosAssociados();

        // Verifica o resultado
        assertEquals(baseAssets.size(), associados.size(), "Deveria encontrar 3 associados");
    }

    @Test
    public void fornecidoNomeAssociado_aoConsultarAssociados_entaoRetornaAssociadoComSucesso() {
        Associado associadoConsultado = baseAssets.get(new Random().nextInt(this.baseAssets.size()));
        // Executa o método
        List<Associado> associados = associadoService.buscarAssociadosPorNome(associadoConsultado.getNome().split(" ")[0]);

        // Verifica o resultado
        assertEquals(1, associados.size(), "Deveria encontrar 1 associado");
        assertEquals(associadoConsultado.getNome(), associados.get(0).getNome(), "Associado encontrado deveria ser João Silva");
    }

    @Test
    public void fornecidoInjecaoSQLNoNomeAssociado_aoConsultarAssociados_entaoRetornaException() {
        // Executa o método com uma tentativa de injeção SQL
    
        // Se ocorrer uma exceção, o teste deve falhar com uma mensagem clara
        // em vez de deixar a exceção se propagar
        
        assertDoesNotThrow(() ->{
            // Caso a consulta seja vulnerável, retornará todos os associados
            List<Associado> associados = associadoService.buscarAssociadosPorNome("' OR '1'='1");
            // Verificamos se a consulta retornou uma lista vazia senão é uma falha
            assertTrue(associados.isEmpty(), "A consulta não deveria ser vulnerável a injeção de SQL");       
        },"O teste deveria falhar com uma asserção e não com uma exceção.:");
    
    }

    @Test
    public void fornecidoAssociadoAtualizado_aoAlterarAssociado_entaoRetornaSucesso() {
        Associado associadoBase = baseAssets.get(new Random().nextInt(this.baseAssets.size()));
        Associado associadoAutualizado = new Associado();
        associadoAutualizado.updateData(associadoBase);
        associadoAutualizado.setId(associadoBase.getId());
        // Modifica o associado
        associadoAutualizado.setNome("Nome Atualizado");
        associadoAutualizado.setEmail("nome.atualizado@example.com");

        // Executa o método
        associadoService.alterarAssociado(associadoAutualizado);

        // Verifica se o associado foi atualizado no banco de dados
        Optional<Associado> updatedAssociado = associadoService.buscarAssociadoPorId(associadoBase.getId());
        assertTrue(updatedAssociado.isPresent(), "Associado deveria ser encontrado");
        assertEquals("Nome Atualizado", updatedAssociado.get().getNome(), "Nome do associado deveria ser atualizado");
        assertEquals("nome.atualizado@example.com", updatedAssociado.get().getEmail(), "E-mail do associado deveria ser atualizado");
    }

    @Test
    public void testeRemoverAssociado() {
        Associado associadoBase = baseAssets.get(new Random().nextInt(this.baseAssets.size()));
        // Executa o método
        associadoService.removerAssociado(associadoBase.getId());

        // Verifica se o associado foi removido do banco de dados
        Optional<Associado> deletedAssociado = associadoService.buscarAssociadoPorId(associadoBase.getId());
        assertFalse(deletedAssociado.isPresent(), "Associado deveria ser removido");
    }

    @Test
    public void testeRemoverAssociadoComPedidos() {
        Associado associadoBase = associadosComVotos.get(new Random().nextInt(this.associadosComVotos.size()));
        
        // Executa o método
        associadoService.removerAssociado(associadoBase.getId());

        Optional<Associado> deletedAssociado = associadoService.buscarAssociadoPorId(associadoBase.getId());
        // Verifica se o associado foi removido do banco de dados
        
        assertFalse(deletedAssociado.isPresent(), "Associado com sessaos deveria ser removido");
    }

    @Test
    public void testeValidarAssociadoEmail() {
        // Testa e-mail válido
        boolean isValid = associadoService.validarEmailAssociado("joao.silva@example.com");
        assertTrue(isValid, "E-mail deveria ser considerado válido");

        // Testa e-mail inválido
        isValid = associadoService.validarEmailAssociado("email-invalido");
        assertFalse(isValid,"E-mail deveria ser considerado inválido");

        // Testa e-mail nulo
        isValid = associadoService.validarEmailAssociado(null);
        assertFalse(isValid,"E-mail nulo deveria ser considerado inválido");

        // Testa e-mail vazio
        isValid = associadoService.validarEmailAssociado("");
        assertFalse(isValid, "E-mail vazio deveria ser considerado inválido");
    }

    @Test
    public void testeValidarEmailAssociadoUtilizandoPadrao() {
        // Testa e-mail com formato válido mas domínio inválido
        boolean isValid = associadoService.validarEmailAssociado("joao.silva@dominio-invalido");
        assertFalse(isValid, "E-mail com domínio inválido não deveria ser aceito");

        // Testa e-mail com formato válido mas sem TLD
        isValid = associadoService.validarEmailAssociado("joao.silva@dominio");
        assertFalse(isValid, "E-mail sem TLD não deveria ser aceito");
    }
}