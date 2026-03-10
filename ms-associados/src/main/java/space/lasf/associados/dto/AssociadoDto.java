package space.lasf.associados.dto;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class AssociadoDto {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String nome;

    @Getter
    @Setter
    private String telefone;

    private Map<Long, VotoDto> votacaoAssociado;

    public Map<Long, VotoDto> getVotacaoAssociado() {
        return copyVotacaoAssociado(votacaoAssociado);
    }

    public void setVotacaoAssociado(final Map<Long, VotoDto> votacaoAssociado) {
        this.votacaoAssociado = copyVotacaoAssociado(votacaoAssociado);
    }

    private static Map<Long, VotoDto> copyVotacaoAssociado(final Map<Long, VotoDto> source) {
        if (source == null) {
            return null;
        }

        Map<Long, VotoDto> copy = new ConcurrentHashMap<>();
        for (Map.Entry<Long, VotoDto> entry : source.entrySet()) {
            copy.put(entry.getKey(), copyVotoDto(entry.getValue()));
        }
        return copy;
    }

    private static VotoDto copyVotoDto(final VotoDto source) {
        if (source == null) {
            return null;
        }

        return new VotoDto(
                source.getId(), source.getIdAssociado(), source.getIdSessao(), source.getOpcao(), source.getDataVoto());
    }
}
