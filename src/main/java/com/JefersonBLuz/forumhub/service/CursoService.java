package com.JefersonBLuz.forumhub.service;

import com.JefersonBLuz.forumhub.domain.model.Curso;
import com.JefersonBLuz.forumhub.domain.repository.CursoRepository;
import com.JefersonBLuz.forumhub.dto.curso.DadosAtualizacaoCurso;
import com.JefersonBLuz.forumhub.dto.curso.DadosCadastroCurso;
import com.JefersonBLuz.forumhub.dto.curso.DadosDetalhamentoCurso;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    @Transactional
    public DadosDetalhamentoCurso cadastrar(DadosCadastroCurso dados) {
        Curso curso = new Curso(dados.nome(), dados.categoria());
        Curso cursoSalvo = cursoRepository.save(curso);
        return new DadosDetalhamentoCurso(cursoSalvo);
    }

    @Transactional(readOnly = true)
    public List<DadosDetalhamentoCurso> listar() {
        return cursoRepository.findAll().stream()
                .map(DadosDetalhamentoCurso::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public DadosDetalhamentoCurso detalhar(Long id) {
        if (id == null || id < 1) {
            throw new IllegalArgumentException("O id informado deve ser maior que zero.");
        }
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Curso nao encontrado para o id informado."));
        return new DadosDetalhamentoCurso(curso);
    }

    @Transactional
    public DadosDetalhamentoCurso atualizar(Long id, DadosAtualizacaoCurso dados) {
        if (id == null || id < 1) {
            throw new IllegalArgumentException("O id informado deve ser maior que zero.");
        }

        Optional<Curso> cursoOptional = cursoRepository.findById(id);
        if (!cursoOptional.isPresent()) {
            throw new EntityNotFoundException("Curso nao encontrado para o id informado.");
        }

        Curso curso = cursoOptional.get();
        curso.atualizarDados(dados.nome(), dados.categoria());
        return new DadosDetalhamentoCurso(curso);
    }

    @Transactional
    public void excluir(Long id) {
        if (id == null || id < 1) {
            throw new IllegalArgumentException("O id informado deve ser maior que zero.");
        }

        Optional<Curso> cursoOptional = cursoRepository.findById(id);
        if (!cursoOptional.isPresent()) {
            throw new EntityNotFoundException("Curso nao encontrado para o id informado.");
        }

        cursoRepository.deleteById(id);
    }
}
