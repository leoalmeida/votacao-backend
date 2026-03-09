package space.lasf.associados.domain.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import space.lasf.associados.domain.model.Associado;

/**
 * Repositório para a entidade de Associado.
 */
@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Long> {

    @Query("select a from Associado a where a.email = ?1")
    Optional<Associado> findByEmail(String email);

    @Query("select a from Associado a where a.nome = ?1")
    List<Associado> findByNomeContaining(String nome);
}
