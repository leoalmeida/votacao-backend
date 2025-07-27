package space.lasf.votacao_backend.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import space.lasf.votacao_backend.domain.model.Associado;
import space.lasf.votacao_backend.domain.model.Sessao;
import space.lasf.votacao_backend.dto.SessaoDto;




@Component
public class SessaoMapper {
    
    public static SessaoDto toSessaoDto(Sessao sessao) {

		if (sessao == null)
			return null;

		return SessaoDto.builder()
				.id(sessao.getId())
				.pautaDto(PautaMapper.toPautaDto(sessao.getPauta()))
				.resultado(sessao.getResultado().name())
				.dataInicioSessao(sessao.getDataInicioSessao())
				.dataFimSessao(sessao.getDataFimSessao())
				.status(sessao.getStatus().name())
				.totalizadores(sessao.getTotalizadores())
				.build();
	}
	
	public static Sessao toSessaoEntity(SessaoDto sessaoDto, Associado associado) {

		if (sessaoDto == null)
			return null;
		
		Sessao sessaoEntity = Sessao.builder()
					.id(sessaoDto.getId())
					.associado(associado)
					.pauta(PautaMapper.toPautaEntity(sessaoDto.getPautaDto()))
					.build();
		
		/*sessaoEntity.setId(sessaoDto.getId());
        sessaoEntity.setStatus(SessaoStatus.valueOf(sessaoDto.getStatus()));
        sessaoEntity.setDataInicioSessao(sessaoDto.getDataInicioSessao());
        sessaoEntity.setDataFimSessao(sessaoDto.getDataFimSessao());
        sessaoEntity.setResultado(VotoOpcao.valueOf(sessaoDto.getResultado()));
        sessaoEntity.setPauta(PautaMapper.toPautaEntity(sessaoDto.getPautaDto()));
		sessaoEntity.setTotalizadores(sessaoDto.getTotalizadores());
		*/
		return sessaoEntity;
	}
	
	public static List<SessaoDto> toListSessaoDto(List<Sessao> sessaoEntities) {

		List<SessaoDto> list = new ArrayList<SessaoDto>();

		if(sessaoEntities != null) {
			sessaoEntities.stream().forEach(sessao -> {
				list.add(SessaoMapper.toSessaoDto(sessao));
			});
		}

		return list;
	}

	public static List<Sessao> toListSessaoEntity(List<SessaoDto> sessaoDtos, Associado associado) {

		List<Sessao> list = new ArrayList<Sessao>();

		if(sessaoDtos != null) {
			sessaoDtos.stream().forEach(sessaoDto -> {
				list.add(SessaoMapper.toSessaoEntity(sessaoDto,associado));
			});
		}

		return list;
	}
}