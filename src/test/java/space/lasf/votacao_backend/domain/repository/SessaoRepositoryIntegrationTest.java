package space.lasf.votacao_backend.domain.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

import space.lasf.votacao_backend.basicos.TestFactory;
import space.lasf.votacao_backend.domain.model.Associado;
import space.lasf.votacao_backend.domain.model.Sessao;
import space.lasf.votacao_backend.domain.model.Pauta;


//@ExtendWith(SpringExtension.class)
@DataMongoTest
@ActiveProfiles("test")
public class SessaoRepositoryIntegrationTest extends TestFactory{

    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired 
    private MongoTemplate mongoTemplate;

    Sessao sessao1;
    Sessao sessao2;
    Sessao sessao3;
    Pauta pauta1;

    
    @BeforeEach
    public void setUp() {
        
        Associado associado = gerarAssociado(null, null);
        // Criar sessaos
        sessao1 = gerarSessao(associado,gerarPauta());
        sessao2 = gerarSessao(associado,gerarPauta());
        sessao3 = gerarSessao(associado,gerarPauta());

        mongoTemplate.insertAll(Arrays.asList(sessao1,sessao2,sessao3));
    }

    @AfterEach
    void clean() {
        mongoTemplate.remove(sessao1);
        mongoTemplate.remove(sessao2);
        mongoTemplate.remove(sessao3);
    }

    @Test
    public void shouldBeNotEmpty() {
        assertTrue(sessaoRepository.findAll().size()>0);
    }

    @Test
    void dadoSessao_quandoCriarSessao_entaoSessaoPersistido() {
        // given
        Pauta pauta1 = new Pauta();
        pauta1.setId(UUID.randomUUID().toString());
        pauta1.setNome("Produto 1");
        pauta1.setDescricao("Descrição do Produto 1");
        
        Sessao sessao1 = Sessao.builder()
                    .id(UUID.randomUUID().toString())
                    .pauta(pauta1)
                    .associado(Associado.builder()
                        .id(UUID.randomUUID().toString())
                        .nome("João Silva")
                        .build())
                    .build();
        
        // when
        sessaoRepository.save(sessao1);

        // then
        Optional<Sessao> retrievedSessao = sessaoRepository.findById(sessao1.getId());
        assertTrue(retrievedSessao.isPresent());
        assertEquals(sessao1.getId(), retrievedSessao.get().getId());
    }
}
