package br.com.fiap.nuclearquest.controller;

import br.com.fiap.nuclearquest.dto.*;
import br.com.fiap.nuclearquest.service.PerguntaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/perguntas")
@RequiredArgsConstructor
@Tag(name = "Perguntas", description = "CRUD das perguntas do quiz e endpoint para responder")
public class PerguntaController {

    private final PerguntaService service;

    @GetMapping
    @Operation(summary = "Lista todas as perguntas")
    public List<PerguntaResponse> listar() {
        return service.listar();
    }

    @GetMapping("/busca")
    @Operation(summary = "Busca perguntas pelo texto do enunciado",
            description = "Ignora maiúsculas/minúsculas e aceita um trecho do enunciado")
    public List<PerguntaResponse> buscar(
            @Parameter(description = "Trecho do enunciado", example = "radiacao")
            @RequestParam("texto") String texto) {
        return service.buscarPorTexto(texto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma pergunta pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pergunta encontrada"),
            @ApiResponse(responseCode = "404", description = "Pergunta não encontrada",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class)))
    })
    public PerguntaResponse buscarPorId(@PathVariable("id") Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastra uma nova pergunta com suas alternativas")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pergunta criada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou regra de negócio violada",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class))),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class)))
    })
    public PerguntaResponse criar(@Valid @RequestBody PerguntaRequest request) {
        return service.criar(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza enunciado e alternativas",
            description = "A categoria não pode ser alterada (faz parte da chave primária)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pergunta atualizada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou regra de negócio violada",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class))),
            @ApiResponse(responseCode = "404", description = "Pergunta não encontrada",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class))),
            @ApiResponse(responseCode = "409", description = "Alternativas já usadas em respostas",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class)))
    })
    public PerguntaResponse atualizar(@PathVariable("id") Long id,
                                      @Valid @RequestBody PerguntaRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove uma pergunta")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pergunta removida"),
            @ApiResponse(responseCode = "404", description = "Pergunta não encontrada",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class))),
            @ApiResponse(responseCode = "409", description = "Pergunta já possui respostas de usuários",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class)))
    })
    public void deletar(@PathVariable("id") Long id) {
        service.deletar(id);
    }

    @PostMapping("/{id}/responder")
    @Operation(summary = "Responde uma pergunta",
            description = "Registra a resposta do usuário e informa se acertou")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resposta registrada"),
            @ApiResponse(responseCode = "400", description = "Alternativa não pertence à pergunta",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class))),
            @ApiResponse(responseCode = "404", description = "Pergunta ou usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class)))
    })
    public ResultadoResponse responder(@PathVariable("id") Long id,
                                       @Valid @RequestBody RespostaRequest request) {
        return service.responder(id, request);
    }
}