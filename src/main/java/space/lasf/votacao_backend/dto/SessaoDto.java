package space.lasf.votacao_backend.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import space.lasf.votacao_backend.domain.model.TotalizadorOpcao;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SessaoDto {

    private String id;
    private String status;
    private String resultado;
    @Singular(value = "totalizadores")
    List<TotalizadorOpcao> totalizadores;
    private LocalDateTime dataFimSessao;
    private LocalDateTime dataInicioSessao;
    private PautaDto pautaDto;

}
