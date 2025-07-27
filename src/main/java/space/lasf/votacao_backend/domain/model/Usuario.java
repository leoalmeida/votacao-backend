package space.lasf.votacao_backend.domain.model;

import java.io.Serial;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection  = "usuarios")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String email;
    private String login;
    private String password;
    
}
