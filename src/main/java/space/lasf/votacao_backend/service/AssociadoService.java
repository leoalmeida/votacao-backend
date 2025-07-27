package space.lasf.votacao_backend.service;


import java.util.List;
import java.util.Optional;

import space.lasf.votacao_backend.domain.model.Associado;

/**
 * Serviço para gerenciamento de associados.
 */
public interface AssociadoService {
    
    Associado criarAssociado(Associado Associado);
    
    Optional<Associado> buscarAssociadoPorId(String idAssociado);
    
    Optional<Associado> buscarAssociadoPorEmail(String email);
    
    List<Associado> buscarTodosAssociados();
    
    List<Associado> buscarAssociadosPorNome(String nome);
    
    Associado alterarAssociado(Associado Associado);
    
    void removerAssociado(String idAssociado);

    boolean validarEmailAssociado(String email);
}