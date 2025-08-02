package space.lasf.votacao_backend.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import space.lasf.votacao_backend.core.util.ObjectsValidator;
import space.lasf.votacao_backend.domain.model.Associado;
import space.lasf.votacao_backend.domain.model.Sessao;
import space.lasf.votacao_backend.domain.model.Pauta;
import space.lasf.votacao_backend.domain.repository.AssociadoRepository;
import space.lasf.votacao_backend.domain.repository.SessaoRepository;
import space.lasf.votacao_backend.domain.repository.PautaRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PautaServiceImplTest {

    @Mock
    private PautaRepository produtoRepository;
    @Mock
    private AssociadoRepository associadoRepository;
    @Mock
    private PautaRepository pautaRepository;
    @Mock
    private SessaoRepository itemPautaRepository;
    @Mock
    private ObjectsValidator<Sessao> validadorDeItem;

    @InjectMocks
    private PautaServiceImpl pautaService;

    private Associado associado;
    private String associadoId;
    private Pauta pauta;
    private Pauta pauta1;
    private Pauta pauta2;

    @BeforeEach
    void setUp() {
        associadoId = UUID.randomUUID().toString();
        associado = new Associado();
        associado.setId(associadoId);
        associado.setNome("Test Pauta");
        associado.setEmail("test@client.com");

        // Setup for tests
        pauta = new Pauta();
        pauta.setId(UUID.randomUUID().toString());
        pauta.setNome("Nome0");
        pauta.setDescricao("Descricao0");

        pauta1 = new Pauta();
        pauta1.setId(UUID.randomUUID().toString());
        pauta1.setNome("Nome 1");
        pauta.setDescricao("Descricao1");

        pauta2 = new Pauta();
        pauta2.setId(UUID.randomUUID().toString());
        pauta2.setNome("Nome 2");
        pauta.setDescricao("Descricao2");

    }

    @Test
    void criarPauta_deveGerarException_quandoPautaInvalida() {
        Pauta pautaInvalida = Pauta.builder()
                    .id("")
                    .nome("")
                    .descricao("")
                    .build();
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            pautaService.criarPauta(pautaInvalida);
        });
    }

    @Test
    void criarPauta_deveCriarPauta_quandoPautaValida() {
        Pauta pauta = new Pauta();
        pauta.setId(UUID.randomUUID().toString());
        pauta.setNome("Nome0");
        pauta.setDescricao("Descricao0");

        // Arrange
        when(pautaRepository.save(any(Pauta.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Pauta result = pautaService.criarPauta(pauta);

        // Assert
        assertNotNull(result);
        verify(pautaRepository).save(any(Pauta.class));
    }

}