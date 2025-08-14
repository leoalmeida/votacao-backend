package space.lasf.associados.basicos;

import java.text.Normalizer;

import java.util.regex.Pattern;

import space.lasf.associados.dto.AssociadoDto;
import space.lasf.associados.dto.VotoDto;
import space.lasf.associados.dto.VotoOpcao;

public class TestFactory {

    public static final String ASSOCIADOS_API_ENDPOINT = "/v1/associados";
    public static final String PAUTA_API_ENDPOINT = "/v1/pautas";
    public static final String SESSAO_API_ENDPOINT = "/v1/sessoes";
    public static final String REF_API_ENDPOINT = "/v1/referencias";
    public static final String VOTO_API_ENDPOINT = "/v1/votos";

    
    public VotoDto gerarVotoDto() {
        VotoDto voto = VotoDto.builder()
                .id(Double.valueOf(Math.random()*100000).longValue())
                .idSessao(1L)
                .idAssociado(1L)
                .dataVoto(null)
                .opcao(((Math.random()*10)<=5)
                        ?VotoOpcao.NAO
                        :VotoOpcao.SIM)
                .build();
        return voto;
    }

    public AssociadoDto gerarAssociadoDto(String nome, String telefone) {
        String regNome = (null==nome||nome.isBlank())?"Jonas Morgan":nome;
        String email = removerAcentos(regNome).replace(" ", ".").concat("@example.com");
        AssociadoDto associado1 = AssociadoDto.builder()
                .id(Double.valueOf(Math.random()*100000).longValue())
                .nome(regNome)
                .email(email)
                .telefone((null==telefone||telefone.isBlank())?"(11) 99999-1111":telefone)
                .build();
        return associado1;
    }

    public static String removerAcentos(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }
}
