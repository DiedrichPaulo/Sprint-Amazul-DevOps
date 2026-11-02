package br.com.fiap.nuclearquest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record PerguntaRequest(
        @NotBlank(message = "O enunciado é obrigatório")
        @Size(max = 500, message = "O enunciado deve ter no máximo 500 caracteres")
        @Schema(example = "Qual elemento é usado como combustível nas usinas nucleares?") String enunciado,

        @NotNull(message = "A categoria é obrigatória")
        @Schema(example = "3") Long categoriaId,

        @NotNull(message = "As alternativas são obrigatórias")
        @Size(min = 2, max = 6, message = "A pergunta deve ter de 2 a 6 alternativas")
        @Valid List<AlternativaRequest> alternativas) {}