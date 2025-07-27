package space.lasf.votacao_backend.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import space.lasf.votacao_backend.domain.model.Associado;
import space.lasf.votacao_backend.dto.AssociadoDto;

@Component
public class AssociadoMapper {
    
    public static AssociadoDto toAssociadoDto(Associado Associado) {

		if (Associado == null)
			return null;

		return AssociadoDto.builder()
				.id(Associado.getId())
				.email(Associado.getEmail())
				.nome(Associado.getNome())
				.telefone(Associado.getTelefone())
				.build();
	}
	
	public static Associado toAssociadoEntity(AssociadoDto AssociadoDto) {

		if (AssociadoDto == null)
			return null;
		
		Associado AssociadoEntity = Associado.builder()
				.id(AssociadoDto.getId())
				.email(AssociadoDto.getEmail())
				.nome(AssociadoDto.getNome())
				.telefone(AssociadoDto.getTelefone())
				.build();

		return AssociadoEntity;
	}
	
	public static List<AssociadoDto> toListAssociadoDto(List<Associado> AssociadoEntities) {

		if (AssociadoEntities == null) {
			return new ArrayList<>();
		}
		return AssociadoEntities.stream()
				.map(AssociadoMapper::toAssociadoDto)
				.toList();
	}

	public static List<Associado> toListAssociadoEntity(List<AssociadoDto> AssociadoDtos) {

		if (AssociadoDtos == null) {
			return new ArrayList<>();
		}
		return AssociadoDtos.stream()
				.map(AssociadoMapper::toAssociadoEntity)
				.toList();
	}
}