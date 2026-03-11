package com.JefersonBLuz.forumhub.domain.repository;

import com.JefersonBLuz.forumhub.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
