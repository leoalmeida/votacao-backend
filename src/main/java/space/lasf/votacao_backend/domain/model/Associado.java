package space.lasf.votacao_backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;



/**
 * Entidade que representa um associado.
 */
@Document(collection  = "associados")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Associado {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;

    @TextIndexed
    private String nome;

    private String email;

    private String telefone;

    public Associado updateData(Associado associado) {
        this.nome = associado.getNome();
        this.email = associado.getEmail();
        this.telefone = associado.getTelefone();
        return this;
    }

}