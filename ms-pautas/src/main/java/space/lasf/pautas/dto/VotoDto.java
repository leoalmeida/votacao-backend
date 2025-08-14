package space.lasf.pautas.dto;

import java.time.LocalDateTime;

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
public class VotoDto {
    private Long id;
    private Long idAssociado;
    private Long idSessao;
    private VotoOpcao opcao;
    private LocalDateTime dataVoto;
}
