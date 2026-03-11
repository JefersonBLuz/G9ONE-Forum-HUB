package com.JefersonBLuz.forumhub.service;

import com.JefersonBLuz.forumhub.domain.model.Curso;
import com.JefersonBLuz.forumhub.domain.model.Topico;
import com.JefersonBLuz.forumhub.domain.model.Usuario;
import com.JefersonBLuz.forumhub.domain.repository.CursoRepository;
import com.JefersonBLuz.forumhub.domain.repository.TopicoRepository;
import com.JefersonBLuz.forumhub.domain.repository.UsuarioRepository;
import com.JefersonBLuz.forumhub.dto.topico.DadosAtualizacaoTopico;
import com.JefersonBLuz.forumhub.dto.topico.DadosCadastroTopico;
import com.JefersonBLuz.forumhub.dto.topico.DadosDetalhamentoTopico;
import com.JefersonBLuz.forumhub.infra.exception.RegraDeNegocioException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

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

    @Transactional(readOnly = true)
    public Page<DadosDetalhamentoTopico> listar(String nomeCurso, Integer ano, Integer page, Integer limit, String sort) {
        if (ano != null && ano < 1) {
            throw new RegraDeNegocioException("O ano informado deve ser maior que zero.");
        }

        PageRequest pageableSeguro = criarPageRequest(page, limit, sort);

        boolean filtrarPorCurso = nomeCurso != null && !nomeCurso.isBlank();
        boolean filtrarPorAno = ano != null;

        Page<Topico> paginaTopicos;
        if (filtrarPorCurso && filtrarPorAno) {
            LocalDateTime inicioAno = LocalDate.of(ano, 1, 1).atStartOfDay();
            LocalDateTime fimAno = LocalDate.of(ano, 12, 31).atTime(23, 59, 59);
            paginaTopicos = topicoRepository.findAllByCursoNomeIgnoreCaseAndDataCriacaoBetween(
                    nomeCurso.trim(),
                    inicioAno,
                    fimAno,
                    pageableSeguro
            );
        } else if (filtrarPorCurso) {
            paginaTopicos = topicoRepository.findAllByCursoNomeIgnoreCase(nomeCurso.trim(), pageableSeguro);
        } else if (filtrarPorAno) {
            LocalDateTime inicioAno = LocalDate.of(ano, 1, 1).atStartOfDay();
            LocalDateTime fimAno = LocalDate.of(ano, 12, 31).atTime(23, 59, 59);
            paginaTopicos = topicoRepository.findAllByDataCriacaoBetween(inicioAno, fimAno, pageableSeguro);
        } else {
            paginaTopicos = topicoRepository.findAll(pageableSeguro);
        }

        return paginaTopicos.map(DadosDetalhamentoTopico::new);
    }

    private PageRequest criarPageRequest(Integer page, Integer limit, String sort) {
        int pagina = page == null ? 0 : Math.max(page, 0);
        int tamanho = limit == null ? 10 : Math.min(Math.max(limit, 1), 100);

        Set<String> ordenacoesPermitidas = Set.of("id", "titulo", "mensagem", "dataCriacao", "estado");
        String propriedade = "dataCriacao";
        Sort.Direction direcao = Sort.Direction.ASC;

        if (sort != null && !sort.isBlank()) {
            String[] partes = sort.split(",");
            String coluna = partes[0].trim();
            if (ordenacoesPermitidas.contains(coluna)) {
                propriedade = coluna;
            }

            if (partes.length > 1) {
                String valorDirecao = partes[1].trim();
                if ("desc".equalsIgnoreCase(valorDirecao)) {
                    direcao = Sort.Direction.DESC;
                }
            }
        }

        return PageRequest.of(pagina, tamanho, Sort.by(new Sort.Order(direcao, propriedade)));
    }

    @Transactional(readOnly = true)
    public DadosDetalhamentoTopico detalhar(Long id) {
        if (id == null || id < 1) {
            throw new IllegalArgumentException("O id informado deve ser maior que zero.");
        }

        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Topico nao encontrado para o id informado."));

        return new DadosDetalhamentoTopico(topico);
    }

    @Transactional
    public DadosDetalhamentoTopico atualizar(Long id, DadosAtualizacaoTopico dados) {
        if (id == null || id < 1) {
            throw new IllegalArgumentException("O id informado deve ser maior que zero.");
        }

        Optional<Topico> topicoOptional = topicoRepository.findById(id);
        if (!topicoOptional.isPresent()) {
            throw new EntityNotFoundException("Topico nao encontrado para o id informado.");
        }

        if (topicoRepository.existsByTituloAndMensagemAndIdNot(dados.titulo(), dados.mensagem(), id)) {
            throw new RegraDeNegocioException("Ja existe um topico com o mesmo titulo e mensagem.");
        }

        Usuario autor = usuarioRepository.findById(dados.autorId())
                .orElseThrow(() -> new EntityNotFoundException("Autor nao encontrado para o id informado."));

        Curso curso = cursoRepository.findById(dados.cursoId())
                .orElseThrow(() -> new EntityNotFoundException("Curso nao encontrado para o id informado."));

        Topico topico = topicoOptional.get();
        topico.atualizarConteudo(dados.titulo(), dados.mensagem());
        topico.atualizarContexto(autor, curso);

        return new DadosDetalhamentoTopico(topico);
    }

    @Transactional
    public void excluir(Long id) {
        if (id == null || id < 1) {
            throw new IllegalArgumentException("O id informado deve ser maior que zero.");
        }

        Optional<Topico> topicoOptional = topicoRepository.findById(id);
        if (!topicoOptional.isPresent()) {
            throw new EntityNotFoundException("Topico nao encontrado para o id informado.");
        }

        topicoRepository.deleteById(id);
    }
}
