package br.com.fiap.nuclearquest.repository;

import br.com.fiap.nuclearquest.model.Pergunta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PerguntaRepository extends JpaRepository<Pergunta, Long> {

    // Query Method - busc por texto

    List<Pergunta> findByEnunciadoContainingIgnoreCase(String texto);
}