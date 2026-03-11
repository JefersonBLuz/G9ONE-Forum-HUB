CREATE TABLE autenticacoes (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL UNIQUE,
    ultimo_login TIMESTAMP,
    tentativas_falhas INTEGER NOT NULL DEFAULT 0,
    CONSTRAINT fk_autenticacoes_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuarios (id)
);
