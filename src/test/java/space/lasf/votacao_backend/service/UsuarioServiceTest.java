package space.lasf.votacao_backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import space.lasf.votacao_backend.core.util.ObjectsValidator;
import space.lasf.votacao_backend.domain.model.Usuario;
import space.lasf.votacao_backend.domain.repository.UsuarioRepository;
import space.lasf.votacao_backend.service.impl.UsuarioServiceImpl;
import space.lasf.votacao_backend.dto.UsuarioDto;
import space.lasf.votacao_backend.dto.mapper.UsuarioMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UsuarioService Tests")
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ObjectsValidator<Usuario> usuarioValidator;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = Usuario.builder()
                .id("1")
                .login("testuser")
                .email("test@example.com")
                .password("password")
                .build();
    }

    @Test
    @DisplayName("Should create a user successfully")
    void criarUsuario_shouldCreateUserSuccessfully() {
        // Arrange
        when(usuarioRepository.findByLogin(anyString())).thenReturn(Optional.empty());
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        doNothing().when(usuarioValidator).validate(any(Usuario.class));

        // Act
        UsuarioDto createdUsuario = usuarioService.criarUsuario(UsuarioMapper.toUsuarioDto(usuario));

        // Assert
        assertNotNull(createdUsuario);
        assertEquals("testuser", createdUsuario.getLogin());
        verify(usuarioValidator, times(1)).validate(usuario);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Should throw exception when creating a user with existing login")
    void criarUsuario_shouldThrowExceptionWhenLoginExists() {
        // Arrange
        when(usuarioRepository.findByLogin("testuser")).thenReturn(Optional.of(usuario));
        doNothing().when(usuarioValidator).validate(any(Usuario.class));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> usuarioService.criarUsuario(UsuarioMapper.toUsuarioDto(usuario)));
        assertEquals("Login já existe: testuser", exception.getMessage());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Should throw exception when creating a user with existing email")
    void criarUsuario_shouldThrowExceptionWhenEmailExists() {
        // Arrange
        when(usuarioRepository.findByLogin(anyString())).thenReturn(Optional.empty());
        when(usuarioRepository.findByEmail("test@example.com")).thenReturn(Optional.of(usuario));
        doNothing().when(usuarioValidator).validate(any(Usuario.class));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> usuarioService.criarUsuario(UsuarioMapper.toUsuarioDto(usuario)));
        assertEquals("Email já existe: test@example.com", exception.getMessage());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Should throw exception when user validation fails on creation")
    void criarUsuario_shouldThrowExceptionWhenValidationFails() {
        // Arrange
        doThrow(new IllegalArgumentException("Validation failed")).when(usuarioValidator).validate(any(Usuario.class));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> usuarioService.criarUsuario(UsuarioMapper.toUsuarioDto(usuario)));
        assertEquals("Validation failed", exception.getMessage());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Should find a user by ID")
    void buscarUsuarioPorId_shouldReturnUserWhenFound() {
        // Arrange
        when(usuarioRepository.findById("1")).thenReturn(Optional.of(usuario));

        // Act
        UsuarioDto foundUsuario = usuarioService.consultarUsuario("1");

        // Assert
        assertNotNull(foundUsuario);
        assertEquals("testuser", foundUsuario.getLogin());
    }

    @Test
    @DisplayName("Should return null value when user by ID is not found")
    void buscarUsuarioPorId_shouldReturnEmptyWhenNotFound() {
        // Arrange
        when(usuarioRepository.findById("1")).thenReturn(Optional.empty());

        // Act
        UsuarioDto foundUsuario = usuarioService.consultarUsuario("1");

        // Assert
        assertNull(foundUsuario);
    }

    @Test
    @DisplayName("Should find all users")
    void buscarTodosUsuarios_shouldReturnAllUsers() {
        // Arrange
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        // Act
        List<UsuarioDto> users = usuarioService.listarUsuarios();

        // Assert
        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
    }

    @Test
    @DisplayName("Should return empty list when no users exist")
    void buscarTodosUsuarios_shouldReturnEmptyList() {
        // Arrange
        when(usuarioRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<UsuarioDto> users = usuarioService.listarUsuarios();

        // Assert
        assertTrue(users.isEmpty());
    }

    @Test
    @DisplayName("Should delete a user successfully")
    void removerUsuario_shouldDeleteUser() {
        // Arrange
        when(usuarioRepository.existsById("1")).thenReturn(true);
        doNothing().when(usuarioRepository).deleteById("1");

        // Act
        assertDoesNotThrow(() -> usuarioService.removerUsuario("1"));

        // Assert
        verify(usuarioRepository, times(1)).deleteById("1");
    }

    @Test
    @DisplayName("Should throw exception when deleting a non-existent user")
    void removerUsuario_shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        when(usuarioRepository.existsById("1")).thenReturn(false);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> usuarioService.removerUsuario("1"));
        assertEquals("Usuário não encontrado com o id: 1", exception.getMessage());
    }
}