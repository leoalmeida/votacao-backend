package space.lasf.votacao_backend.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import space.lasf.votacao_backend.core.util.ObjectsValidator;
import space.lasf.votacao_backend.domain.model.Usuario;
import space.lasf.votacao_backend.domain.repository.UsuarioRepository;
import space.lasf.votacao_backend.dto.UsuarioDto;
import space.lasf.votacao_backend.dto.mapper.UsuarioMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ObjectsValidator<Usuario> validator;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = Usuario.builder()
                .id(UUID.randomUUID().toString())
                .login("testuser")
                .email("test@example.com")
                .password("password")
                .build();
    }

    @Test
    void criarUsuario_shouldSaveAndReturnUsuario() {
        // Arrange
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        // Act
        UsuarioDto createdUsuario = usuarioService.criarUsuario(UsuarioMapper.toUsuarioDto(usuario));
        // Assert
        assertNotNull(createdUsuario);
        assertEquals("testuser", createdUsuario.getLogin());
        verify(validator).validate(usuario);
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void consultarUsuario_shouldReturnUsuario_whenFound() {
        // Arrange
        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));

        // Act
        UsuarioDto foundUsuario = usuarioService.consultarUsuario(usuario.getId());

        // Assert
        assertNotNull(foundUsuario);
        assertEquals(usuario.getId(), foundUsuario.getId());
    }

    @Test
    void buscarUsuarioPorId_shouldReturnEmpty_whenNotFound() {
        // Arrange
        when(usuarioRepository.findById("non-existent-id")).thenReturn(Optional.empty());

        // Act
        UsuarioDto foundUsuario = usuarioService.consultarUsuario("non-existent-id");

        // Assert
        assertNull(foundUsuario);
    }

    @Test
    void buscarTodosUsuarios_shouldReturnListOfUsuarios() {
        // Arrange
        when(usuarioRepository.findAll()).thenReturn(Collections.singletonList(usuario));

        // Act
        List<UsuarioDto> usuarios = usuarioService.listarUsuarios();

        // Assert
        assertFalse(usuarios.isEmpty());
        assertEquals(1, usuarios.size());
    }

    @Test
    void removerUsuario_shouldCallDeleteById() {
        // Arrange
        String userId = usuario.getId();
        // Mock the repository to do nothing when deleteById is called
        doNothing().when(usuarioRepository).deleteById(userId);

        // Act
        usuarioService.removerUsuario(userId);

        // Assert
        verify(usuarioRepository, times(1)).deleteById(userId);
    }
}