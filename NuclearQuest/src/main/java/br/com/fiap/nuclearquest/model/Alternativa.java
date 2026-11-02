package br.com.fiap.nuclearquest.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "T_M_ALTERNATIVA")
@Getter @Setter @NoArgsConstructor
public class Alternativa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alternativa")
    private Long id;

    @Column(name = "ds_texto", nullable = false, length = 200)
    private String texto;

    @Convert(converter = SimNaoConverter.class)
    @Column(name = "ds_correta", nullable = false, length = 1)
    private Boolean correta;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "T_M_PERGUNTA_id_pergunta", nullable = false, updatable = false)
    private Pergunta pergunta;

    @Column(name = "T_M_PERGUNTA_T_M_CATEGORIA_id_categoria", nullable = false, updatable = false)
    private Long categoriaId;

    @PrePersist
    void sincronizarCategoria() {
        this.categoriaId = pergunta.getCategoria().getId();
    }
}