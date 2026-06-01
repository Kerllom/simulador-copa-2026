package com.copa2026.simulacao;

import com.copa2026.model.Grupo;
import com.copa2026.model.Partida;
import com.copa2026.model.Selecao;
import com.copa2026.model.ClassificacaoSelecao;
import com.copa2026.model.Fase;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class FaseGrupos {

    private final SimuladorPartida simulador;

    public FaseGrupos(SimuladorPartida simulador) {
        this.simulador = simulador;
    }

    /**
     * Simula todos os jogos da fase de grupos.
     * Gera 6 jogos por grupo (cada selecao contra todas as outras).
     * Total: 72 jogos (12 grupos x 6).
     *
     * @param grupos lista dos 12 grupos com suas 4 selecoes cada
     * @return lista de todas as partidas simuladas (72 partidas)
     */

    public List<Partida> simularFaseDeGrupos(List<Grupo> grupos) {
        List<Partida> todasAsPartidas = new ArrayList<>();

        for (Grupo grupo : grupos) {
            List<Selecao> selecoes = grupo.getSelecoes();

            for (int i = 0; i < selecoes.size(); i++) {
                for (int j = i + 1; j < selecoes.size(); j++) {
                    Selecao casa = selecoes.get(i);
                    Selecao visitante = selecoes.get(j);

                    Partida partida = simulador.simular(casa, visitante, Fase.GRUPOS);
                    todasAsPartidas.add(partida);
                }
            }
        }

        return todasAsPartidas;
    }


    /**
     * Retorna os 2 primeiros classificados de um grupo.
     * Criterios FIFA: pontos -> saldo de gols -> gols pro.
     *
     * @param grupo    grupo a ser processado
     * @param partidas partidas ja simuladas daquele grupo
     * @return lista com 2 selecoes classificadas (1o e 2o lugar)
     */

    public List<Selecao> getClassificados(Grupo grupo, List<Partida> partidas) {

        // PARTE 1 - montar tabela
        List<ClassificacaoSelecao> tabela = new ArrayList<>();
        for (Selecao s : grupo.getSelecoes()) {
            tabela.add(new ClassificacaoSelecao(s));
        }

        // PARTE 2 - processar partidas
        for (Partida partida : partidas) {
            ClassificacaoSelecao fichaCasa = null;
            ClassificacaoSelecao fichaVisitante = null;

            for (ClassificacaoSelecao ficha : tabela) {
                if (ficha.getSelecao().getId() == partida.getCasa().getId()) {
                    fichaCasa = ficha;
                }
                if (ficha.getSelecao().getId() == partida.getVisitante().getId()) {
                    fichaVisitante = ficha;
                }
            }

            fichaCasa.setGolsPro(fichaCasa.getGolsPro() + partida.getGolsCasa());
            fichaCasa.setGolsContra(fichaCasa.getGolsContra() + partida.getGolsVisitante());
            fichaVisitante.setGolsPro(fichaVisitante.getGolsPro() + partida.getGolsVisitante());
            fichaVisitante.setGolsContra(fichaVisitante.getGolsContra() + partida.getGolsCasa());

            fichaCasa.setJogosDisputados(fichaCasa.getJogosDisputados() + 1);
            fichaVisitante.setJogosDisputados(fichaVisitante.getJogosDisputados() + 1);

            if (partida.getGolsCasa() > partida.getGolsVisitante()) {
                //Vitória da casa
                fichaCasa.setPontos(fichaCasa.getPontos() + 3);
                fichaCasa.setVitorias(fichaCasa.getVitorias() + 1);
                fichaVisitante.setDerrotas(fichaVisitante.getDerrotas() + 1);
            } else if (partida.getGolsCasa() < partida.getGolsVisitante()) {
                // Vitória do visitante
                fichaVisitante.setPontos(fichaVisitante.getPontos() + 3);
                fichaVisitante.setVitorias(fichaVisitante.getVitorias() + 1);
                fichaCasa.setDerrotas(fichaCasa.getDerrotas() + 1);
            } else {
                // Empate
                fichaCasa.setPontos(fichaCasa.getPontos() + 1);
                fichaVisitante.setPontos(fichaVisitante.getPontos() + 1);
                fichaCasa.setEmpates(fichaCasa.getEmpates() + 1);
                fichaVisitante.setEmpates(fichaVisitante.getEmpates() + 1);
            }
        }


        // PARTE 3 - ordenar pelos critérios FIFA
        tabela.sort(Comparator
                .comparingInt(ClassificacaoSelecao::getPontos).reversed()
                .thenComparingInt(ClassificacaoSelecao::getSaldoDeGols).reversed()
                .thenComparingInt(ClassificacaoSelecao::getGolsPro).reversed()
        );

// retornar os 2 primeiros
        List<Selecao> classificados = new ArrayList<>();
        classificados.add(tabela.get(0).getSelecao());
        classificados.add(tabela.get(1).getSelecao());

        return classificados;
    }
}