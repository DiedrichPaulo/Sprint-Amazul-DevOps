package br.com.fiap.nuclearquest.service;

import br.com.fiap.nuclearquest.dto.CategoriaResponse;
import br.com.fiap.nuclearquest.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository repository;

    @Transactional(readOnly = true)
    public List<CategoriaResponse> listar() {
        return repository.findAll().stream()
                .map(c -> new CategoriaResponse(c.getId(), c.getNome(), c.getDescricao()))
                .toList();
    }
}