package space.lasf.associados.service.impl;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.RequiredArgsConstructor;
import space.lasf.associados.core.util.ObjectsValidator;
import space.lasf.associados.domain.model.Associado;
import space.lasf.associados.domain.repository.AssociadoRepository;
import space.lasf.associados.dto.AssociadoDto;
import space.lasf.associados.dto.VotoDto;
import space.lasf.associados.http.SessaoClient;
import space.lasf.associados.service.AssociadoService;


/**
 * Implementação do serviço para gerenciamento de associados.
 */
@Service
@RequiredArgsConstructor
public class AssociadoServiceImpl implements AssociadoService {
 
    @Autowired
    private final AssociadoRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SessaoClient sessaoClient;

    @Autowired
    private final ObjectsValidator<Associado> validador;
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9+_.-]+\\.[a-z]{2,4}$");

    @Override
    @Transactional
    public AssociadoDto criarAssociado(AssociadoDto dto) {
        Associado entity = modelMapper.map(dto,Associado.class);
        // Valida os dados do dto antes de salvar
        if (!validarEmailAssociado(entity.getEmail())) {
            throw new IllegalArgumentException("Email inválido: " + dto.getEmail());
        }
        entity.setId(null); // Garante que o ID seja nulo para criação
        validador.validate(entity);

        Associado result = repository.save(entity);

        return modelMapper.map(result, AssociadoDto.class);

    }

    @Override
    @Transactional
    public AssociadoDto alterarAssociado(AssociadoDto dto) {
        Associado entity = modelMapper.map(dto,Associado.class);
        // Valida os dados do dto antes de salvar
        if (!validarEmailAssociado(entity.getEmail())) {
            throw new IllegalArgumentException("Email inválido: " + dto.getEmail());
        }
        validador.validate(entity);
        Associado result = repository.save(entity);
        
        return modelMapper.map(result, AssociadoDto.class);
    }

    @Override
    @Transactional
    public void removerAssociado(Long idAssociado) {
        if (repository.findById(idAssociado).isPresent()) {
            repository.deleteById(idAssociado);
        }else {
            throw new EntityNotFoundException("Associado não encontrado com ID: " + idAssociado);
        }
    }


    @Override
    @Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE)
    public AssociadoDto buscarAssociadoPorId(Long id) {
        Associado entityIn = repository.findById(id)
                                .orElseThrow(() -> new EntityNotFoundException());
        Map<Long,VotoDto> mapaVotos = sessaoClient.pesquisarVotosAssociado(id);
        AssociadoDto dtoOut = modelMapper.map(entityIn, AssociadoDto.class);
        dtoOut.setVotacaoAssociado(mapaVotos);
        return dtoOut;
    }

    @Override
    public AssociadoDto buscarAssociadoPorEmail(String email) {
        if (!validarEmailAssociado(email)) {
            throw new IllegalArgumentException("false ");
        }

        Associado result = repository.findByEmail(email)
                                .orElseThrow(() -> new EntityNotFoundException());
        return modelMapper.map(result, AssociadoDto.class); 
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssociadoDto> buscarTodosAssociados() {
        return repository.findAll().stream()
                .map(p -> modelMapper.map(p, AssociadoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssociadoDto> buscarAssociadosPorNome(String nome) {
        return repository.findByNomeContaining(nome).stream()
                .map(p -> modelMapper.map(p,AssociadoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean validarEmailAssociado(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        
        return EMAIL_PATTERN.matcher(email).matches();
    }
}