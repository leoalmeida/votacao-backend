package space.lasf.votacao_backend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import space.lasf.votacao_backend.basicos.AbstractIntegrationTest;
import space.lasf.votacao_backend.domain.repository.UsuarioRepository;
import space.lasf.votacao_backend.dto.UsuarioDto;


public class UsuarioControllerIntegrationTest extends AbstractIntegrationTest {


    @Autowired
    private UsuarioRepository repository;
    private UsuarioDto usuarioDto;
    
    @BeforeEach
    public void init() {
        usuarioDto = gerarUsuarioDto("","");
    }

    
    @Test
    @DisplayName("Usuario Path Test: salvar usuario dto e retornar")
    public void dadoUsuarioDtoCorreto_entaoSalvaUsuario_eRetornaUsuarioDto()
      throws Exception {

        // when
        UsuarioDto savedUsuarioDto = performPostRequestExpectedSuccess(
                                    USUARIO_API_ENDPOINT, usuarioDto, UsuarioDto.class);

        //then
        assertNotNull(savedUsuarioDto);
        assertEquals(usuarioDto.getLogin(), savedUsuarioDto.getLogin());
        assertEquals(usuarioDto.getPassword(), savedUsuarioDto.getPassword());
    }
}
