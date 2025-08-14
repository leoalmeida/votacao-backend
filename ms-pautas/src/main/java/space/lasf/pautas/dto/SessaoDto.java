package space.lasf.pautas.dto;

import java.time.LocalDateTime;
import java.util.List;
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
public class SessaoDto {

    private Long id;
    private SessaoStatus status;
    private String resultado;
    List<TotalizadorOpcao> totalizadores;
    private LocalDateTime dataFimSessao;
    private LocalDateTime dataInicioSessao;
    private VotoDto votoAssociado;

}
