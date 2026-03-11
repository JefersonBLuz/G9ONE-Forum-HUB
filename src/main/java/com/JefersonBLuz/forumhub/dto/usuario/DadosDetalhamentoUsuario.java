package com.JefersonBLuz.forumhub.dto.usuario;

import com.JefersonBLuz.forumhub.domain.model.Usuario;

import java.util.Set;

public record DadosDetalhamentoUsuario(
        Long id,
        String nome,
        String email,
        Set<String> perfis
) {
    public DadosDetalhamentoUsuario(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPerfis().stream().map(perfil -> perfil.getNome()).collect(java.util.stream.Collectors.toSet())
        );
    }
}
