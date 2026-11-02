package br.com.fiap.nuclearquest.dto;

import java.util.List;

public record PerguntaResponse(Long id, String enunciado, Long categoriaId,
                               String categoriaNome, List<AlternativaResponse> alternativas) {}