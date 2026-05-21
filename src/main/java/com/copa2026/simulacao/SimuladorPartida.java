package com.copa2026.simulacao;

import com.copa2026.model.Fase;
import com.copa2026.model.Partida;
import com.copa2026.model.Selecao;

/**
 * Motor de simulacao de partidas individuais.
 * Recebe duas selecoes e uma fase, e devolve uma Partida com o resultado.
 *
 * RESPONSAVEL: Kerllom
 *
 * Esta classe e o "tijolinho" que Mateus (FaseGrupos) e Rafael (FaseEliminatoria)
 * vao usar para simular cada jogo individual.
 *
 * ASSINATURA DEFINIDA (NAO MUDAR):
 *   public Partida simular(Selecao casa, Selecao visitante, Fase fase)
 *
 * Algoritmo: aleatorio puro (0 a 4 gols para cada lado, com distribuicao realista).
 * Na fase de grupos, empate e permitido. Nas eliminatorias, em caso de empate,
 * ha decisao por penaltis (aleatoria).
 */
public class SimuladorPartida {

    /**
     * Simula uma partida entre duas selecoes.
     *
     * @param casa selecao mandante
     * @param visitante selecao visitante
     * @param fase fase do torneio (afeta o tratamento de empates)
     * @return Partida com o resultado simulado
     */
    public Partida simular(Selecao casa, Selecao visitante, Fase fase) {
        // TODO: Kerllom implementa amanha de manha.
        // Por enquanto retorna uma partida placeholder para nao quebrar a compilacao.
        Partida partida = new Partida(casa, visitante, fase);
        partida.setGolsCasa(0);
        partida.setGolsVisitante(0);
        return partida;
    }
}
