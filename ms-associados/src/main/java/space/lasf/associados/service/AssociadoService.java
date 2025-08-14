package space.lasf.associados.service;


import java.util.List;
import space.lasf.associados.dto.AssociadoDto;

/**
 * Serviço para gerenciamento de associados.
 */
public interface AssociadoService {
    
    AssociadoDto criarAssociado(AssociadoDto associado);
    
    AssociadoDto buscarAssociadoPorId(Long idAssociado);
    
    AssociadoDto buscarAssociadoPorEmail(String email);
    
    List<AssociadoDto> buscarTodosAssociados();
    
    List<AssociadoDto> buscarAssociadosPorNome(String nome);
    
    AssociadoDto alterarAssociado(AssociadoDto associado);
    
    void removerAssociado(Long idAssociado);

    boolean validarEmailAssociado(String email);
}