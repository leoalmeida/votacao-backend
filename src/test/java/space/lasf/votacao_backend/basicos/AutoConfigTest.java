package space.lasf.votacao_backend.basicos;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import space.lasf.votacao_backend.VotacaoBackendApplication;
import space.lasf.votacao_backend.domain.model.Associado;
import space.lasf.votacao_backend.domain.model.Sessao;
import space.lasf.votacao_backend.domain.model.Voto;
import space.lasf.votacao_backend.domain.model.Pauta;
import space.lasf.votacao_backend.domain.model.Usuario;


@DataMongoTest
@ContextConfiguration(classes = VotacaoBackendApplication.class)
@ActiveProfiles("test")
public class AutoConfigTest {
  @Test
  void example(@Autowired final MongoTemplate mongoTemplate) {
    assertThat(mongoTemplate.getDb()).isNotNull();
      mongoTemplate.dropCollection(Associado.class);
      mongoTemplate.dropCollection(Voto.class);
      mongoTemplate.dropCollection(Sessao.class);
      mongoTemplate.dropCollection(Pauta.class);
      mongoTemplate.dropCollection(Usuario.class);
  }
}