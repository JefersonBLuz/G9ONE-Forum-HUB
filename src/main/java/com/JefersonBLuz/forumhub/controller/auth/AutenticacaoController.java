package com.JefersonBLuz.forumhub.controller.auth;

import com.JefersonBLuz.forumhub.dto.auth.DadosAutenticacao;
import com.JefersonBLuz.forumhub.dto.auth.DadosTokenJWT;
import com.JefersonBLuz.forumhub.infra.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Tag(name = "Autenticacao")
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AutenticacaoController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    @Operation(summary = "Realiza login e retorna token JWT")
    public DadosTokenJWT efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());

        Authentication authentication = authenticationManager.authenticate(token);
        UserDetails user = (UserDetails) authentication.getPrincipal();
        return new DadosTokenJWT(tokenService.gerarToken(user));
    }
}
