package space.lasf.sessoes.basicos;

import space.lasf.sessoes.domain.model.VotoOpcao;
import space.lasf.sessoes.dto.VotoDto;

public class TestFactory {

    public static final String VOTOS_API_ENDPOINT = "/v1/votos";

    public VotoDto gerarVotoDto(Long id, Long idAssociado, Long idSessao, VotoOpcao opcao) {
        return VotoDto.builder()
                .id(id)
                .idAssociado(idAssociado)
                .idSessao(idSessao)
                .opcao(opcao)
                .build();
    }
}
