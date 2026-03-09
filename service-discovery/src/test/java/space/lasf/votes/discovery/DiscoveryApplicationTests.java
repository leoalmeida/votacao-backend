package space.lasf.votes.discovery;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

class DiscoveryApplicationTests {

	@Test
	void mainDeveInicializarAplicacao() {
		try (MockedStatic<SpringApplication> springApplication = Mockito.mockStatic(SpringApplication.class)) {
			springApplication.when(() -> SpringApplication.run(DiscoveryApplication.class, new String[] { "--test" }))
					.thenReturn(null);

			DiscoveryApplication.main(new String[] { "--test" });

			springApplication.verify(() -> SpringApplication.run(DiscoveryApplication.class, new String[] { "--test" }));
		}
	}

	@Test
	void deveDeclararAnotacoesPrincipais() {
		assertTrue(DiscoveryApplication.class.isAnnotationPresent(SpringBootApplication.class));
		assertTrue(DiscoveryApplication.class.isAnnotationPresent(EnableEurekaServer.class));
	}

	@Test
	void contextLoadsSemServidorWeb() {
		assertDoesNotThrow(() -> new SpringApplicationBuilder(DiscoveryApplication.class)
				.properties(
						"spring.main.web-application-type=servlet",
						"server.port=0",
						"eureka.client.register-with-eureka=false",
						"eureka.client.fetch-registry=false")
				.run()
				.close());
	}

}
