package space.lasf.pautas.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import space.lasf.pautas.domain.model.Pauta;

import java.util.List;

/**
 * Repositório para a entidade Pauta.
 */
@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {

    @Query("select p from Pauta p where p.nome = ?1")
    List<Pauta> searchByNome(String nome);

}
