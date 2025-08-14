package space.lasf.associados.dto;

import java.util.Map;

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

    private Long id;
    private String email;
    private String nome;
    private String telefone;
    private Map<Long,VotoDto> votacaoAssociado;

}
