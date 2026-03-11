package com.JefersonBLuz.forumhub.service;

import com.JefersonBLuz.forumhub.domain.model.Resposta;
import com.JefersonBLuz.forumhub.domain.model.Topico;
import com.JefersonBLuz.forumhub.domain.model.Usuario;
import com.JefersonBLuz.forumhub.domain.repository.RespostaRepository;
import com.JefersonBLuz.forumhub.domain.repository.TopicoRepository;
import com.JefersonBLuz.forumhub.domain.repository.UsuarioRepository;
import com.JefersonBLuz.forumhub.dto.resposta.DadosAtualizacaoResposta;
import com.JefersonBLuz.forumhub.dto.resposta.DadosCadastroResposta;
import com.JefersonBLuz.forumhub.dto.resposta.DadosDetalhamentoResposta;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RespostaService {

    private final RespostaRepository respostaRepository;
    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;

    public RespostaService(
            RespostaRepository respostaRepository,
            TopicoRepository topicoRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.respostaRepository = respostaRepository;
        this.topicoRepository = topicoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public DadosDetalhamentoResposta cadastrar(DadosCadastroResposta dados) {
        Topico topico = topicoRepository.findById(dados.topicoId())
                .orElseThrow(() -> new EntityNotFoundException("Topico nao encontrado para o id informado."));
        Usuario autor = usuarioRepository.findById(dados.autorId())
                .orElseThrow(() -> new EntityNotFoundException("Autor nao encontrado para o id informado."));

        Resposta resposta = new Resposta(dados.mensagem(), topico, autor);
        Resposta respostaSalva = respostaRepository.save(resposta);
        return new DadosDetalhamentoResposta(respostaSalva);
    }

    @Transactional(readOnly = true)
    public List<DadosDetalhamentoResposta> listar() {
        return respostaRepository.findAll().stream()
                .map(DadosDetalhamentoResposta::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public DadosDetalhamentoResposta detalhar(Long id) {
        if (id == null || id < 1) {
            throw new IllegalArgumentException("O id informado deve ser maior que zero.");
        }

        Resposta resposta = respostaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Resposta nao encontrada para o id informado."));
        return new DadosDetalhamentoResposta(resposta);
    }

    @Transactional
    public DadosDetalhamentoResposta atualizar(Long id, DadosAtualizacaoResposta dados) {
        if (id == null || id < 1) {
            throw new IllegalArgumentException("O id informado deve ser maior que zero.");
        }

        Optional<Resposta> respostaOptional = respostaRepository.findById(id);
        if (!respostaOptional.isPresent()) {
            throw new EntityNotFoundException("Resposta nao encontrada para o id informado.");
        }

        Topico topico = topicoRepository.findById(dados.topicoId())
                .orElseThrow(() -> new EntityNotFoundException("Topico nao encontrado para o id informado."));
        Usuario autor = usuarioRepository.findById(dados.autorId())
                .orElseThrow(() -> new EntityNotFoundException("Autor nao encontrado para o id informado."));

        Resposta resposta = respostaOptional.get();
        resposta.atualizar(dados.mensagem(), topico, autor, dados.solucao());

        return new DadosDetalhamentoResposta(resposta);
    }

    @Transactional
    public void excluir(Long id) {
        if (id == null || id < 1) {
            throw new IllegalArgumentException("O id informado deve ser maior que zero.");
        }

        Optional<Resposta> respostaOptional = respostaRepository.findById(id);
        if (!respostaOptional.isPresent()) {
            throw new EntityNotFoundException("Resposta nao encontrada para o id informado.");
        }

        respostaRepository.deleteById(id);
    }
}
