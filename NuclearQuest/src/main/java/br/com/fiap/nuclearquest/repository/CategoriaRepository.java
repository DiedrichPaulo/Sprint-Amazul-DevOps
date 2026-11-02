package br.com.fiap.nuclearquest.repository;

import br.com.fiap.nuclearquest.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {}