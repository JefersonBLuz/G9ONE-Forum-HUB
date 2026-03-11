CREATE TABLE cursos (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    categoria VARCHAR(120) NOT NULL
);

CREATE TABLE perfis (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(80) NOT NULL UNIQUE
);

CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    email VARCHAR(160) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL
);

CREATE TABLE usuarios_perfis (
    usuario_id BIGINT NOT NULL,
    perfil_id BIGINT NOT NULL,
    PRIMARY KEY (usuario_id, perfil_id),
    CONSTRAINT fk_usuarios_perfis_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuarios (id),
    CONSTRAINT fk_usuarios_perfis_perfil
        FOREIGN KEY (perfil_id) REFERENCES perfis (id)
);

CREATE TABLE topicos (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    mensagem VARCHAR(3000) NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(30) NOT NULL,
    autor_id BIGINT NOT NULL,
    curso_id BIGINT NOT NULL,
    CONSTRAINT uk_topicos_titulo_mensagem UNIQUE (titulo, mensagem),
    CONSTRAINT fk_topicos_autor
        FOREIGN KEY (autor_id) REFERENCES usuarios (id),
    CONSTRAINT fk_topicos_curso
        FOREIGN KEY (curso_id) REFERENCES cursos (id)
);

CREATE TABLE respostas (
    id BIGSERIAL PRIMARY KEY,
    mensagem VARCHAR(3000) NOT NULL,
    topico_id BIGINT NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    autor_id BIGINT NOT NULL,
    solucao BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_respostas_topico
        FOREIGN KEY (topico_id) REFERENCES topicos (id),
    CONSTRAINT fk_respostas_autor
        FOREIGN KEY (autor_id) REFERENCES usuarios (id)
);
