package space.lasf.pautas.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class PautaDto {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String nome;

    @Getter
    @Setter
    private String descricao;

    @Getter
    @Setter
    private String categoria;

    @Getter
    @Setter
    private Long idAssociado;

    private SessaoDto sessaoDto;

    public SessaoDto getSessaoDto() {
        return copySessaoDto(sessaoDto);
    }

    public void setSessaoDto(final SessaoDto sessaoDto) {
        this.sessaoDto = copySessaoDto(sessaoDto);
    }

    private static SessaoDto copySessaoDto(final SessaoDto source) {
        if (source == null) {
            return null;
        }
        SessaoDto copy = new SessaoDto();
        copy.setId(source.getId());
        copy.setStatus(source.getStatus());
        copy.setResultado(source.getResultado());
        copy.setTotalizadores(source.getTotalizadores());
        copy.setDataFimSessao(source.getDataFimSessao());
        copy.setDataInicioSessao(source.getDataInicioSessao());
        copy.setVotoAssociado(source.getVotoAssociado());
        return copy;
    }
}
