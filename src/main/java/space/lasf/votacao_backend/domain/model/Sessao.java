package space.lasf.votacao_backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import java.io.Serial;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;


/**
 * Entidade que representa um item de pedido.
 */
@Document(collection  = "sessoes")
@Data
@Getter
@AllArgsConstructor
public class Sessao {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @DBRef
    private Associado associado;

    @Field(name = "status")
    private SessaoStatus status;

    private VotoOpcao resultado;

    @Field(name = "data-fim")
    private LocalDateTime dataFimSessao;

    @Field(name = "data-inicio")
    private LocalDateTime dataInicioSessao;

    @DBRef
    private Pauta pauta;

    @DBRef
    @JsonIgnoreProperties("sessoes")
    @JsonManagedReference
    private Set<Voto> votos;

    private List<TotalizadorOpcao> totalizadores;

    @Builder
    public Sessao(String id, Associado associado, Pauta pauta) {
        this.id = id;
        this.associado = associado;
        this.pauta = pauta;
        this.votos = null;
        this.totalizadores = null;
        this.resultado = null;
        this.dataInicioSessao = null;
        this.dataFimSessao = null;
        this.status = SessaoStatus.CREATED;
    }

    public Sessao iniciarSessao(){
        if (!SessaoStatus.CREATED.equals(this.getStatus())){
            throw new IllegalArgumentException("Sessão já foi iniciada!");
        }
        this.votos = new HashSet<>();
        this.totalizadores = new ArrayList<>();
        this.dataInicioSessao = LocalDateTime.now();
        this.status = SessaoStatus.OPEN_TO_VOTE;
        return this;
    }   

    public Sessao finalizarSessao(){
        if (!SessaoStatus.OPEN_TO_VOTE.equals(this.getStatus())){
            throw new IllegalArgumentException("Sessão não está aberta!");
        }
        this.dataFimSessao = LocalDateTime.now(); 
        this.status = SessaoStatus.CLOSED;
        this.totalizarVotos();
        return this;
    }
    
    public Sessao cancelarSessao(){
        if (!SessaoStatus.OPEN_TO_VOTE.equals(this.getStatus())){
            throw new IllegalArgumentException("Sessão não está aberta!");
        }
        this.dataFimSessao = LocalDateTime.now(); 
        this.status = SessaoStatus.CANCELLED;
        return this;
    }

    public Sessao contabilizarVoto(VotoOpcao opcao, String idAssociado){
        Voto novoVoto = Voto.builder()
            .idSessao(this.id)
            .idAssociado(idAssociado)
            .opcao(opcao)
            .dataVoto(LocalDateTime.now()).build();
        this.votos.add(novoVoto);
        return this;
    }

    private void totalizarVotos() {
        if (null==this.votos){
            throw new IllegalArgumentException("Sessão não foi iniciada corretamente!");
        }
        if (!SessaoStatus.OPEN_TO_VOTE.equals(this.getStatus())){
            throw new IllegalArgumentException("Sessão não está aberta para votação!");
        }
        this.totalizadores = new ArrayList<>();
        for (VotoOpcao opcao : VotoOpcao.values()) {
            Long contagem = this.votos.stream()
                .filter(v -> v.getOpcao().equals(opcao))
                .count();
            this.totalizadores.add(new TotalizadorOpcao(opcao,contagem));
        }

        this.resultado = this.totalizadores.stream()
                .max((o1, o2) -> o1.getQuantidade().compareTo(o2.getQuantidade()))
                .get().getOpcaoVoto();
    }

}
