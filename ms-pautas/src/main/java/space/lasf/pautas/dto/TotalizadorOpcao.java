package space.lasf.pautas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TotalizadorOpcao {
    private VotoOpcao opcaoVoto;
    private Long quantidade;
}
