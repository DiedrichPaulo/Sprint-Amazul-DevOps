package br.com.fiap.nuclearquest.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "T_M_PERGUNTA")
@Getter @Setter @NoArgsConstructor
public class Pergunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pergunta")
    private Long id;

    @Column(name = "ds_enunciado", nullable = false, length = 500)
    private String enunciado;

    // updatable = false: a categoria faz parte da PK composta do modelo Oracle
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "T_M_CATEGORIA_id_categoria", nullable = false, updatable = false)
    private Categoria categoria;

    @OneToMany(mappedBy = "pergunta", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    private List<Alternativa> alternativas = new ArrayList<>();

    public void adicionarAlternativa(Alternativa alternativa) {
        alternativa.setPergunta(this);
        alternativas.add(alternativa);
    }
}