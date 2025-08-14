package space.lasf.associados.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serial;



/**
 * Entidade que representa um associado.
 */
@Entity
@Table(name = "associados")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Associado {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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