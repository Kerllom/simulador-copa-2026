package com.copa2026.model;

/**
 * Representa as fases do torneio da Copa do Mundo 2026.
 *
 * GRUPOS: 12 grupos de 4 selecoes (6 jogos por grupo, 72 jogos no total).
 * OITAVAS: 32 selecoes classificadas (16 jogos).
 * QUARTAS: 16 selecoes (8 jogos).
 * SEMIFINAL: 8 selecoes (4 jogos).
 * QUARTA_FINAL: disputa de 3o lugar (1 jogo).
 * FINAL: 2 selecoes (1 jogo).
 */
public enum Fase {

    GRUPOS("Fase de Grupos"),
    OITAVAS("Oitavas de Final"),
    QUARTAS("Quartas de Final"),
    SEMIFINAL("Semifinal"),
    FINAL("Final");

    private final String descricao;

    Fase(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    /**
     * Indica se a fase permite empate.
     * Na fase de grupos empate vale 1 ponto, nas demais ha decisao por penaltis.
     */
    public boolean permiteEmpate() {
        return this == GRUPOS;
    }
}
