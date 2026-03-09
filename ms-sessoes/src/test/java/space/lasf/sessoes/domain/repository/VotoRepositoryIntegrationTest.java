package space.lasf.sessoes.domain.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import space.lasf.sessoes.domain.model.Voto;
import space.lasf.sessoes.domain.model.VotoOpcao;

@Testcontainers(disabledWithoutDocker = true)
@DataJpaTest(properties = {"spring.flyway.enabled=false", "spring.jpa.hibernate.ddl-auto=create-drop"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Tag("docker")
class VotoRepositoryIntegrationTest {

    @Container
    static final MySQLContainer<?> MYSQL = new MySQLContainer<>("mysql:8.4.4");

    @DynamicPropertySource
    static void mysqlProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MYSQL::getJdbcUrl);
        registry.add("spring.datasource.username", MYSQL::getUsername);
        registry.add("spring.datasource.password", MYSQL::getPassword);
    }

    @Autowired
    private VotoRepository repository;

    @Test
    void deveSalvarEBuscarPorSessao() {
        Voto voto = Voto.builder()
                .idAssociado(10L)
                .idSessao(77L)
                .opcao(VotoOpcao.SIM)
                .dataVoto(LocalDateTime.now())
                .build();
        repository.save(voto);

        List<Voto> result = repository.findVotosByIdSessao(77L);

        assertFalse(result.isEmpty());
        assertEquals(77L, result.get(0).getIdSessao());
    }
}
