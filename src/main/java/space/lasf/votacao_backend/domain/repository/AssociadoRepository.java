package space.lasf.votacao_backend.domain.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import space.lasf.votacao_backend.domain.model.Associado;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para a entidade de Associado.
 */
public interface AssociadoRepository extends MongoRepository<Associado, String> {

    @Query(" { 'email': ?0 }")
    Optional<Associado> findByEmail(String email);

    @Query(" { 'nome': /?0/ }")
    List<Associado> findByNomeContaining(String nome);
    
}