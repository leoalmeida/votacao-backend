package space.lasf.votacao_backend.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import space.lasf.votacao_backend.domain.model.Pauta;

import java.util.List;

/**
 * Repositório para a entidade Pauta.
 */
public interface PautaRepository extends MongoRepository<Pauta, String> {

    @Query("{ 'nome':  /?0/ }")
    List<Pauta> searchByNome(String nome);

}
