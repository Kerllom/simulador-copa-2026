-- =============================================================
-- SCHEMA DO BANCO DE DADOS - COPA DO MUNDO 2026
-- =============================================================
-- Base do Ricardo, com ajustes do Tech Lead:
--   1. Adicionada coluna `confederacao` na tabela `times`
--      (necessario para o motor de simulacao)
--   2. Adicionadas colunas `decidido_nos_penaltis`,
--      `penaltis_time1`, `penaltis_time2` na tabela `jogos`
--      (necessarias para armazenar resultados de penaltis)
--   3. Removida tabela `classificacao` (calculada em memoria)
--   4. Padronizadas as fases para bater com o enum Java
-- =============================================================

-- 1. Limpa o banco anterior para evitar qualquer conflito
DROP DATABASE IF EXISTS copa_mundo;
CREATE DATABASE copa_mundo CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE copa_mundo;

-- 2. Criacao das Tabelas

-- Tabela de grupos (A, B, C, ..., L)
CREATE TABLE grupos(
    id_grupos INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(40) NOT NULL
);

-- Tabela de selecoes (times participantes)
CREATE TABLE times(
    id_times INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    sigla CHAR(3) NOT NULL UNIQUE,
    tecnico VARCHAR(75) NOT NULL,
    bandeira VARCHAR(60) NOT NULL,
    confederacao VARCHAR(20) NOT NULL,
    id_grupos INT,
    FOREIGN KEY (id_grupos) REFERENCES grupos(id_grupos)
);

-- Tabela de fases do torneio
CREATE TABLE fases(
    id_fases INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(80) NOT NULL
);

-- Tabela de estadios
CREATE TABLE estadios(
    id_estadios INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    cidade VARCHAR(180) NOT NULL
);

-- Tabela de jogos (partidas)
CREATE TABLE jogos(
    id_jogos INT AUTO_INCREMENT PRIMARY KEY,
    data_hora DATETIME NOT NULL,
    id_estadios INT,
    id_fases INT,
    time1_id INT NOT NULL,
    time2_id INT NOT NULL,
    gols_time1 INT DEFAULT 0,
    gols_time2 INT DEFAULT 0,
    vencedor_id INT NULL,
    decidido_nos_penaltis BOOLEAN DEFAULT FALSE,
    penaltis_time1 INT DEFAULT 0,
    penaltis_time2 INT DEFAULT 0,
    FOREIGN KEY (vencedor_id) REFERENCES times(id_times),
    FOREIGN KEY (id_estadios) REFERENCES estadios(id_estadios),
    FOREIGN KEY (id_fases) REFERENCES fases(id_fases),
    FOREIGN KEY (time1_id) REFERENCES times(id_times),
    FOREIGN KEY (time2_id) REFERENCES times(id_times)
);

-- Tabela de gols marcados
CREATE TABLE gols(
    id_gols INT AUTO_INCREMENT PRIMARY KEY,
    id_jogos INT NOT NULL,
    id_times INT NOT NULL,
    autor VARCHAR(70) NOT NULL,
    tempo_gol INT NOT NULL,
    FOREIGN KEY (id_jogos) REFERENCES jogos(id_jogos),
    FOREIGN KEY (id_times) REFERENCES times(id_times)
);

-- 3. Indices para garantir consultas em ate 200ms
CREATE INDEX idx_grupo ON times(id_grupos);
CREATE INDEX idx_confederacao ON times(confederacao);
CREATE INDEX idx_fase ON jogos(id_fases);
CREATE INDEX idx_partida_gols ON gols(id_jogos);
CREATE INDEX idx_selecao_time1 ON jogos(time1_id);
CREATE INDEX idx_selecao_time2 ON jogos(time2_id);
