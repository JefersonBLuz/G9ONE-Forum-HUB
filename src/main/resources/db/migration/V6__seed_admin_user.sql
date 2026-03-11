INSERT INTO usuarios (nome, email, senha)
VALUES ('Administrador', 'admin@admin.com', '$2b$12$z4j6h8t2gOX6b3bdZXV2nu5Fepk..PFZZlxTkrwoAmjoMycgX3fvK')
ON CONFLICT (email) DO NOTHING;

INSERT INTO usuarios_perfis (usuario_id, perfil_id)
SELECT u.id, p.id
FROM usuarios u
         JOIN perfis p ON p.nome = 'ROLE_ADMIN'
WHERE u.email = 'admin@admin.com'
ON CONFLICT DO NOTHING;

INSERT INTO autenticacoes (usuario_id, ultimo_login, tentativas_falhas)
SELECT u.id, NULL, 0
FROM usuarios u
WHERE u.email = 'admin@admin.com'
ON CONFLICT (usuario_id) DO NOTHING;
