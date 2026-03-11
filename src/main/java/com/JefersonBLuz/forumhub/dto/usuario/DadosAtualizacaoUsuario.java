package com.JefersonBLuz.forumhub.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DadosAtualizacaoUsuario(
        @NotBlank
        @Size(max = 120)
        String nome,
        @NotBlank
        @Email
        @Size(max = 160)
        String email,
        @NotBlank
        @Size(max = 255)
        String senha
) {
}
