package com.copa2026.simulacao;

import com.copa2026.model.Partida;
import com.copa2026.model.Selecao;

import java.util.ArrayList;
import java.util.List;

/**
 * Logica da fase eliminatoria da Copa do Mundo 2026.
 *
 * RESPONSAVEL: Rafael
 *
 * A fase eliminatoria comeca com as 32 selecoes classificadas da fase de grupos:
 *   - Oitavas de final: 16 jogos (32 selecoes)
 *   - Quartas de final: 8 jogos
 *   - Semifinal: 4 jogos
 *   - Final: 1 jogo
 *
 * REGRA: em caso de empate apos o tempo regulamentar, ha decisao por penaltis.
 * Voce DEVE setar partida.setDecididoNosPenaltis(true) e preencher
 * penaltisCasa/penaltisVisitante.
 *
 * USAR: SimuladorPartida.simular(casa, visitante, Fase.OITAVAS) etc.
 *
 * Chaveamento (modelo FIFA padrao):
 *   - 1o do Grupo A vs 2o do Grupo B
 *   - 1o do Grupo C vs 2o do Grupo D
 *   ... e assim por diante.
 */
public class FaseEliminatoria {

    private final SimuladorPartida simulador;

    public FaseEliminatoria(SimuladorPartida simulador) {
        this.simulador = simulador;
    }

    /**
     * Simula toda a fase eliminatoria a partir dos classificados.
     *
     * @param classificados lista das 32 selecoes classificadas (em ordem do chaveamento)
     * @return lista de todas as partidas das eliminatorias (oitavas ate final)
     */
    public List<Partida> simularEliminatorias(List<Selecao> classificados) {
        // TODO: Rafael implementa.
        // Simular oitavas -> quartas -> semifinal -> final.
        // Em cada partida, se for empate, simular penaltis.
        // Retornar todas as partidas em ordem.
        return new ArrayList<>();
    }

    /**
     * Retorna o campeao do torneio (vencedor da final).
     *
     * @param partidasEliminatorias todas as partidas das eliminatorias
     * @return selecao campea
     */
    public Selecao getCampeao(List<Partida> partidasEliminatorias) {
        // TODO: Rafael implementa.
        // Pegar a ultima partida da lista (que e a final) e retornar o vencedor.
        return null;
    }
}
