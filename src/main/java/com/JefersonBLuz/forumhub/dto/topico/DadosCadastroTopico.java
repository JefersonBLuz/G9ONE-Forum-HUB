package com.JefersonBLuz.forumhub.dto.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DadosCadastroTopico(
        @NotBlank
        @Size(max = 150)
        String titulo,

        @NotBlank
        @Size(max = 3000)
        String mensagem,

        @NotNull
        Long autorId,

        @NotNull
        Long cursoId
) {
}
