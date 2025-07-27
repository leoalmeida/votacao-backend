package space.lasf.votacao_backend.service;


import java.util.List;
import java.util.Optional;

import space.lasf.votacao_backend.domain.model.Pauta;

/**
 * Serviço para gerenciamento de pautas.
 */
public interface PautaService {
    
    Pauta criarPauta(Pauta Pauta);

    Pauta alterarPauta(Pauta Pauta);
    
    Optional<Pauta> buscarPautaPorId(String id);
    
    List<Pauta> buscarTodasPautas();
    
    void removerPauta(String PautaId);
    
}