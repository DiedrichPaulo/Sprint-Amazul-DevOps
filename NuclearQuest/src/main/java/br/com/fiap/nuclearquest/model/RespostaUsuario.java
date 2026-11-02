package br.com.fiap.nuclearquest.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "T_M_RESPOSTA_USUARIO")
@Getter @Setter @NoArgsConstructor
public class RespostaUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resposta")
    private Long id;

    @Column(name = "dt_resposta", nullable = false)
    private LocalDateTime dataResposta;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "T_M_USUARIO_id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "T_M_ALTERNATIVA_id_alternativa", nullable = false)
    private Alternativa alternativa;

    // Colunas extras das FKs compostas (preenchidas pelo service)
    @Column(name = "T_M_ALTERNATIVA_T_M_PERGUNTA_id_pergunta", nullable = false)
    private Long perguntaId;

    @Column(name = "T_M_ALTERNATIVA_T_M_PERGUNTA_T_M_CATEGORIA_id_categoria", nullable = false)
    private Long alternativaCategoriaId;

    @Column(name = "T_M_CATEGORIA_id_categoria", nullable = false)
    private Long categoriaId;
}