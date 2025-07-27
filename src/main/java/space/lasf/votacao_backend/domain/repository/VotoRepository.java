package space.lasf.votacao_backend.domain.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import space.lasf.votacao_backend.domain.model.Voto;

/**
 * Repositório para a entidade Voto.
 */
public interface VotoRepository extends MongoRepository<Voto, String> {

    @Query("{ 'sessao.id':  ?0 }")
    List<Voto> findVotosByIdSessao(String idSessao);
}
