package com.JefersonBLuz.forumhub.controller.curso;

import com.JefersonBLuz.forumhub.dto.curso.DadosAtualizacaoCurso;
import com.JefersonBLuz.forumhub.dto.curso.DadosCadastroCurso;
import com.JefersonBLuz.forumhub.dto.curso.DadosDetalhamentoCurso;
import com.JefersonBLuz.forumhub.service.CursoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/curso")
@Tag(name = "Cursos")
@SecurityRequirement(name = "bearerAuth")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @PostMapping
    public ResponseEntity<DadosDetalhamentoCurso> cadastrar(
            @RequestBody @Valid DadosCadastroCurso dados,
            UriComponentsBuilder uriBuilder
    ) {
        DadosDetalhamentoCurso curso = cursoService.cadastrar(dados);
        URI uri = uriBuilder.path("/curso/{id}")
                .buildAndExpand(curso.id())
                .toUri();
        return ResponseEntity.created(uri).body(curso);
    }

    @GetMapping
    public ResponseEntity<List<DadosDetalhamentoCurso>> listar() {
        return ResponseEntity.ok(cursoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoCurso> detalhar(@PathVariable Long id) {
        return ResponseEntity.ok(cursoService.detalhar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoCurso> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid DadosAtualizacaoCurso dados
    ) {
        return ResponseEntity.ok(cursoService.atualizar(id, dados));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        cursoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
