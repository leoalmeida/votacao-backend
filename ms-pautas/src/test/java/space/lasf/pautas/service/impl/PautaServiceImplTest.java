package space.lasf.pautas.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import space.lasf.pautas.core.util.ObjectsValidator;
import space.lasf.pautas.domain.model.Pauta;
import space.lasf.pautas.dto.PautaDto;
import space.lasf.pautas.domain.repository.PautaRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PautaServiceImplTest {

    @Mock
    private PautaRepository pautaRepository;

    @InjectMocks
    private PautaServiceImpl pautaService;

    private Long associadoId;
    private PautaDto pauta;
    private PautaDto pauta1;
    private PautaDto pauta2;

    @BeforeEach
    void setUp() {
        
        // Setup for tests
        pauta = new PautaDto();
        pauta.setId(Double.valueOf(Math.random()*100000).longValue());
        pauta.setNome("Nome0");
        pauta.setDescricao("Descricao0");

        pauta1 = new PautaDto();
        pauta1.setId(Double.valueOf(Math.random()*100000).longValue());
        pauta1.setNome("Nome 1");
        pauta.setDescricao("Descricao1");

        pauta2 = new PautaDto();
        pauta2.setId(Double.valueOf(Math.random()*100000).longValue());
        pauta2.setNome("Nome 2");
        pauta.setDescricao("Descricao2");

    }

    @Test
    void criarPauta_deveGerarException_quandoPautaInvalida() {
        PautaDto pautaInvalida = PautaDto.builder()
                    .id(0L)
                    .nome("")
                    .descricao("")
                    .build();
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            pautaService.criarPauta(pautaInvalida,false);
        });
    }

    @Test
    void criarPauta_deveCriarPauta_quandoPautaValida() {
        PautaDto pauta = new PautaDto();
        pauta.setId(Double.valueOf(Math.random()*100000).longValue());
        pauta.setNome("Nome0");
        pauta.setDescricao("Descricao0");

        // Arrange
        when(pautaRepository.save(any(Pauta.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        PautaDto result = pautaService.criarPauta(pauta,false);

        // Assert
        assertNotNull(result);
        verify(pautaRepository).save(any(Pauta.class));
    }

}