package space.lasf.votacao_backend.service;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import space.lasf.votacao_backend.core.util.ObjectsValidator;
import space.lasf.votacao_backend.domain.model.Pauta;
import space.lasf.votacao_backend.domain.repository.PautaRepository;
import space.lasf.votacao_backend.service.impl.PautaServiceImpl;

/**
 * Testes para o serviço de pautas.
 */
@ExtendWith(SpringExtension.class)
//@SpringBootTest
public class PautaServiceTest {

    @Mock
    private PautaRepository repository;

    @Mock
    private ObjectsValidator<Pauta> validator;
    
    @InjectMocks
    private PautaServiceImpl service;

    private Pauta pauta1;
    private Pauta pauta2;
    private Pauta pauta3;

  
    @BeforeEach
    public void setUp() {
        
        // Cria pautas para testes
        pauta1 = new Pauta();
        pauta1.setId(UUID.randomUUID().toString());
        pauta1.setNome("Pauta 1");
        pauta1.setDescricao("Descrição do Pauta 1");


        pauta2 = new Pauta();
        pauta2.setId(UUID.randomUUID().toString());
        pauta2.setNome("Pauta 2");
        pauta2.setDescricao("Descrição do Pauta 2");

        pauta3 = new Pauta();
        pauta3.setId(UUID.randomUUID().toString());
        pauta3.setNome("Pauta 3");
        pauta3.setDescricao("Descrição do Pauta 3");
        
    }

    @Test
    public void testeCriarPauta() {
        Pauta mockPauta = mock(Pauta.class);

        // Configura o mock
        doReturn(mockPauta).when(repository).save(mockPauta);
        //doNothing().when(modelMapper).map(mockPostRequestDto, post);
        //doReturn(mockPautaDto).when(modelMapper).map(mockUpdatedPauta, PautaDto.class);
        doReturn(mockPauta).when(validator).validate(mockPauta);

        // Executa o método
        Pauta savedPauta = service.criarPauta(mockPauta);

        // Verifica o resultado
        assertNotNull(savedPauta, "Pauta salvo não deveria ser nulo");
        assertEquals(mockPauta.getId(), savedPauta.getId(),"Pauta salvo deveria ter o mesmo ID");

        // Verifica se o método do repositório foi chamado
        verify(repository, times(1)).save(mockPauta);
        verify(validator, times(1)).validate(mockPauta);
    }

    @Test
    public void testeAlterarPauta() {
        Pauta mockPauta = mock(Pauta.class);
        mockPauta.setNome("PautaAlterado");
        // Configura o mock
        doReturn(mockPauta).when(validator).validate(mockPauta);
        doReturn(mockPauta).when(repository).save(mockPauta);
        
        // Executa o método
        Pauta pautaAlterado = service.alterarPauta(mockPauta);

        // Verifica o resultado
        assertNotNull(pautaAlterado, "Pauta salvo não deveria ser nulo");
        assertEquals(mockPauta.getId(), pautaAlterado.getId(),"Pauta salvo deveria ter o mesmo ID");
        assertEquals(mockPauta.getNome(), pautaAlterado.getNome(),"Pauta deveria ter o nome modificado");

        // Verifica se o método do repositório foi chamado
        verify(repository, times(1)).save(mockPauta);
        verify(validator, times(1)).validate(mockPauta);
    }

    @Test
    public void testeBuscarPautaById() {
        Pauta mockPauta = mock(Pauta.class);

        // Configura o mock
        doReturn(Optional.of(mockPauta)).when(repository).findById("1");

        // Executa o método
        Optional<Pauta> foundPauta = service.buscarPautaPorId("1");

        // Verifica o resultado
        assertTrue(foundPauta.isPresent(), "Pauta deveria ser encontrado");
        assertEquals(mockPauta.getId(), 
                    foundPauta.get().getId(),
                    "Pauta encontrado deveria ter o ID correto");

        // Verifica se o método do repositório foi chamado
        verify(repository, times(1)).findById("1");
    }

    @Test
    public void testeBuscarAllPautas() {
        // Configura o mock
        Pauta mockPauta1 = mock(Pauta.class);
        Pauta mockPauta2 = mock(Pauta.class);
        Pauta mockPauta3 = mock(Pauta.class);
        doReturn(Arrays.asList(mockPauta1, mockPauta2, mockPauta3))
            .when(repository).findAll();

        // Executa o método
        List<Pauta> pautas = service.buscarTodasPautas();

        // Verifica o resultado
        assertEquals(3, pautas.size(),"Deveria encontrar 3 pautas");

        // Verifica se o método do repositório foi chamado
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testeRemoverPautaPorIdPauta() {
        // Configura o mock
        doNothing().when(repository).deleteById("1");

        // Executa o método
        service.removerPauta("1");

        // Verifica se o método do repositório foi chamado
        verify(repository, times(1)).deleteById("1");
    }

    @Test
    public void testeRemoverPautaComIdInvalido() {
        String idInvalido = UUID.randomUUID().toString();
        // Configura o mock
        doThrow(NoSuchElementException.class)
                .when(repository).deleteById(idInvalido);
    
        // Executa o método
        Throwable  throwable  = 
                assertThrows(NoSuchElementException.class, () ->{
                    service.removerPauta(idInvalido);
                });
        assertEquals(NoSuchElementException.class, throwable.getClass());

        // Verifica se o método do repositório foi chamado
        verify(repository, times(1)).deleteById(idInvalido);
    }

}
