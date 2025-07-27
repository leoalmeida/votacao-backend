package space.lasf.votacao_backend.basicos;

import java.text.Normalizer;
import java.util.UUID;
import java.util.regex.Pattern;

import space.lasf.votacao_backend.core.component.CodeGenerator;
import space.lasf.votacao_backend.domain.model.Associado;
import space.lasf.votacao_backend.domain.model.Sessao;
import space.lasf.votacao_backend.domain.model.Voto;
import space.lasf.votacao_backend.domain.model.VotoOpcao;
import space.lasf.votacao_backend.domain.model.Pauta;
import space.lasf.votacao_backend.domain.model.Usuario;
import space.lasf.votacao_backend.dto.AssociadoDto;
import space.lasf.votacao_backend.dto.SessaoDto;
import space.lasf.votacao_backend.dto.VotoDto;
import space.lasf.votacao_backend.dto.PautaDto;
import space.lasf.votacao_backend.dto.UsuarioDto;
import space.lasf.votacao_backend.dto.mapper.AssociadoMapper;
import space.lasf.votacao_backend.dto.mapper.SessaoMapper;
import space.lasf.votacao_backend.dto.mapper.VotoMapper;
import space.lasf.votacao_backend.dto.mapper.PautaMapper;
import space.lasf.votacao_backend.dto.mapper.UsuarioMapper;

public class TestFactory {

    public static final String ASSOCIADOS_API_ENDPOINT = "/api/v1/associado";
    public static final String PAUTA_API_ENDPOINT = "/api/v1/pauta";
    public static final String SESSAO_API_ENDPOINT = "/api/v1/sessao";
    public static final String USUARIO_API_ENDPOINT = "/api/v1/usuario";
    public static final String REF_API_ENDPOINT = "/api/v1/referencia";

    
    public SessaoDto gerarSessaoDto(Associado associado) {
        return SessaoMapper.toSessaoDto(gerarSessao(associado));
    }

    public Sessao gerarSessao(Associado associado) {        
        return gerarSessao(associado, Pauta.builder().build());
    }

    public Sessao gerarSessao(Associado associado, Pauta pauta) {        
        if (null==associado) throw new IllegalArgumentException("Associado não pode ser nulo");
        if (null==pauta) throw new IllegalArgumentException("Pauta não pode ser nula");
        Sessao sessao = Sessao.builder()
                .id(UUID.randomUUID().toString())
                .pauta(pauta)
                .associado(associado)
                .build();
        return sessao;
    }

    public Voto gerarVoto(Sessao sessao) {
        if (null==sessao) throw new IllegalArgumentException("Sessao não pode ser nulo");
        Voto voto = Voto.builder()
                .id(UUID.randomUUID().toString())
                .idSessao(sessao.getId())
                .dataVoto(null)
                .opcao(((Math.random()*10)<=5)
                        ?VotoOpcao.NAO
                        :VotoOpcao.SIM)
                .build();
        return voto;
    }

    public VotoDto gerarVotoDto(Sessao sessao) {
        return VotoMapper.toVotoDto(gerarVoto(sessao));
    }

    public AssociadoDto gerarAssociadoDto(String nome, String telefone) {
        return AssociadoMapper.toAssociadoDto(gerarAssociado(nome,telefone));
    }

    public Associado gerarAssociado(String nome, String telefone) {
        String regNome = (null==nome||nome.isBlank())?"Jonas Morgan":nome;
        String email = removerAcentos(regNome).replace(" ", ".").concat("@example.com");
        Associado associado1 = Associado.builder()
                .id(UUID.randomUUID().toString())
                .nome(regNome)
                .email(email)
                .telefone((null==telefone||telefone.isBlank())?"(11) 99999-1111":telefone)
                .build();
        return associado1;
    }

    public PautaDto gerarPautaDto() {
        return PautaMapper.toPautaDto(gerarPauta());
    }

    public Pauta gerarPauta() {
        Integer regPauta = (int)(Math.random() * 999) + 1;
        Pauta produto = Pauta.builder()
                .id(UUID.randomUUID().toString())
                .nome("Pauta "+regPauta)
                .descricao("Descrição da Pauta " + regPauta)
                .build();
        return produto;
    }

    public UsuarioDto gerarUsuarioDto(String nome, String telefone) {
        return UsuarioMapper.toUsuarioDto(gerarUsuario(nome,telefone));
    }

    public Usuario gerarUsuario(String nome, String telefone) {
        String regNome = (null==nome||nome.isBlank())?"Jonas Morgan":nome;
        String login = removerAcentos(regNome).replace(" ", ".");
        String email = login.concat("@example.com");
        
        Usuario usuario = Usuario.builder()
                .id(UUID.randomUUID().toString())
                .login(login)
                .password(CodeGenerator.generateRandomCode(15))
                .email(email)
                .build();
        return usuario;
    }

    public static String removerAcentos(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }
}
