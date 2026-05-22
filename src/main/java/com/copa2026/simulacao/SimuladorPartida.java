package com.copa2026.simulacao;

import com.copa2026.model.Fase;
import com.copa2026.model.Partida;
import com.copa2026.model.Selecao;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Motor de simulação de partidas individuais.
 * Recebe duas seleções e uma fase, e devolve uma Partida com o resultado.
 *
 * Algoritmo:
 * Na fase de grupos, empate é perimitido. Nas eliminatórias, em caso de empate,
 * há decisão por penaltis (aleatória).
 */
public class SimuladorPartida {

    // Fator de força por confederação baseado em desempenho histórico em Copas.
    private static final Map<String, Double> FATOR_CONFEDERACAO = new HashMap<>();

    static {
        FATOR_CONFEDERACAO.put("UEFA", 1.20);       // Europa
        FATOR_CONFEDERACAO.put("CONMEBOL", 1.20);   // América do Sul
        FATOR_CONFEDERACAO.put("CONCAF", 0.85);     // América do Norte/Central
        FATOR_CONFEDERACAO.put("AFC", 0.80);        // Ásia
        FATOR_CONFEDERACAO.put("CAF", 0.80);        // África
        FATOR_CONFEDERACAO.put("OFC", 0.55);        // Oceânia
    }

    // Bônus de mnado de campo (simula vantagem psicológica/torcida).
    private static final double BONUS_MANDO = 0.20;

    //Máximo de gols possíveis em uma partida (por time).
    private static final int MAX_GOLS = 6;

    private final Random random;

    public SimuladorPartida() {
        this.random = new Random();
    }

    /**
     * Construtor para testes (com seed fixa).
     * Em produção, usar o construtor sem argumentos.
     */
    public SimuladorPartida(long seed) {
        this.random = new Random(seed);
    }

    /**
     * Simula uma partida entre duas seleções.
     *
     * @param casa seleção mandante
     * @param visitante selecao visitante
     * @param fase fase do torneio (afeta o tratamento de empates)
     * @return Partida com o resultado simulado
     */
    public Partida simular(Selecao casa, Selecao visitante, Fase fase) {
        Partida partida = new Partida(casa, visitante, fase);

        // Calcula gols esperados para cada lado, com base na confederação
        int golsCasa = gerarGols(casa, true);
        int golsVisitante = gerarGols(visitante, false);

        partida.setGolsCasa(golsCasa);
        partida.setGolsVisitante(golsVisitante);

        // Em fases eliminatórias, se empatar, decide nos penaltis
        if (!fase.permiteEmpate() && golsCasa == golsVisitante) {
            simularPenaltis(partida);
        }

        return partida;
    }

    /**
     * Gera um número aleatório de gols para uma seleção, ponderado pela
     * força da confederação e (se aplicável) pelo bônus de mando de campo.
     */
    private int gerarGols(Selecao selecao, boolean ehMandante) {
        double fator = FATOR_CONFEDERACAO.getOrDefault(selecao.getConfederacao(), 1.0);

        // Bônus para o time da casa (simula vantagem psicológica)
        if (ehMandante) {
            fator += BONUS_MANDO;
        }

        // Gera um valor base aleatório entre 0 e 3, depois aplica o fator
        double base = random.nextDouble() * 3.0;
        double golsEsperados = base * fator;

        // Adiciona uma pequena variação para imprevisibilidade
        double variacao = (random.nextDouble() - 0.5) * 1.5;
        int golsFinais = (int) Math.round(golsEsperados + variacao);

        // Garante que está dentro dos limites válidos
        return Math.max(0, Math.min(golsFinais, MAX_GOLS));
    }

    /**
     * Simula a disputa de penaltis em caso de empate na fase eliminatoria.
     * Cada selecao bate 5 penaltis. Cada cobranca tem 75% de chance de gol.
     * Se ainda empatar, faz cobrancas alternadas ate decidir (morte subita).
     */
    private void simularPenaltis(Partida partida) {
        partida.setDecididoNosPenaltis(true);

        int penCasa = 0;
        int penVisitante = 0;

        // 5 cobranças iniciais para cada
        for (int i = 0; i < 5; i++) {
            if (random.nextDouble() < 0.75) penCasa++;
            if (random.nextDouble() < 0.75) penVisitante++;
        }

        // Se empatou nas 5 cobranças, vai pra morte súbita
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