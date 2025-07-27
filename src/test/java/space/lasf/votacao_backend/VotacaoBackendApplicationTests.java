package space.lasf.votacao_backend;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = VotacaoBackendApplication.class)
@ActiveProfiles("test")
@TestPropertySource(properties = { 
	"logging.level.org.springframework=INFO",
    "logging.level.org.hibernate=WARN" })
class VotacaoBackendApplicationTests {

	@Test
	void contextLoads() {
		assertTrue(true);
	}

}
