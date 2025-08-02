package space.lasf.votacao_backend.basicos;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.text.MessageFormat;
import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import space.lasf.votacao_backend.domain.model.Associado;

@DataMongoTest
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class SimpleTest extends TestFactory{

	@Autowired 
    private MongoTemplate mongoTemplate;

	Associado associado1;
    Associado associado2;
    Associado associado3;

	@BeforeEach
	void setUp() throws Exception {
		System.out.println("Preparando dados que serão utilizados nos testes.");
		associado1 = gerarAssociado("João Silva", "(11) 99999-1111");
        associado2 = gerarAssociado("Maria Santos", "(21) 99999-2222");
        associado3 = gerarAssociado("Pedro Oliveira", "(31) 99999-3333");
        mongoTemplate.insertAll(Arrays.asList(associado1,associado2,associado3));
		long entitySize = mongoTemplate.count(new Query(), Associado.class);
		System.out.println(MessageFormat.format("Colection Associado possui {0} registros.", entitySize));
	}

	@AfterEach
	void tearDown() throws Exception {
		System.out.println("Removendo dados utilizados no teste.");
		mongoTemplate.remove(associado1);
        mongoTemplate.remove(associado2);
        mongoTemplate.remove(associado3);
		long entitySize = mongoTemplate.count(new Query(), Associado.class);
		System.out.println(MessageFormat.format("Colection Associado possui {0} registros.", entitySize));
	}


	@Test
	void contextLoads() throws Exception {
		assumeTrue(associado1!=null);
		System.out.println("Executando SimpleTest.");
	}
}