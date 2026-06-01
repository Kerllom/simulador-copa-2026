package com.copa2026.simulacao;

import com.copa2026.model.Fase;
import com.copa2026.model.Partida;
import com.copa2026.model.Selecao;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Motor de simulacao de partidas individuais.
 * Recebe duas selecoes e uma fase, e devolve uma Partida com o resultado.
 *
 * RESPONSAVEL: Kerllom
 *
 * ALGORITMO:
 * Cada confederacao tem um "fator de forca" baseado no historico em Copas
 * do Mundo. UEFA e CONMEBOL dominam (12 e 10 titulos respectivamente).
 * A simulacao usa esse fator + componente aleatorio + bonus de mando de campo
 * para gerar placares realistas.
 *
 * Na fase de grupos, empate e permitido. Nas eliminatorias, em caso de empate,
 * ha decisao por penaltis (aleatoria).
 */
public class SimuladorPartida {

    // Fator de forca por confederacao baseado em desempenho historico em Copas.
    private static final Map<String, Double> FATOR_CONFEDERACAO = new HashMap<>();

    static {
        FATOR_CONFEDERACAO.put("UEFA", 1.20);       // Europa - 12 titulos mundiais
        FATOR_CONFEDERACAO.put("CONMEBOL", 1.20);   // America do Sul - 10 titulos
        FATOR_CONFEDERACAO.put("CONCACAF", 0.85);   // America do Norte/Central
        FATOR_CONFEDERACAO.put("AFC", 0.80);        // Asia
        FATOR_CONFEDERACAO.put("CAF", 0.80);        // Africa
        FATOR_CONFEDERACAO.put("OFC", 0.55);        // Oceania
    }

    // Bonus de mando de campo (simula vantagem psicologica/torcida).
    private static final double BONUS_MANDO = 0.20;

    // Maximo de gols possiveis em uma partida (por time).
    private static final int MAX_GOLS = 6;

    private final Random random;

    public SimuladorPartida() {
        this.random = new Random();
    }

    /**
     * Construtor para testes deterministicos (com seed fixa).
     */
    public SimuladorPartida(long seed) {
        this.random = new Random(seed);
    }

    /**
     * Simula uma partida entre duas selecoes.
     */
    public Partida simular(Selecao casa, Selecao visitante, Fase fase) {
        Partida partida = new Partida(casa, visitante, fase);

        // Calcula gols esperados para cada lado
        int golsCasa = gerarGols(casa, true);
        int golsVisitante = gerarGols(visitante, false);

        partida.setGolsCasa(golsCasa);
        partida.setGolsVisitante(golsVisitante);

        // Em fases eliminatorias, se empatar, decide nos penaltis
        if (!fase.permiteEmpate() && golsCasa == golsVisitante) {
            simularPenaltis(partida);
        }

        return partida;
    }

    /**
     * Gera um numero aleatorio de gols ponderado pela forca da confederacao.
     */
    private int gerarGols(Selecao selecao, boolean ehMandante) {
        double fator = FATOR_CONFEDERACAO.getOrDefault(selecao.getConfederacao(), 1.0);

        if (ehMandante) {
            fator += BONUS_MANDO;
        }

        double base = random.nextDouble() * 3.0;
        double golsEsperados = base * fator;

        double variacao = (random.nextDouble() - 0.5) * 1.5;
        int golsFinais = (int) Math.round(golsEsperados + variacao);

        return Math.max(0, Math.min(golsFinais, MAX_GOLS));
    }

    /**
     * Simula a disputa de penaltis em caso de empate na fase eliminatoria.
     */
    private void simularPenaltis(Partida partida) {
        partida.setDecididoNosPenaltis(true);

        int penCasa = 0;
        int penVisitante = 0;

        // 5 cobrancas iniciais para cada
        for (int i = 0; i < 5; i++) {
            if (random.nextDouble() < 0.75) penCasa++;
            if (random.nextDouble() < 0.75) penVisitante++;
        }

        // Morte subita se empatou
        while (penCasa == penVisitante) {
            boolean casaConverteu = random.nextDouble() < 0.75;
            boolean visitanteConverteu = random.nextDouble() < 0.75;

            if (casaConverteu) penCasa++;
            if (visitanteConverteu) penVisitante++;
        }

        partida.setPenaltisCasa(penCasa);
        partida.setPenaltisVisitante(penVisitante);
    }
}