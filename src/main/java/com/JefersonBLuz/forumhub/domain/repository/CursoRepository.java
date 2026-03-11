package com.JefersonBLuz.forumhub.domain.repository;

import com.JefersonBLuz.forumhub.domain.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
