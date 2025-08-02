package space.lasf.votacao_backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
import space.lasf.votacao_backend.domain.model.SessaoStatus;
import space.lasf.votacao_backend.domain.model.Voto;
import space.lasf.votacao_backend.domain.model.VotoOpcao;
import space.lasf.votacao_backend.domain.model.Pauta;
import space.lasf.votacao_backend.domain.repository.AssociadoRepository;
import space.lasf.votacao_backend.domain.repository.SessaoRepository;
import space.lasf.votacao_backend.domain.repository.PautaRepository;
import space.lasf.votacao_backend.service.SessaoService;
import space.lasf.votacao_backend.service.PautaService;

@DataMongoTest
@ActiveProfiles("test")
public class SessaoServiceImplIntegrationTest extends TestFactory{

    @TestConfiguration
    static class SessaoServiceImplTestContextConfiguration {

        @Autowired
        PautaRepository pautaRepository;

        @Autowired
        AssociadoRepository associadoRepository;
        
        @Autowired
        SessaoRepository votoRepository;
        
        @Autowired
        SessaoRepository sessaoRepository; 

        @Bean
        public SessaoService sessaoService() {
            return new SessaoServiceImpl(associadoRepository, votoRepository, sessaoRepository);
        }
        @Bean
        public PautaService pautaService() {
            return new PautaServiceImpl();
        }
        @Bean 
        public ObjectsValidator<Sessao> validadorDeItem(){
            return new ObjectsValidator<Sessao>();
        };

        @Bean 
        public ObjectsValidator<Pauta> validadorDePauta(){
            return new ObjectsValidator<Pauta>();
        };
    }

    @Autowired
    private SessaoService sessaoService;
    
    
    @Autowired 
    private MongoTemplate mongoTemplate;

    Associado associado1;
    Associado associado2;
    Pauta pauta;
    Sessao sessao;

    @BeforeEach
    public void setUp() {
        mongoTemplate.dropCollection(Associado.class);
        mongoTemplate.dropCollection(Pauta.class);
        mongoTemplate.dropCollection(Sessao.class);
        mongoTemplate.dropCollection(Sessao.class);
        // Cria associados para testes
        associado1 = gerarAssociado("João Silva","(11) 99999-1111");
        associado2 = gerarAssociado("Joana Maia","(31) 99999-1111");
        
        // Cria pauta para testes
        pauta = gerarPauta();
        
        // Cria sessao para testes
        sessao = gerarSessao(associado1,pauta);
        
        mongoTemplate.save(associado1);
        mongoTemplate.save(associado2);
        mongoTemplate.save(sessao);
        mongoTemplate.save(pauta);
    }

    @AfterEach
    public void coolDown() {
        mongoTemplate.remove(sessao);
        mongoTemplate.remove(associado1);
        mongoTemplate.remove(associado2);
        mongoTemplate.remove(pauta);
    }

    @Test
    public void testCreateSessao() {
        // Cria lista de itens para o sessao
        Pauta novaPauta = gerarPauta();
       
        // Executa o método
        Sessao createdSessao = sessaoService.criarSessao(associado1.getId(), novaPauta);
        
        // Verifica o resultado
        assertNotNull(createdSessao,"Sessao criada não deveria ser nulo");
        assertNotNull(createdSessao.getId(),"Sessao criada deveria ter um ID");
        assertEquals(associado1.getId(), createdSessao.getAssociado().getId(),"Sessao deveria ter o associado correto");
        assertEquals(SessaoStatus.CREATED, createdSessao.getStatus(),"Status da sessao deveria estar CREATED");
        
        // Verifica se o sessao foi realmente salvo no banco de dados
        Optional<Sessao> foundSessao = sessaoService.buscarSessaoPorId(createdSessao.getId());
        assertTrue(foundSessao.isPresent(),"Sessao deveria ser encontrada no banco de dados");
        assertEquals(SessaoStatus.CREATED, foundSessao.get().getStatus(),"Status da sessao deveria estar CREATED");
    }

    @Test
    public void testIniciarSessao() {
        
        // Executa o método
        sessaoService.iniciarSessao(sessao.getId());

        // Verifica se a sessão foi atualizada
        Optional<Sessao> sessaoAtualizada = sessaoService.buscarSessaoPorId(sessao.getId());
        assertTrue(sessaoAtualizada.isPresent(), "Sessao deveria ser encontrada");
        assertEquals(sessao.getId(), sessaoAtualizada.get().getId(),"Sessao deveria ter o mesmo ID");
        assertEquals(SessaoStatus.OPEN_TO_VOTE, sessaoAtualizada.get().getStatus(),"Sessao deveria ter a mesma quantidade de items.");

    }


    @Test
    public void testFindSessaoById() {
        // Executa o método
        Optional<Sessao> foundSessao = sessaoService.buscarSessaoPorId(sessao.getId());

        // Verifica o resultado
        assertTrue(foundSessao.isPresent(), "Sessao deveria ser encontrada");
        assertEquals(sessao.getId(), foundSessao.get().getId(),
                "Sessao encontrada deveria ter o ID correto" );
        assertEquals(SessaoStatus.CREATED, foundSessao.get().getStatus(),
                    "Sessao encontrada deveria ter o número correto");
    }

    @Test
    public void testFindAllSessoes() {
        // Executa o método
        List<Sessao> sessoes = sessaoService.buscarTodasSessoes();

        // Verifica o resultado
        assertEquals(1, sessoes.size(),"Deveria encontrar 1 sessao");
        assertEquals(sessao.getId(), sessoes.get(0).getId(),
                    "Sessao encontrada deveria ter o número correto");
    }

    @Test
    public void testFindSessoesByAssociadoId() {
        // Executa o método
        List<Sessao> sessoes = sessaoService.buscarSessoesPorIdAssociado(associado1.getId());

        // Verifica o resultado
        assertEquals(1, sessoes.size(),"Deveria encontrar 1 sessao");
        assertEquals( sessao.getId(), sessoes.get(0).getId(), "Sessao encontrada deveria ter o número correto");
    }

    @Test
    public void testFinalizarSessaoAberta() {
 
        // Executa o método
        sessaoService.finalizarSessao(sessao.getId());

        // Verifica se o sessao foi finalizado
        Optional<Sessao> finalizedSessao = sessaoService.buscarSessaoPorId(sessao.getId());
        assertTrue(finalizedSessao.isPresent(),"Sessao deveria ser encontrada");
        assertEquals(SessaoStatus.CLOSED, finalizedSessao.get().getStatus(), "Status do sessao deveria ser CLOSED");
        assertEquals(VotoOpcao.NAO, finalizedSessao.get().getResultado(),"Resutado da sessao deveria estar correta");
        assertNotNull(finalizedSessao.get().getDataFimSessao(), "Data Fim deveria estar preenchida");
        assertNotNull(finalizedSessao.get().getTotalizadores(), "Totalizadores deveriam estar preenchidos");
        assertEquals(VotoOpcao.values().length,finalizedSessao.get().getTotalizadores().size(), "Deveriam ter 2 totalizadores preenchidos");

    }

    @Test
    public void testCancelarSessaoAberta() {
        // Executa o método
        sessaoService.cancelarSessao(sessao.getId());

        // Verifica se o sessao foi cancelado
        Optional<Sessao> sessaoCancelada = sessaoService.buscarSessaoPorId(sessao.getId());
        assertTrue(sessaoCancelada.isPresent(), "Sessao deveria ser encontrada");

        assertEquals(SessaoStatus.CANCELLED, sessaoCancelada.get().getStatus(), "Status do sessao deveria ser CANCELADO");
        assertNotNull(sessaoCancelada.get().getDataFimSessao(), "Data FIm deveria estar preenchida");
    }

    @Test
    public void testCreateSessaoWithInvalidAssociadoId() {
        // Executa o método - deve lançar IllegalArgumentException
        
        Throwable  throwable  = 
                assertThrows(IllegalArgumentException.class, () ->{
                    sessaoService.criarSessao(UUID.randomUUID().toString(), gerarPauta());
                });
        
        assertEquals(IllegalArgumentException.class, throwable.getClass());
    }

}