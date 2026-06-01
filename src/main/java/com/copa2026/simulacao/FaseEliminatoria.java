package com.copa2026.simulacao;

import com.copa2026.model.Fase;
import com.copa2026.model.Partida;
import com.copa2026.model.Selecao;

import java.util.ArrayList;
import java.util.List;

/**
 * Logica da fase eliminatoria da Copa do Mundo 2026.
 *
 * RESPONSAVEL: Rafael
 *
 * A fase eliminatoria comeca com 16 selecoes classificadas:
 *   - Oitavas de final: 8 jogos (16 selecoes)
 *   - Quartas de final: 4 jogos
 *   - Semifinal: 2 jogos
 *   - Final: 1 jogo
 * Total: 15 partidas eliminatorias.
 *
 * REGRA: em caso de empate apos o tempo regulamentar, ha decisao por penaltis
 * (tratada automaticamente pelo SimuladorPartida quando a fase nao permite empate).
 *
 * Chaveamento: 1o vs 2o, 3o vs 4o, etc. da lista de classificados.
 */
public class FaseEliminatoria {

    private final SimuladorPartida simulador;

    public FaseEliminatoria(SimuladorPartida simulador) {
        this.simulador = simulador;
    }

    /**
     * Simula toda a fase eliminatoria a partir dos classificados.
     *
     * @param classificados lista das 16 selecoes classificadas (em ordem do chaveamento)
     * @return lista de todas as partidas das eliminatorias (oitavas ate final)
     */
    public List<Partida> simularEliminatorias(List<Selecao> classificados) {
        List<Partida> todasPartidas = new ArrayList<>();
        List<Selecao> vencedores = new ArrayList<>();

        // Oitavas de final - 16 times, 8 jogos
        for (int i = 0; i < classificados.size(); i += 2) {
            Selecao time1 = classificados.get(i);
            Selecao time2 = classificados.get(i + 1);
            Partida partida = simulador.simular(time1, time2, Fase.OITAVAS);
            todasPartidas.add(partida);
            vencedores.add(partida.getVencedor());
        }

        // Quartas de final - 8 times, 4 jogos
        List<Selecao> vencedoresQuartas = new ArrayList<>();
        for (int i = 0; i < vencedores.size(); i += 2) {
            Selecao time1 = vencedores.get(i);
            Selecao time2 = vencedores.get(i + 1);
            Partida partida = simulador.simular(time1, time2, Fase.QUARTAS);
            todasPartidas.add(partida);
            vencedoresQuartas.add(partida.getVencedor());
        }

        // Semifinal - 4 times, 2 jogos
        List<Selecao> vencedoresSemis = new ArrayList<>();
        for (int i = 0; i < vencedoresQuartas.size(); i += 2) {
            Selecao time1 = vencedoresQuartas.get(i);
            Selecao time2 = vencedoresQuartas.get(i + 1);
            Partida partida = simulador.simular(time1, time2, Fase.SEMIFINAL);
            todasPartidas.add(partida);
            vencedoresSemis.add(partida.getVencedor());
        }

        // Final - 2 times, 1 jogo
        Selecao finalista1 = vencedoresSemis.get(0);
        Selecao finalista2 = vencedoresSemis.get(1);
        Partida partidaFinal = simulador.simular(finalista1, finalista2, Fase.FINAL);
        todasPartidas.add(partidaFinal);

        return todasPartidas;
    }

    /**
     * Retorna o campeao do torneio (vencedor da final).
     *
     * @param partidasEliminatorias todas as partidas das eliminatorias
     * @return selecao campea
     */
    public Selecao getCampeao(List<Partida> partidasEliminatorias) {
        // A ultima partida da lista e a final
        Partida partidaFinal = partidasEliminatorias.get(partidasEliminatorias.size() - 1);
        return partidaFinal.getVencedor();
    }
}