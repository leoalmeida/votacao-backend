package space.lasf.votacao_backend.controller;


import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import space.lasf.votacao_backend.core.util.ObjectsValidator;
import space.lasf.votacao_backend.domain.model.Pauta;
import space.lasf.votacao_backend.domain.model.Sessao;
import space.lasf.votacao_backend.domain.model.SessaoStatus;
import space.lasf.votacao_backend.dto.PautaDto;
import space.lasf.votacao_backend.dto.SessaoDto;
import space.lasf.votacao_backend.dto.mapper.PautaMapper;
import space.lasf.votacao_backend.dto.mapper.SessaoMapper;
import space.lasf.votacao_backend.service.SessaoService;

/**
 * Controller para gerenciamento de sessoes.
 */
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/v1/sessao")
public class SessaoController {

    @Autowired
    private SessaoService sessaoService;
    @Autowired
    private ObjectsValidator<PautaDto> pautaValidator;
    @Autowired
    private ObjectsValidator<SessaoDto> sessaoValidator;


    @GetMapping
    public ResponseEntity<List<SessaoDto>> buscarTodasSessoes() {
        List<SessaoDto> sessoes = SessaoMapper.toListSessaoDto(sessaoService.buscarTodasSessoes());
        return ResponseEntity.ok(sessoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessaoDto> buscarSessaoPorId(@PathVariable String id) {
        return sessaoService.buscarSessaoPorId(id)
                .map(SessaoMapper::toSessaoDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SessaoDto> criarSessao(@RequestParam String idCliente, @RequestBody PautaDto pautaDto) {
        pautaValidator.validate(pautaDto);
        Pauta entity = PautaMapper.toPautaEntity(pautaDto);
        String idAssociado = "x";
        Sessao sessaoCriada = sessaoService.criarSessao(idAssociado, entity);
        SessaoDto novaSessao = SessaoMapper.toSessaoDto(sessaoCriada);
        return ResponseEntity.status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(novaSessao);
    }

    @PostMapping("/{idSessao}/finalizar")
    public ResponseEntity<Void> finalizarSessao(@PathVariable String idSessao) {
        sessaoService.finalizarSessao(idSessao);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{idSessao}/cancelar")
    public ResponseEntity<Void> cancelarSessao(@PathVariable String idSessao) {
        sessaoService.cancelarSessao(idSessao);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{idSessao}/validar")
    public ResponseEntity<Map<String, Boolean>> validarSessao(@PathVariable String idSessao) {
        Optional<Sessao> sessao = sessaoService.buscarSessaoPorId(idSessao);
        if (sessao.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        SessaoDto dto = SessaoMapper.toSessaoDto(sessao.get());
        sessaoValidator.validate(dto);
        boolean isValid = SessaoStatus.OPEN_TO_VOTE.name().equals(dto.getStatus());

        return ResponseEntity.ok(Map.of(idSessao, isValid));
    }

    @GetMapping("/com-votos")
    public ResponseEntity<List<SessaoDto>> buscarSessaoComVotos() {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(SessaoMapper.toListSessaoDto(sessaoService.buscarSessoesComVotos()));
    }

}