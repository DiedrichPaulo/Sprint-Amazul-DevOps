package br.com.fiap.nuclearquest.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "T_M_CATEGORIA")
@Getter @Setter @NoArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long id;

    @Column(name = "nm_categoria", nullable = false, length = 50)
    private String nome;

    @Column(name = "ds_categoria", nullable = false, length = 50)
    private String descricao;
}