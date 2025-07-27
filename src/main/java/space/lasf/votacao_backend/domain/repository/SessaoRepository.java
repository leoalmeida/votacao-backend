package space.lasf.votacao_backend.domain.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import space.lasf.votacao_backend.domain.model.Sessao;

/**
 * Repositório para a entidade Sessao.
 */
public interface SessaoRepository extends MongoRepository<Sessao, String> {

    @Query("{ 'votos': {$exists: true, $ne: [] }}")
    List<Sessao> findSessaoWithVotos();

    @Query("{ 'associado.id':  ?0 }")
    List<Sessao> findSessoesWithIdAssociado(String idAssociado);
}
