package space.lasf.votacao_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssociadoDto {

    private String id;
    private String email;
    private String nome;
    private String telefone;

}
