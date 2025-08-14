package space.lasf.pautas.basicos;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import space.lasf.pautas.PautasBackendApplication;
import space.lasf.pautas.controller.PautaController;

@SpringBootTest
@ContextConfiguration(classes = PautasBackendApplication.class)
@ActiveProfiles("test")
public class SmokeTest {

	@Autowired
	private PautaController pautaController;

	@Test
	void contextLoads() throws Exception {
		assertThat(pautaController).isNotNull();
	}
}