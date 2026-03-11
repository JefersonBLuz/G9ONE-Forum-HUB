package com.JefersonBLuz.forumhub.dto.topico;

import com.JefersonBLuz.forumhub.domain.model.Topico;

import java.time.LocalDateTime;

public record DadosDetalhamentoTopico(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        String estado,
        Long autorId,
        String autorNome,
        Long cursoId,
        String cursoNome
) {
    public DadosDetalhamentoTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getEstado().name(),
                topico.getAutor().getId(),
                topico.getAutor().getNome(),
                topico.getCurso().getId(),
                topico.getCurso().getNome()
        );
    }
}
