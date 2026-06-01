-- =============================================================
-- SEED - DADOS INICIAIS - COPA DO MUNDO 2026
-- =============================================================
-- 12 grupos (A a L) com 4 selecoes cada = 48 selecoes
-- 5 fases (Grupos, Oitavas, Quartas, Semifinal, Final)
-- 16 estadios sede da Copa 2026 (EUA, Mexico e Canada)
--
-- IMPORTANTE: cada selecao foi atribuida ao seu grupo correto
-- (o INSERT do Ricardo tinha TODAS no grupo 1, foi corrigido).
-- Confederacoes adicionadas conforme o ajuste do schema.
-- =============================================================

USE copa_mundo;

-- =============================================================
-- 1. GRUPOS (A ate L)
-- =============================================================
INSERT INTO grupos(nome) VALUES
('A'), ('B'), ('C'), ('D'), ('E'), ('F'),
('G'), ('H'), ('I'), ('J'), ('K'), ('L');

-- =============================================================
-- 2. FASES (padronizadas para bater com o enum Fase do Java)
-- =============================================================
INSERT INTO fases(nome) VALUES
('GRUPOS'),
('OITAVAS'),
('QUARTAS'),
('SEMIFINAL'),
('FINAL');

-- =============================================================
-- 3. ESTADIOS (16 sedes da Copa do Mundo 2026)
-- =============================================================
INSERT INTO estadios(nome, cidade) VALUES
('MetLife Stadium', 'Nova Jersey'),
('AT&T Stadium', 'Dallas'),
('SoFi Stadium', 'Los Angeles'),
('Hard Rock Stadium', 'Miami'),
('Mercedes-Benz Stadium', 'Atlanta'),
('NRG Stadium', 'Houston'),
('Lincoln Financial Field', 'Filadelfia'),
('Lumen Field', 'Seattle'),
('Levi''s Stadium', 'Sao Francisco'),
('Arrowhead Stadium', 'Kansas City'),
('Gillette Stadium', 'Boston'),
('Estadio Azteca', 'Cidade do Mexico'),
('Estadio BBVA', 'Monterrey'),
('Estadio Akron', 'Guadalajara'),
('BMO Field', 'Toronto'),
('BC Place', 'Vancouver');

-- =============================================================
-- 4. SELECOES (48 selecoes distribuidas em 12 grupos de 4)
-- =============================================================
-- Cada linha: (nome, sigla, tecnico, bandeira, confederacao, id_grupos)
-- Confederacoes: UEFA, CONMEBOL, CONCACAF, AFC, CAF, OFC

INSERT INTO times(nome, sigla, tecnico, bandeira, confederacao, id_grupos) VALUES

-- Grupo A (id_grupos = 1)
('Mexico', 'MEX', 'Javier Aguirre', '/imagens/mexico.png', 'CONCACAF', 1),
('Africa do Sul', 'RSA', 'Hugo Broos', '/imagens/africa-do-sul.png', 'CAF', 1),
('Coreia do Sul', 'KOR', 'Hong Myungbo', '/imagens/coreia-do-sul.png', 'AFC', 1),
('Tchequia', 'CZE', 'Ivan Hasek', '/imagens/tchequia.png', 'UEFA', 1),

-- Grupo B (id_grupos = 2)
('Canada', 'CAN', 'Jesse Marsch', '/imagens/canada.png', 'CONCACAF', 2),
('Bosnia e Herzegovina', 'BIH', 'Sergej Barbarez', '/imagens/bosnia-e-herz.png', 'UEFA', 2),
('Catar', 'QAT', 'Tintin Marquez', '/imagens/catar.png', 'AFC', 2),
('Suica', 'SUI', 'Murat Yakin', '/imagens/suica.png', 'UEFA', 2),

-- Grupo C (id_grupos = 3)
('Brasil', 'BRA', 'Carlo Ancelotti', '/imagens/brasil.png', 'CONMEBOL', 3),
('Marrocos', 'MAR', 'Mohamed Ouahbi', '/imagens/marrocos.png', 'CAF', 3),
('Haiti', 'HAI', 'Gabriel Calderon', '/imagens/haiti.png', 'CONCACAF', 3),
('Escocia', 'SCO', 'Steve Clarke', '/imagens/escocia.png', 'UEFA', 3),

-- Grupo D (id_grupos = 4)
('Estados Unidos', 'USA', 'Mauricio Pochettino', '/imagens/estados-unidos.png', 'CONCACAF', 4),
('Paraguai', 'PAR', 'Gustavo Alfaro', '/imagens/paraguai.png', 'CONMEBOL', 4),
('Australia', 'AUS', 'Tony Popovic', '/imagens/australia.png', 'AFC', 4),
('Turquia', 'TUR', 'Vincenzo Montella', '/imagens/turquia.png', 'UEFA', 4),

-- Grupo E (id_grupos = 5)
('Alemanha', 'GER', 'Julian Nagelsmann', '/imagens/alemanha.png', 'UEFA', 5),
('Curacao', 'CUW', 'Dick Advocaat', '/imagens/curacao.png', 'CONCACAF', 5),
('Costa do Marfim', 'CIV', 'Emerse Fae', '/imagens/costa-do-marfim.png', 'CAF', 5),
('Equador', 'ECU', 'Sebastian Beccacece', '/imagens/equador.png', 'CONMEBOL', 5),

-- Grupo F (id_grupos = 6)
('Holanda', 'NED', 'Ronald Koeman', '/imagens/holanda.png', 'UEFA', 6),
('Japao', 'JPN', 'Hajime Moriyasu', '/imagens/japao.png', 'AFC', 6),
('Suecia', 'SWE', 'Jon Dahl Tomasson', '/imagens/suecia.png', 'UEFA', 6),
('Tunisia', 'TUN', 'Faouzi Benzarti', '/imagens/tunisia.png', 'CAF', 6),

-- Grupo G (id_grupos = 7)
('Belgica', 'BEL', 'Domenico Tedesco', '/imagens/belgica.png', 'UEFA', 7),
('Egito', 'EGY', 'Hossam Hassan', '/imagens/egito.png', 'CAF', 7),
('Ira', 'IRN', 'Amir Ghalenoei', '/imagens/ira.png', 'AFC', 7),
('Nova Zelandia', 'NZL', 'Darren Bazeley', '/imagens/nova-zelandia.png', 'OFC', 7),

-- Grupo H (id_grupos = 8)
('Espanha', 'ESP', 'Luis de la Fuente', '/imagens/espanha.png', 'UEFA', 8),
('Cabo Verde', 'CPV', 'Bubista', '/imagens/cabo-verde.png', 'CAF', 8),
('Arabia Saudita', 'KSA', 'Herve Renard', '/imagens/arabia-saudita.png', 'AFC', 8),
('Uruguai', 'URU', 'Marcelo Bielsa', '/imagens/uruguai.png', 'CONMEBOL', 8),

-- Grupo I (id_grupos = 9)
('Franca', 'FRA', 'Didier Deschamps', '/imagens/franca.png', 'UEFA', 9),
('Senegal', 'SEN', 'Pape Thiaw', '/imagens/senegal.png', 'CAF', 9),
('Iraque', 'IRQ', 'Jesus Casas', '/imagens/iraque.png', 'AFC', 9),
('Noruega', 'NOR', 'Stale Solbakken', '/imagens/noruega.png', 'UEFA', 9),

-- Grupo J (id_grupos = 10)
('Argentina', 'ARG', 'Lionel Scaloni', '/imagens/argentina.png', 'CONMEBOL', 10),
('Argelia', 'ALG', 'Vladimir Petkovic', '/imagens/argelia.png', 'CAF', 10),
('Austria', 'AUT', 'Ralf Rangnick', '/imagens/austria.png', 'UEFA', 10),
('Jordania', 'JOR', 'Jamal Sellami', '/imagens/jordania.png', 'AFC', 10),

-- Grupo K (id_grupos = 11)
('Portugal', 'POR', 'Roberto Martinez', '/imagens/portugal.png', 'UEFA', 11),
('RD Congo', 'COD', 'Sebastien Desabre', '/imagens/rd-congo.png', 'CAF', 11),
('Uzbequistao', 'UZB', 'Srecko Katanec', '/imagens/uzbequistao.png', 'AFC', 11),
('Colombia', 'COL', 'Nestor Lorenzo', '/imagens/colombia.png', 'CONMEBOL', 11),

-- Grupo L (id_grupos = 12)
('Inglaterra', 'ENG', 'Thomas Tuchel', '/imagens/inglaterra.png', 'UEFA', 12),
('Croacia', 'CRO', 'Zlatko Dalic', '/imagens/croacia.png', 'UEFA', 12),
('Gana', 'GHA', 'Otto Addo', '/imagens/gana.png', 'CAF', 12),
('Panama', 'PAN', 'Thomas Christiansen', '/imagens/panama.png', 'CONCACAF', 12);
