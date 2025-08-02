package space.lasf.votacao_backend.domain.model;


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
public class TotalizadorOpcao {
    private VotoOpcao opcaoVoto;
    private Long quantidade;
}
