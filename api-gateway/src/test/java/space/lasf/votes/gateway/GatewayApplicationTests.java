package space.lasf.votes.gateway;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

class GatewayApplicationTests {

	@Test
	void mainDeveInicializarAplicacao() {
		try (MockedStatic<SpringApplication> springApplication = Mockito.mockStatic(SpringApplication.class)) {
			springApplication.when(() -> SpringApplication.run(GatewayApplication.class, new String[] { "--test" }))
					.thenReturn(null);

			GatewayApplication.main(new String[] { "--test" });

			springApplication.verify(() -> SpringApplication.run(GatewayApplication.class, new String[] { "--test" }));
		}
	}

	@Test
	void deveDeclararSpringBootApplication() {
		assertTrue(GatewayApplication.class.isAnnotationPresent(SpringBootApplication.class));
	}

	@Test
	void contextLoadsSemServidorWeb() {
		assertDoesNotThrow(() -> new SpringApplicationBuilder(GatewayApplication.class)
				.properties(
						"spring.main.web-application-type=none",
						"eureka.client.enabled=false",
						"spring.cloud.gateway.enabled=false")
				.run()
				.close());
	}

}
