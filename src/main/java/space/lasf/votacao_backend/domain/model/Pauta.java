package space.lasf.votacao_backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entidade que representa um produto.
 */
@Document(collection  = "pautas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pauta {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Indexed
    private String nome;

    private String descricao;

    public Pauta updateData(Pauta product) {
        this.nome = product.getNome();
        this.descricao = product.getDescricao();
        return this;
    }
}
