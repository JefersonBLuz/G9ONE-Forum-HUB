package com.JefersonBLuz.forumhub.dto.resposta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DadosAtualizacaoResposta(
        @NotBlank
        @Size(max = 3000)
        String mensagem,
        @NotNull
        Long topicoId,
        @NotNull
        Long autorId,
        @NotNull
        Boolean solucao
) {
}
