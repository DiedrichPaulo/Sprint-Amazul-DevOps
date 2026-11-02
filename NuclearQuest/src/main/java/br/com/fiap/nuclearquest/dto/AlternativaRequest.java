package br.com.fiap.nuclearquest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AlternativaRequest(
        @NotBlank(message = "O texto da alternativa é obrigatório")
        @Size(max = 200, message = "O texto deve ter no máximo 200 caracteres")
        @Schema(example = "Urânio") String texto,

        @NotNull(message = "Informe se a alternativa é correta")
        @Schema(example = "true") Boolean correta) {}