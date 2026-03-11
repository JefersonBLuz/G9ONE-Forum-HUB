package com.JefersonBLuz.forumhub.dto.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DadosCadastroCurso(
        @NotBlank
        @Size(max = 120)
        String nome,
        @NotBlank
        @Size(max = 120)
        String categoria
) {
}
