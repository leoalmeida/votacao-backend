package space.lasf.votacao_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import space.lasf.votacao_backend.dto.UsuarioDto;
import space.lasf.votacao_backend.service.UsuarioService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping
    public UsuarioDto post(@RequestBody UsuarioDto usuarioDto){
        return usuarioService.criarUsuario(usuarioDto);
    }
    @PutMapping
    public UsuarioDto put(@RequestBody UsuarioDto usuarioDto){
        return usuarioService.alterarUsuario(usuarioDto);
    }
    @GetMapping("{id}")
    public UsuarioDto getOne(@PathVariable("id") String id){
        return usuarioService.consultarUsuario(id);
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") String id){
        usuarioService.removerUsuario(id);
    }
    @GetMapping
    public List<UsuarioDto> getAll(){
        return usuarioService.listarUsuarios();
    }
}

