package com.JefersonBLuz.forumhub.service;

import com.JefersonBLuz.forumhub.domain.model.Perfil;
import com.JefersonBLuz.forumhub.domain.model.Usuario;
import com.JefersonBLuz.forumhub.domain.repository.PerfilRepository;
import com.JefersonBLuz.forumhub.domain.repository.UsuarioRepository;
import com.JefersonBLuz.forumhub.dto.usuario.DadosAtualizacaoUsuario;
import com.JefersonBLuz.forumhub.dto.usuario.DadosCadastroUsuario;
import com.JefersonBLuz.forumhub.dto.usuario.DadosDetalhamentoUsuario;
import com.JefersonBLuz.forumhub.infra.exception.RegraDeNegocioException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PerfilRepository perfilRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public DadosDetalhamentoUsuario cadastrar(DadosCadastroUsuario dados) {
        if (usuarioRepository.existsByEmail(dados.email())) {
            throw new RegraDeNegocioException("Ja existe um usuario com o email informado.");
        }

        Perfil perfilPadrao = perfilRepository.findByNome("ROLE_USER")
                .orElseThrow(() -> new EntityNotFoundException("Perfil padrao ROLE_USER nao encontrado."));

        Usuario usuario = new Usuario(
                dados.nome(),
                dados.email(),
                passwordEncoder.encode(dados.senha())
        );
        usuario.adicionarPerfil(perfilPadrao);

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return new DadosDetalhamentoUsuario(usuarioSalvo);
    }

    @Transactional(readOnly = true)
    public List<DadosDetalhamentoUsuario> listar() {
        return usuarioRepository.findAll().stream()
                .map(DadosDetalhamentoUsuario::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public DadosDetalhamentoUsuario detalhar(Long id) {
        if (id == null || id < 1) {
            throw new IllegalArgumentException("O id informado deve ser maior que zero.");
        }

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario nao encontrado para o id informado."));
        return new DadosDetalhamentoUsuario(usuario);
    }

    @Transactional
    public DadosDetalhamentoUsuario atualizar(Long id, DadosAtualizacaoUsuario dados) {
        if (id == null || id < 1) {
            throw new IllegalArgumentException("O id informado deve ser maior que zero.");
        }

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (!usuarioOptional.isPresent()) {
            throw new EntityNotFoundException("Usuario nao encontrado para o id informado.");
        }

        if (usuarioRepository.existsByEmailAndIdNot(dados.email(), id)) {
            throw new RegraDeNegocioException("Ja existe um usuario com o email informado.");
        }

        Usuario usuario = usuarioOptional.get();
        usuario.atualizarDados(
                dados.nome(),
                dados.email(),
                passwordEncoder.encode(dados.senha())
        );

        return new DadosDetalhamentoUsuario(usuario);
    }

    @Transactional
    public void excluir(Long id) {
        if (id == null || id < 1) {
            throw new IllegalArgumentException("O id informado deve ser maior que zero.");
        }

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (!usuarioOptional.isPresent()) {
            throw new EntityNotFoundException("Usuario nao encontrado para o id informado.");
        }

        usuarioRepository.deleteById(id);
    }
}
