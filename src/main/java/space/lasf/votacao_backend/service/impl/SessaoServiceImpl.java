package space.lasf.votacao_backend.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import space.lasf.votacao_backend.domain.model.Associado;
import space.lasf.votacao_backend.domain.model.Sessao;
import space.lasf.votacao_backend.domain.model.Voto;
import space.lasf.votacao_backend.domain.model.Pauta;
import space.lasf.votacao_backend.domain.repository.AssociadoRepository;
import space.lasf.votacao_backend.domain.repository.SessaoRepository;
import space.lasf.votacao_backend.domain.repository.VotoRepository;
import space.lasf.votacao_backend.service.SessaoService;

/**
 * Implementação do serviço para gerenciamento de sessões.
 */
@Service
@RequiredArgsConstructor
public class SessaoServiceImpl implements SessaoService {

    private final AssociadoRepository associadoRepository;
    private final VotoRepository votoRepository;
    private final SessaoRepository sessaoRepository;

    @Override
    @Transactional
    public Sessao criarSessao(String idAssociado, Pauta pauta) {
        if (idAssociado == null ) {
            throw new IllegalArgumentException("ID do associado não pode ser nulo.");
        }
        Associado associado = associadoRepository.findById(idAssociado)
				.orElseThrow(() -> new IllegalArgumentException("Associado não encontrado"));
        
        Sessao novaSessao = sessaoRepository
                            .save(Sessao.builder()
                                .pauta(pauta)
                                .associado(associado)
                                .build());
        
        return novaSessao;
    }

    @Override
    public Optional<Sessao> buscarSessaoPorId(String id) {
        return sessaoRepository.findById(id);
    }

    @Override
    public List<Sessao> buscarTodasSessoes() {
        return sessaoRepository.findAll();
    }

    @Override
    public List<Voto> buscarVotosPorIdSessao(String idSessao) {
        if (idSessao == null) {
            throw new IllegalArgumentException("ID da sessao inválido: " + idSessao);
        }
        return votoRepository.findVotosByIdSessao(idSessao);
    }

    @Override
    @Transactional
    public void iniciarSessao(String idSessao) {
        // Verifica se sessão existe
        Sessao sessaoAlterada = buscarSessaoPorId(idSessao)
            .orElseThrow(() -> new IllegalArgumentException("Sessao não encontrado com o id: " + idSessao));
      
        // Caso sessão seja encontrada, 
        //      inicializar a sessão e atualizar registro 
        sessaoRepository.save(sessaoAlterada.iniciarSessao());
    }

    @Override
    @Transactional
    public void finalizarSessao(String idSessao) {
        // Verifica se sessão existe
        Sessao sessaoAlterada = buscarSessaoPorId(idSessao)
            .orElseThrow(() -> new IllegalArgumentException("Sessao não encontrado com o id: " + idSessao));

        // Caso sessão seja encontrada, 
        //      finalize a sessão e atualiza registro 
        sessaoRepository.save(sessaoAlterada.finalizarSessao());
    }

    @Override
    @Transactional
    public void cancelarSessao(String idSessao) {
        // Verifica se sessão existe
        Sessao sessaoCancelada = buscarSessaoPorId(idSessao)
            .orElseThrow(() -> new IllegalArgumentException("Sessao não encontrado com o id: " + idSessao));
        
            // Caso sessão seja encontrada, 
            //      cancele a sessão e atualiza registro 
        sessaoRepository.save(sessaoCancelada.cancelarSessao());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Sessao> buscarSessoesPorIdAssociado(String idAssociado) {
        return sessaoRepository.findSessoesWithIdAssociado(idAssociado);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Sessao> buscarSessoesComVotos() {
        return sessaoRepository.findSessaoWithVotos();
    }

}
