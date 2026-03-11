INSERT INTO usuarios (nome, email, senha)
VALUES ('Admin ForumHub', 'admin@forumhub.local', '$2b$12$WwdWJYTno9R9lqsS35ma9uZ2R39yE8Mfd5Ail80ZiSgfZgM8CgAYu')
ON CONFLICT (email) DO NOTHING;

INSERT INTO usuarios_perfis (usuario_id, perfil_id)
SELECT u.id, p.id
FROM usuarios u
         JOIN perfis p ON p.nome = 'ROLE_ADMIN'
WHERE u.email = 'admin@forumhub.local'
ON CONFLICT DO NOTHING;

INSERT INTO autenticacoes (usuario_id, ultimo_login, tentativas_falhas)
SELECT u.id, NULL, 0
FROM usuarios u
WHERE u.email = 'admin@forumhub.local'
ON CONFLICT (usuario_id) DO NOTHING;
