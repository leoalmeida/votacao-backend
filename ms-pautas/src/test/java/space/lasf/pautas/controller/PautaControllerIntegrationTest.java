package space.lasf.pautas.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import space.lasf.pautas.basicos.AbstractIntegrationTest;
import space.lasf.pautas.domain.repository.PautaRepository;
import space.lasf.pautas.dto.PautaDto;


public class PautaControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private PautaRepository repository;
    private PautaDto pautaDto;

    @BeforeEach
    public void init() {
        pautaDto = gerarPautaDto();
    }

    @Test
    @DisplayName("Pauta Path Test: salvar pauta dto e retornar")
    public void dadoPautaDtoCorreto_entaoSalvaPauta_eRetornaPautaDto()
      throws Exception {

        // when
        PautaDto savedPautaDto = performPostRequestExpectedSuccess(
                                    PAUTA_API_ENDPOINT, pautaDto, PautaDto.class);

        //then
        assertNotNull(savedPautaDto);
        assertEquals(pautaDto.getDescricao(), savedPautaDto.getDescricao());
        assertEquals(pautaDto.getNome(), savedPautaDto.getNome());
    }
}
