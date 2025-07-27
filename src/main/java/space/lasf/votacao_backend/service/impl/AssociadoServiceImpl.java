package space.lasf.votacao_backend.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import space.lasf.votacao_backend.core.util.ObjectsValidator;
import space.lasf.votacao_backend.domain.model.Associado;
import space.lasf.votacao_backend.domain.repository.AssociadoRepository;
import space.lasf.votacao_backend.service.AssociadoService;


/**
 * Implementação do serviço para gerenciamento de associados.
 */
@Service
@RequiredArgsConstructor
public class AssociadoServiceImpl implements AssociadoService {
 
    private final AssociadoRepository associadoRepository;
    private final ObjectsValidator<Associado> validadorDeAssociado;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9+_.-]+\\.[a-z]{2,4}$");

    @Override
    @Transactional
    public Associado criarAssociado(Associado associado) {
        // Valida os dados do associado antes de salvar
        if (!validarEmailAssociado(associado.getEmail())) {
            throw new IllegalArgumentException("Email inválido: " + associado.getEmail());
        }
        validadorDeAssociado.validate(associado);

        return associadoRepository.save(associado);

    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE)
    public Optional<Associado> buscarAssociadoPorId(String id) {
        return associadoRepository.findById(id);
    }

    @Override
    public Optional<Associado> buscarAssociadoPorEmail(String email) {
        if (!validarEmailAssociado(email)) {
            throw new IllegalArgumentException("false ");
        }
        
        return associadoRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Associado> buscarTodosAssociados() {
        return associadoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Associado> buscarAssociadosPorNome(String nome) {
        return associadoRepository.findByNomeContaining(nome);
    }

    @Override
    @Transactional
    public Associado alterarAssociado(Associado associado) {
        // Valida os dados do associado antes de salvar
        if (!validarEmailAssociado(associado.getEmail())) {
            throw new IllegalArgumentException("Email inválido: " + associado.getEmail());
        }
        validadorDeAssociado.validate(associado);
        return associadoRepository.save(associado);
    }

    @Override
    @Transactional
    public void removerAssociado(String idAssociado) {
        Optional<Associado> associadoOpt = associadoRepository.findById(idAssociado);
        if (associadoOpt.isPresent()) {
            Associado associado = associadoOpt.get();
            associadoRepository.delete(associado);
        }
    }

    @Override
    public boolean validarEmailAssociado(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        
        return EMAIL_PATTERN.matcher(email).matches();
    }
}