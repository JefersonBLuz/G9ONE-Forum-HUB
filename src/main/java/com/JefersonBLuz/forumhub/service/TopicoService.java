package com.JefersonBLuz.forumhub.service;

import com.JefersonBLuz.forumhub.domain.model.Curso;
import com.JefersonBLuz.forumhub.domain.model.Topico;
import com.JefersonBLuz.forumhub.domain.model.Usuario;
import com.JefersonBLuz.forumhub.domain.repository.CursoRepository;
import com.JefersonBLuz.forumhub.domain.repository.TopicoRepository;
import com.JefersonBLuz.forumhub.domain.repository.UsuarioRepository;
import com.JefersonBLuz.forumhub.dto.topico.DadosCadastroTopico;
import com.JefersonBLuz.forumhub.dto.topico.DadosDetalhamentoTopico;
import com.JefersonBLuz.forumhub.infra.exception.RegraDeNegocioException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TopicoService {

    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;

    public TopicoService(
            TopicoRepository topicoRepository,
            UsuarioRepository usuarioRepository,
            CursoRepository cursoRepository
    ) {
        this.topicoRepository = topicoRepository;
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
    }

    @Transactional
    public DadosDetalhamentoTopico cadastrar(DadosCadastroTopico dados) {
        if (topicoRepository.existsByTituloAndMensagem(dados.titulo(), dados.mensagem())) {
            throw new RegraDeNegocioException("Ja existe um topico com o mesmo titulo e mensagem.");
        }

        Usuario autor = usuarioRepository.findById(dados.autorId())
                .orElseThrow(() -> new EntityNotFoundException("Autor nao encontrado para o id informado."));

        Curso curso = cursoRepository.findById(dados.cursoId())
                .orElseThrow(() -> new EntityNotFoundException("Curso nao encontrado para o id informado."));

        Topico topico = new Topico(dados.titulo(), dados.mensagem(), autor, curso);
        Topico topicoSalvo = topicoRepository.save(topico);
        return new DadosDetalhamentoTopico(topicoSalvo);
    }
}
