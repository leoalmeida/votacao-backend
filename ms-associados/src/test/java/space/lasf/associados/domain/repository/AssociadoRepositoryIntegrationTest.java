package space.lasf.associados.domain.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import org.springframework.test.context.ActiveProfiles;

import space.lasf.associados.basicos.TestFactory;
import space.lasf.associados.domain.model.Associado;

//@ExtendWith(SpringExtension.class)
@DataMongoTest
@ActiveProfiles("test")
public class AssociadoRepositoryIntegrationTest extends TestFactory{

    @Autowired
    private AssociadoRepository associadoRepository;

    
    Associado associado1;
    Associado associado2;
    Associado associado3;

    @BeforeEach
    public void setUp() {
        // Criar pedidos
        associado1 = new Associado(1L, "João Silva", "teste@teste.com","(11) 99999-1111");
        associado2 = new Associado(2L, "Maria Santos", "teste@teste.com","(21) 99999-2222");
        associado3 = new Associado(3L, "Pedro Oliveira", "teste@teste.com","(31) 99999-3333");

        //Pedido pedido3 = 

        associadoRepository.saveAll(Arrays.asList(associado1,associado2,associado3));
    }

    @AfterEach
    void clean() {
        associadoRepository.delete(associado1);
        associadoRepository.delete(associado2);
        associadoRepository.delete(associado3);
        //mongoTemplate.remove(pedido);
    }

    @Test
    public void shouldBeNotEmpty() {
        assertTrue(associadoRepository.findAll().size()>0);
    }

    @Test
    void dadoAssociado_quandoCriarAssociado_entaoAssociadoPersistido() {
        // given
        Associado associado4 = new Associado(null, "Joaquim Nabuco", "teste@teste.com","(41) 99999-4444");

        // when
        associadoRepository.save(associado4);

        // then
        Optional<Associado> retrievedAssociado = associadoRepository.findById(associado4.getId());
        assertTrue(retrievedAssociado.isPresent());
        assertEquals(associado4.getId(), retrievedAssociado.get().getId());
        assertEquals("Joaquim Nabuco", retrievedAssociado.get().getNome());
    }
}
