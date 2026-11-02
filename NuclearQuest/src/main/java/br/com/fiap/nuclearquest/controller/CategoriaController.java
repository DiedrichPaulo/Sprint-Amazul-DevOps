package br.com.fiap.nuclearquest.controller;

import br.com.fiap.nuclearquest.dto.CategoriaResponse;
import br.com.fiap.nuclearquest.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
@Tag(name = "Categorias", description = "Temas do quiz nuclear")
public class CategoriaController {

    private final CategoriaService service;

    @GetMapping
    @Operation(summary = "Lista todas as categorias")
    public List<CategoriaResponse> listar() {
        return service.listar();
    }
}