package com.JefersonBLuz.forumhub.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "respostas")
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 3000)
    @Column(nullable = false, length = 3000)
    private String mensagem;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "topico_id", nullable = false)
    private Topico topico;

    @NotNull
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;

    @Column(nullable = false)
    private Boolean solucao;

    protected Resposta() {
    }

    public Resposta(String mensagem, Topico topico, Usuario autor) {
        this.mensagem = mensagem;
        this.topico = topico;
        this.autor = autor;
        this.solucao = false;
    }

    @PrePersist
    public void prePersist() {
        if (dataCriacao == null) {
            dataCriacao = LocalDateTime.now();
        }
        if (solucao == null) {
            solucao = false;
        }
    }

    public void marcarComoSolucao() {
        this.solucao = true;
    }

    public Long getId() {
        return id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public Topico getTopico() {
        return topico;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public Usuario getAutor() {
        return autor;
    }

    public Boolean getSolucao() {
        return solucao;
    }
}
