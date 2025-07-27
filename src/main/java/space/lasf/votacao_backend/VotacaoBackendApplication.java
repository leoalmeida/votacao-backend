package space.lasf.votacao_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class VotacaoBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(VotacaoBackendApplication.class, args);
	}

}
