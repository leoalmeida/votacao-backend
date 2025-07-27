package space.lasf.votacao_backend.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import space.lasf.votacao_backend.core.util.ObjectsValidator;
import space.lasf.votacao_backend.domain.model.Pauta;
import space.lasf.votacao_backend.dto.PautaDto;
import space.lasf.votacao_backend.dto.mapper.PautaMapper;
import space.lasf.votacao_backend.service.PautaService;



/**
 * Controller para gerenciamento de pautas.
 */
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/v1/pauta")
public class PautaController {

    @Autowired
    private PautaService pautaService;
    @Autowired
    private ObjectsValidator<PautaDto> pautaValidator;


    @GetMapping
    public ResponseEntity<List<PautaDto>> buscarTodosPautas() {
        List<PautaDto> pautas = PautaMapper.toListPautaDto(pautaService.buscarTodasPautas());
        return ResponseEntity.ok(pautas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PautaDto> buscarPautaById(@PathVariable String id) {
        return pautaService.buscarPautaPorId(id)
                .map(PautaMapper::toPautaDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PautaDto> criarPauta(@RequestBody PautaDto pautaDto) {
        pautaValidator.validate(pautaDto);
        PautaDto savedPauta = PautaMapper.toPautaDto(
                            pautaService.criarPauta(PautaMapper.toPautaEntity(pautaDto)));
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(savedPauta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PautaDto> alterarPauta(@PathVariable String id, @RequestBody PautaDto pauta) {
        pautaValidator.validate(pauta);
        Pauta entity = PautaMapper.toPautaEntity(pauta);
        return pautaService.buscarPautaPorId(id)
                .map(existingPauta -> pautaService.alterarPauta(existingPauta.updateData(entity)))
                .map(PautaMapper::toPautaDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerPauta(@PathVariable String id) {
        pautaService.removerPauta(id);
        return ResponseEntity.noContent().build();
    }

}
