package br.com.fiap.nuclearquest.exception;

import br.com.fiap.nuclearquest.dto.ErroResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice(basePackages = "br.com.fiap.nuclearquest.controller")
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResponse> naoEncontrado(RecursoNaoEncontradoException ex) {
        return montar(HttpStatus.NOT_FOUND, ex.getMessage(), List.of());
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ErroResponse> regraNegocio(RegraNegocioException ex) {
        return montar(HttpStatus.BAD_REQUEST, ex.getMessage(), List.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> validacao(MethodArgumentNotValidException ex) {
        List<String> detalhes = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .toList();
        return montar(HttpStatus.BAD_REQUEST, "Dados inválidos", detalhes);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResponse> jsonInvalido(HttpMessageNotReadableException ex) {
        return montar(HttpStatus.BAD_REQUEST, "JSON inválido", List.of());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroResponse> integridade(DataIntegrityViolationException ex) {
        return montar(HttpStatus.CONFLICT,
                "Operação não permitida", List.of());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponse> generico(Exception ex) {
        return montar(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor", List.of());
    }

    private ResponseEntity<ErroResponse> montar(HttpStatus status, String mensagem, List<String> detalhes) {
        ErroResponse corpo = new ErroResponse(LocalDateTime.now(), status.value(),
                status.getReasonPhrase(), mensagem, detalhes);
        return ResponseEntity.status(status).body(corpo);
    }
}