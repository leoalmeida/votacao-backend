package space.lasf.votacao_backend.domain.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import space.lasf.votacao_backend.domain.model.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String>{
    
    @Query(" { 'email': ?0 }")
    Optional<Usuario> findByEmail(String email);

    @Query(" { 'login': ?0 }")
    Optional<Usuario> findByLogin(String login);
}
