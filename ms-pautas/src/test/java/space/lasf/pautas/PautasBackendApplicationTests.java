package space.lasf.pautas;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PautasBackendApplication.class)
@ActiveProfiles("test")
@TestPropertySource(properties = { 
	"logging.level.org.springframework=INFO",
    "logging.level.org.hibernate=WARN" })
class PautasBackendApplicationTests {

	@Test
	void contextLoads() {
		assertTrue(true);
	}

}
