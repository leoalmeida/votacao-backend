package space.lasf.votacao_backend.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import space.lasf.votacao_backend.domain.model.Pauta;
import space.lasf.votacao_backend.dto.PautaDto;

@Component
public class PautaMapper {
    
    public static PautaDto toPautaDto(Pauta pauta) {

		if (pauta == null)
			return null;

		return PautaDto.builder()
				.id(pauta.getId())
				.nome(pauta.getNome())
				.descricao(pauta.getDescricao())
				.build();
	}
	
	public static Pauta toPautaEntity(PautaDto pautaDto) {

		if (pautaDto == null)
			return null;
		
		Pauta pautaEntity = new Pauta();
		
        pautaEntity.setId(pautaDto.getId());
        pautaEntity.setNome(pautaDto.getNome());
        pautaEntity.setDescricao(pautaDto.getDescricao());
	

		return pautaEntity;
	}
	
	public static List<PautaDto> toListPautaDto(List<Pauta> pautaEntities) {

		List<PautaDto> list = new ArrayList<PautaDto>();

		if(pautaEntities != null) {
			pautaEntities.stream().forEach(pauta -> {
				list.add(PautaMapper.toPautaDto(pauta));
			});
		}

		return list;
	}

	public static List<Pauta> toListPautaEntity(List<PautaDto> pautaDtos) {

		List<Pauta> list = new ArrayList<Pauta>();

		if(pautaDtos != null) {
			pautaDtos.stream().forEach(pauta -> {
				list.add(PautaMapper.toPautaEntity(pauta));
			});
		}

		return list;
	}
}