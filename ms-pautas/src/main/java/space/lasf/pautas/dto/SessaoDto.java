package space.lasf.pautas.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class SessaoDto {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private SessaoStatus status;

    @Getter
    @Setter
    private String resultado;

    private List<TotalizadorOpcao> totalizadores;

    @Getter
    @Setter
    private LocalDateTime dataFimSessao;

    @Getter
    @Setter
    private LocalDateTime dataInicioSessao;

    private VotoDto votoAssociado;

    public List<TotalizadorOpcao> getTotalizadores() {
        return copyTotalizadores(totalizadores);
    }

    public void setTotalizadores(final List<TotalizadorOpcao> totalizadores) {
        this.totalizadores = copyTotalizadores(totalizadores);
    }

    public VotoDto getVotoAssociado() {
        return copyVotoDto(votoAssociado);
    }

    public void setVotoAssociado(final VotoDto votoAssociado) {
        this.votoAssociado = copyVotoDto(votoAssociado);
    }

    private static List<TotalizadorOpcao> copyTotalizadores(final List<TotalizadorOpcao> source) {
        if (source == null) {
            return null;
        }

        List<TotalizadorOpcao> copy = new ArrayList<>();
        for (TotalizadorOpcao item : source) {
            if (item == null) {
                copy.add(null);
            } else {
                copy.add(new TotalizadorOpcao(item.getOpcaoVoto(), item.getQuantidade()));
            }
        }
        return copy;
    }

    private static VotoDto copyVotoDto(final VotoDto source) {
        if (source == null) {
            return null;
        }
        VotoDto copy = new VotoDto();
        copy.setId(source.getId());
        copy.setIdAssociado(source.getIdAssociado());
        copy.setIdSessao(source.getIdSessao());
        copy.setOpcao(source.getOpcao());
        copy.setDataVoto(source.getDataVoto());
        return copy;
    }
}
