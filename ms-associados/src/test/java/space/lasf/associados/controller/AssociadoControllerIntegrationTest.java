package space.lasf.associados.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import space.lasf.associados.basicos.AbstractIntegrationTest;
import space.lasf.associados.domain.repository.AssociadoRepository;
import space.lasf.associados.dto.AssociadoDto;


public class AssociadoControllerIntegrationTest extends AbstractIntegrationTest{

  
    @Autowired
    private AssociadoRepository associadoRepository;
    private AssociadoDto associadoDto;

    @BeforeEach
    public void init() {
        associadoDto = gerarAssociadoDto("Marta Rocha", "(51) 99999-5555");
    }

    @Test
    @DisplayName("Associado Path Test: salvar associado dto e retornar")
    public void dadoAssociadoDtoCorreto_entaoSalvaAssociado_eRetornaAssociadoDto()
      throws Exception {

        // when
        AssociadoDto savedAssociadoDto = performPostRequestExpectedSuccess(
                                    ASSOCIADOS_API_ENDPOINT, associadoDto, AssociadoDto.class);

        //then
        assertNotNull(savedAssociadoDto);
        assertEquals(associadoDto.getEmail(), savedAssociadoDto.getEmail());
        assertEquals(associadoDto.getNome(), savedAssociadoDto.getNome());
        assertEquals(associadoDto.getTelefone(), savedAssociadoDto.getTelefone());
    }
    
}
