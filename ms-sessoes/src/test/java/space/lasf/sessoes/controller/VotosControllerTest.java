package space.lasf.sessoes.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import space.lasf.sessoes.domain.model.VotoOpcao;
import space.lasf.sessoes.dto.VotoDto;
import space.lasf.sessoes.service.VotoService;

@WebMvcTest(VotosController.class)
class VotosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private VotoService votoService;

    @Test
    void pesquisarVotosSessaoDeveRetornarLista() throws Exception {
        VotoDto dto = VotoDto.builder().id(1L).idSessao(10L).idAssociado(2L).opcao(VotoOpcao.SIM).build();
        when(votoService.buscarVotosSessao(10L)).thenReturn(List.of(dto));

        mockMvc.perform(get("/v1/votos/sessao/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].idSessao", is(10)));
    }

    @Test
    void pesquisarVotosAssociadoDeveRetornarMapa() throws Exception {
        VotoDto dto = VotoDto.builder().id(1L).idSessao(10L).idAssociado(2L).opcao(VotoOpcao.NAO).build();
        when(votoService.buscarVotosAssociado(2L)).thenReturn(Map.of(10L, dto));

        mockMvc.perform(get("/v1/votos/associado/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.10.id", is(1)))
                .andExpect(jsonPath("$.10.idAssociado", is(2)));
    }

    @Test
    void criarVotoDeveRetornarCreatedComLocation() throws Exception {
        VotoDto input = VotoDto.builder().idAssociado(3L).idSessao(15L).opcao(VotoOpcao.SIM).build();
        VotoDto output = VotoDto.builder().id(50L).idAssociado(3L).idSessao(15L).opcao(VotoOpcao.SIM).build();
        when(votoService.criarVoto(any(VotoDto.class))).thenReturn(output);

        mockMvc.perform(post("/v1/votos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
        .andExpect(header().string("Location", is("http://localhost/votos/50")))
                .andExpect(jsonPath("$.id", is(50)));
    }

    @Test
    void removerVotoDeveRetornarNoContent() throws Exception {
        doNothing().when(votoService).removerVoto(5L);

        mockMvc.perform(delete("/v1/votos/5"))
                .andExpect(status().isNoContent());

        verify(votoService).removerVoto(5L);
    }
}
