package com.JefersonBLuz.forumhub.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JefersonBLuz.forumhub.domain.model.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    boolean existsByTituloAndMensagem(String titulo, String mensagem);
}
