package space.lasf.associados.controller;

import java.net.URI;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;
import space.lasf.associados.dto.AssociadoDto;
import space.lasf.associados.service.AssociadoService;


/**
 * Controller para gerenciamento de associados.
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/v1/associados")
public class AssociadoController {

    @Autowired
    private AssociadoService associadoService;


    @GetMapping
    public ResponseEntity<List<AssociadoDto>> buscarAssociados() {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
            .body(associadoService.buscarTodosAssociados());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssociadoDto> buscarAssociadoPorId(@PathVariable Long id) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
            .body(associadoService.buscarAssociadoPorId(id));
    }
    
    @GetMapping("/{id}/existe")
    public ResponseEntity<Boolean> verificarAssociadoValido(@PathVariable Long id) {
        AssociadoDto dto = associadoService.buscarAssociadoPorId(id);
        return ResponseEntity.ok((null != dto));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<AssociadoDto> buscarAssociadoPorEmail(@PathVariable String email) {
        if (!associadoService.validarEmailAssociado(email)) {
            return ResponseEntity.notFound().build();
        }
        AssociadoDto dto = associadoService.buscarAssociadoPorEmail(email);
        
        return (null == dto)
                        ?ResponseEntity.notFound().build()
                        :ResponseEntity.ok(dto);
    }

    @GetMapping("/pesquisar")
    public ResponseEntity<List<AssociadoDto>> buscarAssociadosPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(associadoService.buscarAssociadosPorNome(nome));
    }

    @PostMapping
    public ResponseEntity<AssociadoDto> criarAssociado(@RequestBody @Valid AssociadoDto associado, UriComponentsBuilder uriBuilder) {
        AssociadoDto novoAssociado = associadoService.criarAssociado(associado);
        URI endereco = uriBuilder.path("/v1/associados/{id}").buildAndExpand(novoAssociado.getId()).toUri();
        return ResponseEntity.created(endereco)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(novoAssociado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssociadoDto> alterarAssociado(@PathVariable Long id, @RequestBody AssociadoDto associado) {
        AssociadoDto dto = associadoService.alterarAssociado(associado);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerAssociado(@PathVariable Long id) {
        associadoService.removerAssociado(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/validar-email")
    public ResponseEntity<Boolean> validateEmail(@RequestParam String email) {
        if (associadoService.validarEmailAssociado(email)) {
            return ResponseEntity.ok(true);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }
}