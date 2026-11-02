INSERT INTO t_m_categoria (nm_categoria, ds_categoria) VALUES
('Fisica Nuclear', 'Fundamentos do nucleo atomico'),
('Radiacao', 'Tipos e propriedades da radiacao'),
('Energia Nuclear', 'Geracao de energia'),
('Medicina Nuclear', 'Aplicacoes medicas'),
('Seguranca Nuclear', 'Protecao e seguranca');

INSERT INTO t_m_usuario (nm_usuario, nm_email, dt_cadastro) VALUES
('Ana Silva', 'ana@email.com', CURRENT_DATE),
('Bruno Costa', 'bruno@email.com', CURRENT_DATE),
('Carla Souza', 'carla@email.com', CURRENT_DATE),
('Diego Lima', 'diego@email.com', CURRENT_DATE),
('Eva Santos', 'eva@email.com', CURRENT_DATE);

INSERT INTO t_m_pergunta (ds_enunciado, t_m_categoria_id_categoria) VALUES
('Qual particula possui carga eletrica positiva no nucleo atomico?', 1),
('Qual tipo de radiacao possui maior poder de penetracao entre alfa, beta e gama?', 2),
('Qual processo e utilizado em usinas nucleares para liberar energia?', 3),
('Qual exame utiliza materiais radioativos para produzir imagens do organismo?', 4),
('Qual material e utilizado para proteger contra determinados tipos de radiacao?', 5);

INSERT INTO t_m_alternativa (ds_texto, ds_correta, t_m_pergunta_id_pergunta, t_m_pergunta_t_m_categoria_id_categoria) VALUES
('Proton', 'S', 1, 1), ('Eletron', 'N', 1, 1),
('Radiacao gama', 'S', 2, 2), ('Radiacao alfa', 'N', 2, 2),
('Fissao nuclear', 'S', 3, 3), ('Combustao de madeira', 'N', 3, 3),
('Cintilografia', 'S', 4, 4), ('Radiografia convencional', 'N', 4, 4),
('Chumbo', 'S', 5, 5), ('Papel comum', 'N', 5, 5);

INSERT INTO t_m_resposta_usuario (dt_resposta, t_m_usuario_id_usuario, t_m_alternativa_id_alternativa,
    t_m_alternativa_t_m_pergunta_id_pergunta, t_m_alternativa_t_m_pergunta_t_m_categoria_id_categoria,
    t_m_categoria_id_categoria) VALUES
(CURRENT_TIMESTAMP, 1, 1, 1, 1, 1),
(CURRENT_TIMESTAMP, 2, 4, 2, 2, 2),
(CURRENT_TIMESTAMP, 3, 5, 3, 3, 3),
(CURRENT_TIMESTAMP, 4, 8, 4, 4, 4),
(CURRENT_TIMESTAMP, 5, 9, 5, 5, 5);