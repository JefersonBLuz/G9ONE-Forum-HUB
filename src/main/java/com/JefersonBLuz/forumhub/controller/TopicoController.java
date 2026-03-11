package com.JefersonBLuz.forumhub.controller;

import com.JefersonBLuz.forumhub.dto.topico.DadosAtualizacaoTopico;
import com.JefersonBLuz.forumhub.dto.topico.DadosCadastroTopico;
import com.JefersonBLuz.forumhub.dto.topico.DadosDetalhamentoTopico;
import com.JefersonBLuz.forumhub.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoService topicoService;

    public TopicoController(TopicoService topicoService) {
        this.topicoService = topicoService;
    }

    @PostMapping
    public ResponseEntity<DadosDetalhamentoTopico> cadastrar(
            @RequestBody @Valid DadosCadastroTopico dados,
            UriComponentsBuilder uriBuilder
    ) {
        DadosDetalhamentoTopico topicoCadastrado = topicoService.cadastrar(dados);
        URI uri = uriBuilder.path("/topicos/{id}")
                .buildAndExpand(topicoCadastrado.id())
                .toUri();
        return ResponseEntity.created(uri).body(topicoCadastrado);
    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoTopico>> listar(
            @RequestParam(required = false) String nomeCurso,
            @RequestParam(required = false) Integer ano,
            @PageableDefault(size = 10, sort = "dataCriacao", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<DadosDetalhamentoTopico> pagina = topicoService.listar(nomeCurso, ano, pageable);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoTopico> detalhar(@PathVariable Long id) {
        DadosDetalhamentoTopico topico = topicoService.detalhar(id);
        return ResponseEntity.ok(topico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoTopico> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid DadosAtualizacaoTopico dados
    ) {
        DadosDetalhamentoTopico topicoAtualizado = topicoService.atualizar(id, dados);
        return ResponseEntity.ok(topicoAtualizado);
    }
}
