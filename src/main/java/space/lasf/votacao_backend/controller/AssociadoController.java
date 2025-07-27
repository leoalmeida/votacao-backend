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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import space.lasf.votacao_backend.core.util.ObjectsValidator;
import space.lasf.votacao_backend.domain.model.Associado;
import space.lasf.votacao_backend.dto.AssociadoDto;
import space.lasf.votacao_backend.dto.mapper.AssociadoMapper;
import space.lasf.votacao_backend.service.AssociadoService;


/**
 * Controller para gerenciamento de associados.
 */
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/v1/associados")
public class AssociadoController {

    @Autowired
    private AssociadoService associadoService;

    @Autowired
    private ObjectsValidator<AssociadoDto> associadoValidator;


    @GetMapping
    public ResponseEntity<List<AssociadoDto>> buscarAssociados() {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
            .body(AssociadoMapper.toListAssociadoDto(associadoService.buscarTodosAssociados()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssociadoDto> buscarAssociadoPorId(@PathVariable String id) {
        return associadoService.buscarAssociadoPorId(id)
                .map(AssociadoMapper::toAssociadoDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<AssociadoDto> buscarAssociadoPorEmail(@PathVariable String email) {
        if (!associadoService.validarEmailAssociado(email)) {
            return ResponseEntity.notFound().build();
        }
        return associadoService.buscarAssociadoPorEmail(email)
                .map(AssociadoMapper::toAssociadoDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/pesquisar")
    public ResponseEntity<List<AssociadoDto>> buscarAssociadosPorNome(@RequestParam String nome) {
        List<Associado> listaAssociados = associadoService.buscarAssociadosPorNome(nome);
        List<AssociadoDto> associados = AssociadoMapper.toListAssociadoDto(listaAssociados);
        return ResponseEntity.ok(associados);
    }

    @PostMapping
    public ResponseEntity<AssociadoDto> criarAssociado(@RequestBody AssociadoDto associado) {
        associadoValidator.validate(associado);
        AssociadoDto novoAssociado = AssociadoMapper.toAssociadoDto(
                        associadoService.criarAssociado(AssociadoMapper.toAssociadoEntity(associado)));
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(novoAssociado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssociadoDto> alterarAssociado(@PathVariable String id, @RequestBody AssociadoDto associado) {
        associadoValidator.validate(associado);
        associadoService.alterarAssociado(AssociadoMapper.toAssociadoEntity(associado));
        return ResponseEntity.ok(associado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerAssociado(@PathVariable String id) {
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