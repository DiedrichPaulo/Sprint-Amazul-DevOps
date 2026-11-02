package br.com.fiap.nuclearquest.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ErroResponse(LocalDateTime timestamp, int status, String erro,
                           String mensagem, List<String> detalhes) {}