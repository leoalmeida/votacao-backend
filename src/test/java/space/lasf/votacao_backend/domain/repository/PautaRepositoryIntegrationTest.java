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
import space.lasf.votacao_backend.domain.model.Pauta;


//@ExtendWith(SpringExtension.class)
@DataMongoTest
@ActiveProfiles("test")
public class PautaRepositoryIntegrationTest extends TestFactory{

    @Autowired
    private PautaRepository pautaRepository;

    @Autowired 
    private MongoTemplate mongoTemplate;

    Pauta pauta1;
    Pauta pauta2;

    @BeforeEach
    public void setUp() {
        // Cria pautas para testes básicos
        pauta1 = gerarPauta();
        pauta2 = gerarPauta();
        mongoTemplate.insertAll(Arrays.asList(pauta1,pauta2));
    }

    
    @AfterEach
    void clean() {
        mongoTemplate.remove(pauta1);
        mongoTemplate.remove(pauta2);
    }

    @Test
    public void shouldBeNotEmpty() {
        assertTrue(pautaRepository.findAll().size()>0);
    }

    @Test
    void dadoPauta_quandoCriarPauta_entaoPautaPersistido() {
        // given
        Pauta pauta1 = gerarPauta();

        // when
        pautaRepository.save(pauta1);

        // then
        Optional<Pauta> retrievedPauta = pautaRepository.findById(pauta1.getId());
        assertTrue(retrievedPauta.isPresent());
        assertEquals(pauta1.getId(), retrievedPauta.get().getId());
        assertEquals(pauta1.getNome(), retrievedPauta.get().getNome());
    }
}
