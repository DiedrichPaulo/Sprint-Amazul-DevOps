package br.com.fiap.nuclearquest.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "T_M_USUARIO")
@Getter @Setter @NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "nm_usuario", nullable = false, length = 50)
    private String nome;

    @Column(name = "nm_email", nullable = false, length = 150)
    private String email;

    @Column(name = "dt_cadastro", nullable = false)
    private LocalDate dataCadastro;
}