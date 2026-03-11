ALTER TABLE topicos
    ADD CONSTRAINT ck_topicos_estado
        CHECK (estado IN ('NAO_RESPONDIDO', 'NAO_SOLUCIONADO', 'SOLUCIONADO', 'FECHADO'));

CREATE INDEX idx_topicos_data_criacao ON topicos (data_criacao);
CREATE INDEX idx_topicos_autor_id ON topicos (autor_id);
CREATE INDEX idx_topicos_curso_id ON topicos (curso_id);

CREATE INDEX idx_respostas_topico_id ON respostas (topico_id);
CREATE INDEX idx_respostas_autor_id ON respostas (autor_id);
CREATE INDEX idx_respostas_data_criacao ON respostas (data_criacao);
