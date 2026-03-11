package com.JefersonBLuz.forumhub.controller.resposta;

import com.JefersonBLuz.forumhub.dto.resposta.DadosAtualizacaoResposta;
import com.JefersonBLuz.forumhub.dto.resposta.DadosCadastroResposta;
import com.JefersonBLuz.forumhub.dto.resposta.DadosDetalhamentoResposta;
import com.JefersonBLuz.forumhub.service.RespostaService;
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
@RequestMapping("/respostas")
@Tag(name = "Respostas")
@SecurityRequirement(name = "bearerAuth")
public class RespostaController {

    private final RespostaService respostaService;

    public RespostaController(RespostaService respostaService) {
        this.respostaService = respostaService;
    }

    @PostMapping
    public ResponseEntity<DadosDetalhamentoResposta> cadastrar(
            @RequestBody @Valid DadosCadastroResposta dados,
            UriComponentsBuilder uriBuilder
    ) {
        DadosDetalhamentoResposta resposta = respostaService.cadastrar(dados);
        URI uri = uriBuilder.path("/respostas/{id}")
                .buildAndExpand(resposta.id())
                .toUri();
        return ResponseEntity.created(uri).body(resposta);
    }

    @GetMapping
    public ResponseEntity<List<DadosDetalhamentoResposta>> listar() {
        return ResponseEntity.ok(respostaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoResposta> detalhar(@PathVariable Long id) {
        return ResponseEntity.ok(respostaService.detalhar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoResposta> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid DadosAtualizacaoResposta dados
    ) {
        return ResponseEntity.ok(respostaService.atualizar(id, dados));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        respostaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
