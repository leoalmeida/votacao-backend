package space.lasf.pautas.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.lasf.pautas.core.exception.BusinessException;
import space.lasf.pautas.core.util.ObjectsValidator;
import space.lasf.pautas.domain.model.Pauta;
import space.lasf.pautas.domain.repository.PautaRepository;
import space.lasf.pautas.dto.PautaDto;
import space.lasf.pautas.dto.SessaoDto;
import space.lasf.pautas.http.SessaoClient;
import space.lasf.pautas.service.PautaService;

/**
 * Implementação do serviço para gerenciamento de pautas.
 */
@Service
@RequiredArgsConstructor
public class PautaServiceImpl implements PautaService {

    @Autowired
    private ObjectsValidator<Pauta> validador;

    @Autowired
    private PautaRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SessaoClient sessaoClient;

    @Override
    @Transactional
    public PautaDto criarPauta(final PautaDto dto, final boolean iniciarSessao) {
        Pauta entityIn = modelMapper.map(dto, Pauta.class);
        entityIn.setId(null); // Garante que o ID seja nulo para criação
        validador.validate(entityIn); // Valida a pauta antes de salvar
        Pauta entityOut = repository.save(entityIn); // Salva a pauta no repositório

        PautaDto dtoOut = modelMapper.map(entityOut, PautaDto.class);
        SessaoDto sessaoDto = sessaoClient.criarSessao(dtoOut, iniciarSessao);
        dtoOut.setSessaoDto(sessaoDto);
        entityOut.setIdSessao(sessaoDto.getId());
        repository.save(entityOut); // Atualiza a pauta com o ID da sessão

        return modelMapper.map(dtoOut, PautaDto.class);
    }

    @Override
    @Transactional
    public PautaDto alterarPauta(final Long id, final PautaDto pauta) {
        Pauta entity = modelMapper.map(pauta, Pauta.class);
        validador.validate(entity); // Valida a pauta antes  de realizar o merge
        if (this.buscarPautaPorId(id) != null) {
            ;
            return modelMapper.map(repository.save(entity), PautaDto.class);
        } else {
            throw new BusinessException("Pauta não encontrada para atualização.");
        }
    }

    @Override
    public PautaDto buscarPautaPorId(final Long id) {
        return repository
                .findById(id)
                .map(this::fillSessaoDto)
                .orElseThrow(() -> new BusinessException("Pauta não encontrada para atualização."));
    }

    @Override
    public PautaDto buscarPautaPorId(final Long idPauta, final Long idAssociado) {
        return repository
                .findById(idPauta)
                .map(pauta -> this.fillSessaoDto(pauta, idAssociado))
                .orElseThrow(() -> new BusinessException("Pauta não encontrada para atualização."));
    }

    @Override
    public List<PautaDto> buscarTodasPautas() {
        return repository.findAll().stream().map(this::fillSessaoDto).collect(Collectors.toList());
    }

    @Override
    public List<PautaDto> buscarTodasPautas(final Long idAssociado) {
        return repository.findAll().stream()
                .map(pauta -> this.fillSessaoDto(pauta, idAssociado))
                .collect(Collectors.toList());
    }

    private PautaDto fillSessaoDto(final Pauta entity) {
        SessaoDto sessaoDto = sessaoClient.buscarSessaoPorId(entity.getIdSessao());
        PautaDto dtoOut = modelMapper.map(entity, PautaDto.class);
        dtoOut.setSessaoDto(sessaoDto);
        return dtoOut;
    }

    private PautaDto fillSessaoDto(final Pauta entity, final Long idAssociado) {
        SessaoDto sessaoDto = sessaoClient.buscarSessaoPorId(entity.getIdSessao(), idAssociado);
        PautaDto dtoOut = modelMapper.map(entity, PautaDto.class);
        dtoOut.setSessaoDto(sessaoDto);
        return dtoOut;
    }

    @Override
    @Transactional
    public void removerPauta(final Long pautaId) {
        repository.deleteById(pautaId);
    }
}
