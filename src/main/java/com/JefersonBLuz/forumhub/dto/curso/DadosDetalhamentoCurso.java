package com.JefersonBLuz.forumhub.dto.curso;

import com.JefersonBLuz.forumhub.domain.model.Curso;

public record DadosDetalhamentoCurso(
        Long id,
        String nome,
        String categoria
) {
    public DadosDetalhamentoCurso(Curso curso) {
        this(curso.getId(), curso.getNome(), curso.getCategoria());
    }
}
