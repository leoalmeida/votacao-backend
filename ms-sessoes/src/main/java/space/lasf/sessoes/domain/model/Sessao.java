package space.lasf.sessoes.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa um item de pedido.
 */
@Entity
@Table(name = "sessoes")
@Data
@Getter
@NoArgsConstructor
public class Sessao {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pauta_id")
    private Long idPauta;

    @Column(name = "status")
    @NotNull
    @Enumerated(EnumType.STRING)
    private SessaoStatus status;

    @Column(name = "resultado")
    @Enumerated(EnumType.STRING)
    private VotoOpcao resultado;

    @Column(name = "data_fim")
    private LocalDateTime dataFimSessao;

    @Column(name = "data_inicio")
    private LocalDateTime dataInicioSessao;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "sessao")
    private List<TotalizadorOpcao> totalizadores;

    public List<TotalizadorOpcao> getTotalizadores() {
        return copyTotalizadores(totalizadores);
    }

    public void setTotalizadores(final List<TotalizadorOpcao> totalizadores) {
        this.totalizadores = copyTotalizadores(totalizadores);
    }

    @Builder
    public Sessao(final Long id, final Long idPauta) {
        this.id = id;
        this.idPauta = idPauta;
        this.totalizadores = null;
        this.resultado = null;
        this.dataInicioSessao = null;
        this.dataFimSessao = null;
        this.status = SessaoStatus.CREATED;
    }

    public Sessao iniciarSessao() {
        if (!SessaoStatus.CREATED.equals(this.getStatus())) {
            throw new IllegalArgumentException("Sessão já foi iniciada!");
        }
        this.totalizadores = new ArrayList<>();
        this.dataInicioSessao = LocalDateTime.now();
        this.status = SessaoStatus.OPEN_TO_VOTE;
        return this;
    }

    public Sessao finalizarSessao() {
        if (!SessaoStatus.OPEN_TO_VOTE.equals(this.getStatus())) {
            throw new IllegalArgumentException("Sessão não está aberta!");
        }
        this.dataFimSessao = LocalDateTime.now();
        this.status = SessaoStatus.CLOSED;
        return this;
    }

    public Sessao cancelarSessao() {
        if (!SessaoStatus.OPEN_TO_VOTE.equals(this.getStatus())) {
            throw new IllegalArgumentException("Sessão não está aberta!");
        }
        this.dataFimSessao = LocalDateTime.now();
        this.status = SessaoStatus.CANCELLED;
        return this;
    }

    public Sessao totalizarVotos(final List<Voto> votos) {
        if (null == votos) {
            throw new IllegalArgumentException("Sessão não foi iniciada corretamente!");
        }
        if (!SessaoStatus.OPEN_TO_VOTE.equals(this.getStatus())) {
            throw new IllegalArgumentException("Sessão não está aberta para votação!");
        }
        this.totalizadores = new ArrayList<>();
        for (VotoOpcao opcao : VotoOpcao.values()) {
            Long contagem =
                    votos.stream().filter(v -> v.getOpcao().equals(opcao)).count();
            this.totalizadores.add(new TotalizadorOpcao(opcao, contagem));
        }

        this.resultado = this.totalizadores.stream()
                .max((o1, o2) -> o1.getQuantidade().compareTo(o2.getQuantidade()))
                .get()
                .getOpcaoVoto();

        return this;
    }

    private static List<TotalizadorOpcao> copyTotalizadores(final List<TotalizadorOpcao> source) {
        if (source == null) {
            return null;
        }

        List<TotalizadorOpcao> copy = new ArrayList<>();
        for (TotalizadorOpcao item : source) {
            if (item == null) {
                copy.add(null);
            } else {
                TotalizadorOpcao totalizador = new TotalizadorOpcao(item.getOpcaoVoto(), item.getQuantidade());
                totalizador.setId(item.getId());
                copy.add(totalizador);
            }
        }
        return copy;
    }
}
