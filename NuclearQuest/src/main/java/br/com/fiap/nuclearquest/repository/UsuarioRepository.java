package br.com.fiap.nuclearquest.repository;

import br.com.fiap.nuclearquest.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {}