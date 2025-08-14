package space.lasf.associados.service;

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

import space.lasf.associados.basicos.TestFactory;
import space.lasf.associados.core.util.ObjectsValidator;
import space.lasf.associados.domain.model.Associado;
import space.lasf.associados.domain.repository.AssociadoRepository;
import space.lasf.associados.dto.AssociadoDto;
import space.lasf.associados.service.impl.AssociadoServiceImpl;

@ExtendWith(SpringExtension.class)
public class AssociadoServiceTest extends TestFactory{

    @Mock
    private AssociadoRepository repository;

    @Mock
    private ObjectsValidator<AssociadoDto> validator;
    
    @InjectMocks
    private AssociadoServiceImpl service;

    AssociadoDto associadoDto;
    AssociadoDto associadoDto2;
    AssociadoDto associadoDto3;
    AssociadoDto pedido;
    List<AssociadoDto> associadosAssets;

    @BeforeEach
    public void setUp() {
        associadoDto = gerarAssociadoDto("João Silva","(11) 99999-1111");
        associadoDto2 = gerarAssociadoDto("Maria Santos","(11) 99999-2222");
        associadoDto3 = gerarAssociadoDto("Pedro Oliveira","(11) 99999-3333");
        associadosAssets = Arrays.asList(associadoDto,associadoDto2,associadoDto3);
    }

    
    @Test
    public void testeCriacaoDeNovoAssociado() {
        // Configura os mocks
        doReturn(associadoDto).when(validator)
            .validate(associadoDto);
        doReturn(associadoDto).when(repository).save(any(Associado.class));
        
        // Executa o método
        AssociadoDto createdAssociadoDto = service.criarAssociado(associadoDto);

        // Verifica o resultado
        assertNotNull(createdAssociadoDto, "AssociadoDto criado não deveria ser nulo");
        assertEquals(associadoDto.getId(), createdAssociadoDto.getId(), "AssociadoDto deveria ter o id correto");

        // Verifica se os métodos foram chamados
        verify(repository, atLeastOnce()).save(any(Associado.class));
    }

    @Test
    public void testeCriacaoDeAssociadoComEmailInvalido() {
        associadoDto.setEmail("email-invalido");

        // Chamada ao serviço que deve lançar uma IllegalArgumentException
        Throwable  throwable  = 
            assertThrows(IllegalArgumentException.class, () ->{
                // Executa o método - deveria falhar para associados com email inválido
                service.criarAssociado(associadoDto);
        
                // Uma exceção deveria ter sido lançada ao salvar um AssociadoDto inválido
                assertTrue(true, "Exception não lançada ao salvar AssociadoDto com email inválido");
            });
        // O teste deve falhar caso não seja gerada uma exception
        assertEquals(IllegalArgumentException.class, throwable.getClass(), 
                            "Exception gerada sem relação ao email do AssociadoDto Inválido:" + throwable.getMessage());
        assertEquals("Email inválido: " + associadoDto.getEmail(), throwable.getMessage());
    }

    @Test
    public void testeBuscarAssociadoPorIdAssociado() {
         // Configura os mocks
        doReturn(Optional.of(associadoDto)).when(repository).findById(associadoDto.getId());

        // Executa o método
        AssociadoDto foundAssociadoDto = service.buscarAssociadoPorId(associadoDto.getId());

        // Verifica o resultado
        assertNotNull(foundAssociadoDto,"AssociadoDto deveria ser encontrado");
        assertEquals(associadoDto.getId(), foundAssociadoDto.getId(),
                "AssociadoDto encontrado deveria ter o ID correto");

        // Verifica se o método foi chamado
        verify(repository, times(1)).findById(associadoDto.getId());
    }

    
    @Test
    public void testeBuscarAssociadoPorEmail() {
        // Configura o mock
        doReturn(Optional.of(associadoDto)).when(repository).findByEmail(associadoDto.getEmail());

        // Executa o método
        AssociadoDto foundAssociado = service.buscarAssociadoPorEmail(associadoDto.getEmail());

        // Verifica o resultado
        assertNotNull(foundAssociado,"AssociadoDto deveria ser encontrado");
        assertEquals(associadoDto.getEmail(), foundAssociado.getEmail(),"AssociadoDto encontrado deveria ter o email correto");

        // Verifica se os métodos foram chamados
        verify(repository, times(1)).findByEmail(associadoDto.getEmail());
    }

    @Test
    public void testeBuscarAssociadoPorNome() {
        // Configura o mock
        doReturn(Arrays.asList(associadoDto)).when(repository).findByNomeContaining(associadoDto.getNome());

        // Executa o método
        List<AssociadoDto> foundListAssociados = service.buscarAssociadosPorNome(associadoDto.getNome());

        // Verifica o resultado
        assertEquals(1,foundListAssociados.size(),"Apenas 1 AssociadoDto deveria ser encontrado");
        assertEquals(associadoDto.getNome(), foundListAssociados.get(0).getNome(),"AssociadoDto encontrado deveria ter o nome correto");

        // Verifica se os métodos foram chamados
        verify(repository, times(1)).findByNomeContaining(associadoDto.getNome());
    }

    @Test
    public void testeBucarTodosOsAssociados() {
        // Configura o mock
        doReturn(associadosAssets).when(repository).findAll();

        // Executa o método
        List<AssociadoDto> associados = service.buscarTodosAssociados();

        // Verifica o resultado
        assertEquals( associadosAssets.size(), associados.size(), 
                "Deveria encontrar a quantidade correta de associados");

        // Verifica se os métodos foram chamados
        verify(repository, times(1)).findAll();
    }

}