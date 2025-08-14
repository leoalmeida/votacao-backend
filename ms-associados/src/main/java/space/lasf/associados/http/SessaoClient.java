package space.lasf.associados.http;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import space.lasf.associados.dto.VotoDto;


@FeignClient("ms-sessoes")
public interface SessaoClient {
    
    @GetMapping("/v1/votos/associado/{idAssociado}")
    Map<Long,VotoDto> pesquisarVotosAssociado(@PathVariable Long idAssociado);

}