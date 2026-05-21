package com.copa2026.simulacao;

import com.copa2026.model.Grupo;
import com.copa2026.model.Partida;
import com.copa2026.model.Selecao;

import java.util.ArrayList;
import java.util.List;

/**
 * Logica da fase de grupos da Copa do Mundo 2026.
 *
 * RESPONSAVEL: Mateus
 *
 * A fase de grupos tem 12 grupos de 4 selecoes (Grupos A a L).
 * Cada selecao joga contra todas as outras do seu grupo (6 jogos por grupo).
 * Total: 72 jogos.
 *
 * Criterios de classificacao (em ordem):
 *   1. Pontos (V=3, E=1, D=0)
 *   2. Saldo de gols
 *   3. Gols pro
 *   4. Confronto direto
 *
 * Classificam-se os 2 primeiros de cada grupo (32 selecoes para as oitavas).
 *
 * USAR: SimuladorPartida.simular(casa, visitante, Fase.GRUPOS)
 */
public class FaseGrupos {

    private final SimuladorPartida simulador;

    public FaseGrupos(SimuladorPartida simulador) {
        this.simulador = simulador;
    }

    /**
     * Simula todos os jogos da fase de grupos.
     *
     * @param grupos lista dos 12 grupos com suas 4 selecoes cada
     * @return lista de todas as partidas simuladas (72 partidas)
     */
    public List<Partida> simularFaseDeGrupos(List<Grupo> grupos) {
        // TODO: Mateus implementa.
        // Para cada grupo:
        //   - Gerar os 6 jogos (cada selecao contra todas as outras)
        //   - Chamar simulador.simular(casa, visitante, Fase.GRUPOS)
        //   - Adicionar partida na lista de retorno
        return new ArrayList<>();
    }

    /**
     * Retorna os 2 primeiros classificados de um grupo, aplicando os criterios FIFA.
     *
     * @param grupo grupo a ser processado
     * @param partidas todas as partidas daquele grupo
     * @return lista com 2 selecoes (1o e 2o colocados)
     */
    public List<Selecao> getClassificados(Grupo grupo, List<Partida> partidas) {
        // TODO: Mateus implementa.
        // Calcular pontuacao de cada selecao do grupo.
        // Ordenar por: pontos -> saldo de gols -> gols pro -> confronto direto.
        // Retornar os 2 primeiros.
        return new ArrayList<>();
    }
}
