package space.lasf.votacao_backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import space.lasf.votacao_backend.core.exception.BusinessException;
import space.lasf.votacao_backend.domain.model.Usuario;
import space.lasf.votacao_backend.domain.repository.UsuarioRepository;
import space.lasf.votacao_backend.dto.UsuarioDto;
import space.lasf.votacao_backend.dto.mapper.UsuarioMapper;
import space.lasf.votacao_backend.service.UsuarioService;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Implementação do serviço para gerenciamento de usuários.
 */
@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9+_.-]+\\.[a-z]{2,4}$");
  
    @Override  
    public UsuarioDto criarUsuario(UsuarioDto novoUsuario){
        if(novoUsuario.getLogin()==null || novoUsuario.getLogin().isEmpty())
            throw new BusinessException("O campo ID precisa ser informado");
        Usuario savedUsuario = repository.save(UsuarioMapper.toUsuarioEntity(novoUsuario));
        return UsuarioMapper.toUsuarioDto(savedUsuario);
    }

    @Override
    public UsuarioDto alterarUsuario(UsuarioDto usuario){
        if(usuario.getLogin()==null || usuario.getLogin().isEmpty())
            throw new BusinessException("O campo ID precisa ser informado");
        Usuario savedUser = repository.save(UsuarioMapper.toUsuarioEntity(usuario));
        return UsuarioMapper.toUsuarioDto(savedUser);
    }

    @Override
    public void removerUsuario(String id){
        if(id==null || id.isEmpty())
            throw new BusinessException("O campo ID precisa ser informado");
        repository.deleteById(id);
    }
   
    @Override 
    public List<UsuarioDto> listarUsuarios(){
        return UsuarioMapper.toListUsuarioDto(repository.findAll());
    }

    @Override
    public UsuarioDto consultarUsuario(String id){
        if(id==null || id.isEmpty())
            throw new BusinessException("O campo ID precisa ser informado");
        else
            return UsuarioMapper.toUsuarioDto(repository.findById(id).get());
    }

}