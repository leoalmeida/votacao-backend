package space.lasf.pautas.http;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import space.lasf.pautas.dto.PautaDto;
import space.lasf.pautas.dto.SessaoDto;
import space.lasf.pautas.dto.VotoDto;


@FeignClient("ms-sessoes")
public interface SessaoClient {

    @GetMapping("/v1/sessoes/{idSessao}")
    SessaoDto buscarSessaoPorId(@PathVariable Long idSessao);

    @GetMapping("/v1/sessoes/{idSessao}/associado/{idAssociado}")
    SessaoDto buscarSessaoPorId(@PathVariable Long idSessao,
                          @PathVariable Long idAssociado);

    @PostMapping("/v1/sessoes")
    SessaoDto criarSessao(@RequestBody PautaDto pautaDto, 
                          @RequestParam boolean iniciarSessao);

    @GetMapping("/v1/votos/associado/{idAssociado}")
    Map<Long,VotoDto> pesquisarVotosAssociado(@PathVariable Long idAssociado);

}
