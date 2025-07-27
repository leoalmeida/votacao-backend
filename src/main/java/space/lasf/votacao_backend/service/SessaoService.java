package space.lasf.votacao_backend.service;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import space.lasf.votacao_backend.domain.model.Pauta;
import space.lasf.votacao_backend.domain.model.Sessao;
import space.lasf.votacao_backend.domain.model.Voto;

/**
 * Serviço para gerenciamento de sessoes.
 */
public interface SessaoService {
    
    Sessao criarSessao(String idAssociado, Pauta pauta);

    
    Optional<Sessao> buscarSessaoPorId(String id);
    
    List<Sessao> buscarTodasSessoes();
    
    List<Voto> buscarVotosPorIdSessao(String idSessao);
    
    void iniciarSessao(String idSessao);
    
    void finalizarSessao(String idSessao);
    
    void cancelarSessao(String idSessao);
    
    List<Sessao> buscarSessoesPorIdAssociado(String idAssociado);

    List<Sessao> buscarSessoesComVotos();
}