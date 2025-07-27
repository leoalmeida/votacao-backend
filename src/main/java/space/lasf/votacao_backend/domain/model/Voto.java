package space.lasf.votacao_backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Entidade que representa um pedido.
 */
@Document(collection  = "votos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Voto {

    @Serial
    private static final long serialVersionUID = 1L;
 
    @Id
    private String id;

    @Field(name = "id-associado")
    private String idAssociado;

    @Field(name = "id-sessao")
    private String idSessao;

    @Field(name = "opcao")
    private VotoOpcao opcao;

    @Field(name = "data-voto")
    private LocalDateTime dataVoto;

}