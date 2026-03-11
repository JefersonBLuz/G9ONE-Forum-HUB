package com.JefersonBLuz.forumhub.controller;

import com.JefersonBLuz.forumhub.dto.topico.DadosCadastroTopico;
import com.JefersonBLuz.forumhub.dto.topico.DadosDetalhamentoTopico;
import com.JefersonBLuz.forumhub.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
}
