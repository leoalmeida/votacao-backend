package space.lasf.pautas.service;


import java.util.List;
import space.lasf.pautas.dto.PautaDto;

/**
 * Serviço para gerenciamento de pautas.
 */
public interface PautaService {
    
    PautaDto criarPauta(PautaDto Pauta, boolean iniciarSessao);

    PautaDto alterarPauta(Long id,PautaDto Pauta);
    
    PautaDto buscarPautaPorId(Long id);

    PautaDto buscarPautaPorId(Long id, Long idAssociado);
    
    List<PautaDto> buscarTodasPautas();

    List<PautaDto> buscarTodasPautas(Long idAssociado);
    
    void removerPauta(Long PautaId);
    
}