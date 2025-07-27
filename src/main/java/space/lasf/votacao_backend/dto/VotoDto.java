package space.lasf.votacao_backend.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import space.lasf.votacao_backend.domain.model.Sessao;
import space.lasf.votacao_backend.domain.model.VotoOpcao;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VotoDto {

    private String id;
    private String idAssociado;
    private String idSessao;
    private VotoOpcao opcao;
    private LocalDateTime dataVoto;
    private Sessao sessao;

}
