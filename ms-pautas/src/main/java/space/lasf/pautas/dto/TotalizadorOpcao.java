package space.lasf.pautas.dto;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public class TotalizadorOpcao {
    private VotoOpcao opcaoVoto;
    private Long quantidade;
}
