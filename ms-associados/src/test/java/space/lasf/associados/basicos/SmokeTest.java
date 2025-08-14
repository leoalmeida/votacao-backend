package space.lasf.associados.basicos;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import space.lasf.associados.AssociadosBackendApplication;
import space.lasf.associados.controller.AssociadoController;

@SpringBootTest
@ContextConfiguration(classes = AssociadosBackendApplication.class)
@ActiveProfiles("test")
public class SmokeTest {

	@Autowired
	private AssociadoController associadoController;

	@Test
	void contextLoads() throws Exception {
		assertThat(associadoController).isNotNull();
	}
}