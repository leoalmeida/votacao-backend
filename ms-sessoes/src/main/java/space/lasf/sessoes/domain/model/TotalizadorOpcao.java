package space.lasf.sessoes.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "totalizadores")
@Setter
@Getter
@NoArgsConstructor
public class TotalizadorOpcao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Sessao sessao;

    @Column(name = "opcao")
    private VotoOpcao opcaoVoto;

    private Long quantidade;

    public TotalizadorOpcao(final VotoOpcao opcaoVoto, final Long quantidade) {
        this.opcaoVoto = opcaoVoto;
        this.quantidade = quantidade;
    }
}
