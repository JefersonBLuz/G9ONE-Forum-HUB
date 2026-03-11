package com.JefersonBLuz.forumhub.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.JefersonBLuz.forumhub.domain.model.Topico;

import java.time.LocalDateTime;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    boolean existsByTituloAndMensagem(String titulo, String mensagem);

    Page<Topico> findAllByCursoNomeIgnoreCase(String nomeCurso, Pageable pageable);

    Page<Topico> findAllByDataCriacaoBetween(LocalDateTime inicio, LocalDateTime fim, Pageable pageable);

    Page<Topico> findAllByCursoNomeIgnoreCaseAndDataCriacaoBetween(
            String nomeCurso,
            LocalDateTime inicio,
            LocalDateTime fim,
            Pageable pageable
    );
}
