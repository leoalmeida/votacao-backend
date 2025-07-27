package space.lasf.votacao_backend.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import space.lasf.votacao_backend.core.util.ObjectsValidator;
import space.lasf.votacao_backend.domain.model.Pauta;
import space.lasf.votacao_backend.domain.repository.PautaRepository;
import space.lasf.votacao_backend.service.PautaService;

/**
 * Implementação do serviço para gerenciamento de pautas.
 */
@Service
@RequiredArgsConstructor
public class PautaServiceImpl implements PautaService {

    @Autowired
    private ObjectsValidator<Pauta> validadorDePauta;

    @Autowired
    private PautaRepository pautaRepository;

    @Override
    @Transactional
    public Pauta criarPauta(Pauta pauta) {
        validadorDePauta.validate(pauta);// Valida a pauta antes de salvar
        return  pautaRepository.save(pauta);
    }

    @Override
    @Transactional
    public Pauta alterarPauta(Pauta pauta) {
        validadorDePauta.validate(pauta);// Valida a pauta antes  de realizar o merge
        return  pautaRepository.save(pauta);
    }

    @Override
    public Optional<Pauta> buscarPautaPorId(String id) {
        return pautaRepository.findById(id);
    }


    @Override
    public List<Pauta> buscarTodasPautas() {
        return pautaRepository.findAll();
    }

    @Override
    @Transactional
    public void removerPauta(String pautaId) {
        pautaRepository.deleteById(pautaId);
    }
    
}