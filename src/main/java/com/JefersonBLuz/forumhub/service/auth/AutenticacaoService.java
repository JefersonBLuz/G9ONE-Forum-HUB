package com.JefersonBLuz.forumhub.service.auth;

import com.JefersonBLuz.forumhub.domain.model.Perfil;
import com.JefersonBLuz.forumhub.domain.model.Usuario;
import com.JefersonBLuz.forumhub.domain.repository.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AutenticacaoService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public AutenticacaoService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario nao encontrado para o login informado."));

        return User.withUsername(usuario.getEmail())
                .password(usuario.getSenha())
                .authorities(obterAuthorities(usuario))
                .build();
    }

    private Collection<? extends GrantedAuthority> obterAuthorities(Usuario usuario) {
        return usuario.getPerfis().stream()
                .map(Perfil::getNome)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}
