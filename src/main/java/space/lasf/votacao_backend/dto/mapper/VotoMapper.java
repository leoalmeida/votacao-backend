package space.lasf.votacao_backend.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import space.lasf.votacao_backend.domain.model.Voto;
import space.lasf.votacao_backend.dto.VotoDto;


@Component
public class VotoMapper {
    
    public static VotoDto toVotoDto(Voto voto) {

		if (voto == null)
			return null;

		return VotoDto.builder()
				.id(voto.getId())
				.opcao(voto.getOpcao())
				.dataVoto(voto.getDataVoto())
				.idSessao(voto.getIdSessao())
				.build();
	}
	
	public static Voto toVotoEntity(VotoDto votoDto) {

		if (votoDto == null)
			return null;
		
		Voto votoEntity = new Voto();
		
        votoEntity.setId(votoDto.getId());
		votoEntity.setOpcao(votoDto.getOpcao());
        votoEntity.setDataVoto(votoDto.getDataVoto());
        votoEntity.setIdSessao(votoDto.getIdSessao());
        

		return votoEntity;
	}
	
	public static List<VotoDto> toListVotoDto(List<Voto> votos) {

		if (votos == null) {
			return new ArrayList<>();
		}
		return votos.stream()
				.map(VotoMapper::toVotoDto)
				.toList();
	}

	public static List<Voto> toListVotoEntity(List<VotoDto> votoDtos) {

		if (votoDtos == null) {
			return new ArrayList<>();
		}
		return votoDtos.stream()
				.map(VotoMapper::toVotoEntity)
				.toList();
	}
}