package space.lasf.pautas.dto;

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
public class PautaDto {

    private Long id;
    private String nome;
    private String descricao;
    private String categoria;
    private Long idAssociado;
    private SessaoDto sessaoDto;

}
