package space.lasf.votacao_backend.service;

import java.util.List;

import space.lasf.votacao_backend.dto.UsuarioDto;

public interface UsuarioService {

    UsuarioDto criarUsuario(UsuarioDto novoUsuario);
    UsuarioDto alterarUsuario(UsuarioDto novoUsuario);
    void removerUsuario(String id);
    List<UsuarioDto> listarUsuarios();
    UsuarioDto consultarUsuario(String id);

}
