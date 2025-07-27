package space.lasf.votacao_backend.domain.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

import space.lasf.votacao_backend.basicos.TestFactory;
import space.lasf.votacao_backend.domain.model.Associado;

//@ExtendWith(SpringExtension.class)
@DataMongoTest
@ActiveProfiles("test")
public class AssociadoRepositoryIntegrationTest extends TestFactory{

    @Autowired
    private AssociadoRepository associadoRepository;

    @Autowired 
    private MongoTemplate mongoTemplate;
    
    Associado associado1;
    Associado associado2;
    Associado associado3;

    @BeforeEach
    public void setUp() {
        // Criar pedidos
        associado1 = gerarAssociado("João Silva", "(11) 99999-1111");
        associado2 = gerarAssociado("Maria Santos", "(21) 99999-2222");
        associado3 = gerarAssociado("Pedro Oliveira", "(31) 99999-3333");

        //Pedido pedido3 = 

        mongoTemplate.insertAll(Arrays.asList(associado1,associado2,associado3));
    }

    @AfterEach
    void clean() {
        mongoTemplate.remove(associado1);
        mongoTemplate.remove(associado2);
        mongoTemplate.remove(associado3);
        //mongoTemplate.remove(pedido);
    }

    @Test
    public void shouldBeNotEmpty() {
        assertTrue(associadoRepository.findAll().size()>0);
    }

    @Test
    void dadoAssociado_quandoCriarAssociado_entaoAssociadoPersistido() {
        // given
        Associado associado4 = gerarAssociado("Joaquim Nabuco", "(41) 99999-4444");

        // when
        associadoRepository.save(associado4);

        // then
        Optional<Associado> retrievedAssociado = associadoRepository.findById(associado4.getId());
        assertTrue(retrievedAssociado.isPresent());
        assertEquals(associado4.getId(), retrievedAssociado.get().getId());
        assertEquals("Joaquim Nabuco", retrievedAssociado.get().getNome());
    }
}
