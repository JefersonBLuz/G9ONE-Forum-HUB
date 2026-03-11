package com.JefersonBLuz.forumhub.domain.repository;

import com.JefersonBLuz.forumhub.domain.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    Optional<Perfil> findByNome(String nome);
}
