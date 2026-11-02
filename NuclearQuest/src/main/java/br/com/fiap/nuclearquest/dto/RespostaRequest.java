package br.com.fiap.nuclearquest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record RespostaRequest(
        @NotNull(message = "O usuário é obrigatório") @Schema(example = "1") Long usuarioId,
        @NotNull(message = "A alternativa é obrigatória") @Schema(example = "1") Long alternativaId) {}