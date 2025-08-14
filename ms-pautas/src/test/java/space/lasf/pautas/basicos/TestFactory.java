package space.lasf.pautas.basicos;

import java.text.Normalizer;

import java.util.regex.Pattern;

import space.lasf.pautas.domain.model.Pauta;
import space.lasf.pautas.dto.PautaDto;

public class TestFactory {

    public static final String ASSOCIADOS_API_ENDPOINT = "/v1/associados";
    public static final String PAUTA_API_ENDPOINT = "/v1/pautas";
    public static final String SESSAO_API_ENDPOINT = "/v1/sessoes";
    public static final String REF_API_ENDPOINT = "/v1/referencias";
    public static final String VOTO_API_ENDPOINT = "/v1/votos";
    
    public PautaDto gerarPautaDto() {
        Integer regPauta = (int)(Math.random() * 999) + 1;
        PautaDto produto = PautaDto.builder()
                .id(Double.valueOf(Math.random()*100000).longValue())
                .nome("Pauta "+regPauta)
                .descricao("Descrição da Pauta " + regPauta)
                .build();
        return produto; 
    }

    public Pauta gerarPauta() {
        Integer regPauta = (int)(Math.random() * 999) + 1;
        Pauta produto = Pauta.builder()
                .id(Double.valueOf(Math.random()*100000).longValue())
                .nome("Pauta "+regPauta)
                .descricao("Descrição da Pauta " + regPauta)
                .build();
        return produto;
    }

    public static String removerAcentos(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }
}
