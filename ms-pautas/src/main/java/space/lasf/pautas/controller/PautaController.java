package space.lasf.pautas.controller;


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
import org.springframework.web.bind.annotation.RequestParam;

import space.lasf.pautas.dto.PautaDto;
import space.lasf.pautas.service.PautaService;



/**
 * Controller para gerenciamento de pautas.
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/v1/pautas")
public class PautaController {

    @Autowired
    private PautaService pautaService;


    @GetMapping
    public ResponseEntity<List<PautaDto>> buscarTodasPautas() {
        List<PautaDto> pautas = pautaService.buscarTodasPautas();
        return ResponseEntity.ok(pautas);
    }

    @GetMapping("/associado/{idAssociado}")
    public ResponseEntity<List<PautaDto>> buscarTodasPautas(@PathVariable Long idAssociado) {
        List<PautaDto> pautas = pautaService.buscarTodasPautas(idAssociado);
        return ResponseEntity.ok(pautas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PautaDto> buscarPautaById(@PathVariable Long id) {
        return ResponseEntity.ok(pautaService.buscarPautaPorId(id));
    }

    @GetMapping("/{idPauta}/associado/{idAssociado}")
    public ResponseEntity<PautaDto> buscarPautaById(@PathVariable Long idPauta,
                                                    @PathVariable Long idAssociado) {
        return ResponseEntity.ok(pautaService.buscarPautaPorId(idPauta, idAssociado));
    }

    @PostMapping
    public ResponseEntity<PautaDto> criarPauta(@RequestBody PautaDto pautaDto, 
                                                @RequestParam boolean iniciarSessao) {
        PautaDto savedPauta = pautaService.criarPauta(pautaDto,iniciarSessao);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(savedPauta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PautaDto> alterarPauta(@PathVariable Long id, @RequestBody PautaDto pauta) {
        return ResponseEntity.ok(pautaService.alterarPauta(id, pauta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerPauta(@PathVariable Long id) {
        pautaService.removerPauta(id);
        return ResponseEntity.noContent().build();
    }

}
