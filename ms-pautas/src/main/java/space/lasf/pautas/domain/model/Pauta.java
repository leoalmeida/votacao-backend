package space.lasf.pautas.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serial;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Entidade que representa um produto.
 */
@Entity
@Table(name = "pautas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pauta {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(name = "associado_id")
    @NotNull(message = "O ID do associado não pode ser nulo")
    //@NotBlank(message = "O ID do associado não pode ser vazio")
    private Long idAssociado;

    @Column(name = "sessao_id")
    private Long idSessao;

    private String nome;
    private String descricao;
    private String categoria;

    public Pauta updateData(Pauta pauta) {
        this.nome = pauta.getNome();
        this.descricao = pauta.getDescricao();
        return this;
    }
}
