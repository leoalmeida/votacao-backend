package space.lasf.votacao_backend.domain.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

import space.lasf.votacao_backend.basicos.TestFactory;
import space.lasf.votacao_backend.domain.model.Usuario;

//@ExtendWith(SpringExtension.class)
@DataMongoTest
@ActiveProfiles("test")
public class UsuarioRepositoryIntegrationTest extends TestFactory{
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired 
    private MongoTemplate mongoTemplate;

    Usuario usuario1;
    Usuario usuario2;

    @BeforeEach
    public void setUp() {
        // Cria usuarios para testes básicos
        usuario1 = gerarUsuario("João Silva", "(11) 99999-1111");
        usuario2 = gerarUsuario("Maria Santos", "(21) 99999-2222");
        mongoTemplate.insertAll(Arrays.asList(usuario1,usuario2));
    }

    
    @AfterEach
    void clean() {
        mongoTemplate.remove(usuario1);
        mongoTemplate.remove(usuario2);
    }

    @Test
    void givenUserEntity_whenSaveUser_thenUserIsPersisted() {
        // given
        Usuario usuario = gerarUsuario("Pedro Oliveira", "(31) 99999-3333");

        // when
        usuarioRepository.save(usuario);

        // then
        Optional<Usuario> retrievedUser = usuarioRepository.findById(usuario.getId());
        assertTrue(retrievedUser.isPresent());
        assertEquals("userName", retrievedUser.get().getLogin());
    }

}
