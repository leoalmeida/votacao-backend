package space.lasf.votacao_backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import space.lasf.votacao_backend.core.util.ObjectsValidator;
import space.lasf.votacao_backend.dto.SessaoDto;
import space.lasf.votacao_backend.dto.VotoDto;
import space.lasf.votacao_backend.service.SessaoService;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SessaoController.class)
class SessaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SessaoService sessaoService;

    // Mock validators to avoid NullPointerException as they are not initialized in this slice test
    @MockitoBean
    private ObjectsValidator<SessaoDto> itemPedidoValidator;
    @MockitoBean
    private ObjectsValidator<VotoDto> sessaoValidator;

    @Test
    void validarPedido_shouldReturnNotFound_whenPedidoDoesNotExist() throws Exception {
        String sessaoId = UUID.randomUUID().toString();
        when(sessaoService.buscarSessaoPorId(sessaoId)).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/v1/sessao/{idPedido}/validar", sessaoId))
                .andExpect(status().isNotFound());
    }

    // Add more tests for other endpoints...

}