package space.lasf.votacao_backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import space.lasf.votacao_backend.basicos.TestFactory;
import space.lasf.votacao_backend.core.util.ObjectsValidator;
import space.lasf.votacao_backend.domain.model.Associado;
import space.lasf.votacao_backend.domain.repository.AssociadoRepository;
import space.lasf.votacao_backend.service.impl.AssociadoServiceImpl;

@ExtendWith(SpringExtension.class)
public class AssociadoServiceTest extends TestFactory{

    @Mock
    private AssociadoRepository repository;

    @Mock
    private ObjectsValidator<Associado> validator;
    
    @InjectMocks
    private AssociadoServiceImpl service;

    Associado associadoEntity;
    Associado associadoEntity2;
    Associado associadoEntity3;
    Associado pedido;
    List<Associado> associadosAssets;

    @BeforeEach
    public void setUp() {
        associadoEntity = gerarAssociado("João Silva","(11) 99999-1111");
        associadoEntity2 = gerarAssociado("Maria Santos","(11) 99999-2222");
        associadoEntity3 = gerarAssociado("Pedro Oliveira","(11) 99999-3333");
        associadosAssets = Arrays.asList(associadoEntity,associadoEntity2,associadoEntity3);
    }

    
    @Test
    public void testeCriacaoDeNovoAssociado() {
        // Configura os mocks
        doReturn(associadoEntity).when(validator)
            .validate(associadoEntity);
        doReturn(associadoEntity).when(repository).save(any(Associado.class));
        
        // Executa o método
        Associado createdAssociado = service.criarAssociado(associadoEntity);

        // Verifica o resultado
        assertNotNull(createdAssociado, "Associado criado não deveria ser nulo");
        assertEquals(associadoEntity.getId(), createdAssociado.getId(), "Associado deveria ter o id correto");

        // Verifica se os métodos foram chamados
        verify(repository, atLeastOnce()).save(any(Associado.class));
    }

    @Test
    public void testeCriacaoDeAssociadoComEmailInvalido() {
        associadoEntity.setEmail("email-invalido");

        // Chamada ao serviço que deve lançar uma IllegalArgumentException
        Throwable  throwable  = 
            assertThrows(IllegalArgumentException.class, () ->{
                // Executa o método - deveria falhar para associados com email inválido
                service.criarAssociado(associadoEntity);
        
                // Uma exceção deveria ter sido lançada ao salvar um associado inválido
                assertTrue(true, "Exception não lançada ao salvar associado com email inválido");
            });
        // O teste deve falhar caso não seja gerada uma exception
        assertEquals(IllegalArgumentException.class, throwable.getClass(), 
                            "Exception gerada sem relação ao email do associado Inválido:" + throwable.getMessage());
        assertEquals("Email inválido: " + associadoEntity.getEmail(), throwable.getMessage());
    }

    @Test
    public void testeBuscarAssociadoPorIdAssociado() {
         // Configura os mocks
        doReturn(Optional.of(associadoEntity)).when(repository).findById(associadoEntity.getId());

        // Executa o método
        Optional<Associado> foundAssociado = service.buscarAssociadoPorId(associadoEntity.getId());

        // Verifica o resultado
        assertTrue(foundAssociado.isPresent(),"Associado deveria ser encontrado");
        assertEquals(associadoEntity.getId(), foundAssociado.get().getId(),
                "Associado encontrado deveria ter o ID correto");

        // Verifica se o método foi chamado
        verify(repository, times(1)).findById(associadoEntity.getId());
    }

    
    @Test
    public void testeBuscarAssociadoPorEmail() {
        // Configura o mock
        doReturn(Optional.of(associadoEntity)).when(repository).findByEmail(associadoEntity.getEmail());

        // Executa o método
        Optional<Associado> foundAssociado = service.buscarAssociadoPorEmail(associadoEntity.getEmail());

        // Verifica o resultado
        assertTrue(foundAssociado.isPresent(),"Associado deveria ser encontrado");
        assertEquals(associadoEntity.getEmail(), foundAssociado.get().getEmail(),"Associado encontrado deveria ter o email correto");

        // Verifica se os métodos foram chamados
        verify(repository, times(1)).findByEmail(associadoEntity.getEmail());
    }

    @Test
    public void testeBuscarAssociadoPorNome() {
        // Configura o mock
        doReturn(Arrays.asList(associadoEntity)).when(repository).findByNomeContaining(associadoEntity.getNome());

        // Executa o método
        List<Associado> foundListAssociados = service.buscarAssociadosPorNome(associadoEntity.getNome());

        // Verifica o resultado
        assertEquals(1,foundListAssociados.size(),"Apenas 1 Associado deveria ser encontrado");
        assertEquals(associadoEntity.getNome(), foundListAssociados.get(0).getNome(),"Associado encontrado deveria ter o nome correto");

        // Verifica se os métodos foram chamados
        verify(repository, times(1)).findByNomeContaining(associadoEntity.getNome());
    }

    @Test
    public void testeBucarTodosOsAssociados() {
        // Configura o mock
        doReturn(associadosAssets).when(repository).findAll();

        // Executa o método
        List<Associado> associados = service.buscarTodosAssociados();

        // Verifica o resultado
        assertEquals( associadosAssets.size(), associados.size(), 
                "Deveria encontrar a quantidade correta de associados");

        // Verifica se os métodos foram chamados
        verify(repository, times(1)).findAll();
    }

}