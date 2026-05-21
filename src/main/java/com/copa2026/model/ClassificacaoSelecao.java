package com.copa2026.model;

public class ClassificacaoSelecao {
    private Selecao selecao;
    private int pontos;
    private int jogosDisputados;
    private int vitorias;
    private int empates;
    private int derrotas;
    private int golsPro;
    private int golsContra;

    public ClassificacaoSelecao(Selecao selecao) {
        this.selecao = selecao;
    }

    public int getSaldoDeGols() {
        return golsPro - golsContra;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public int getJogosDisputados() {
        return jogosDisputados;
    }

    public void setJogosDisputados(int jogosDisputados) {
        this.jogosDisputados = jogosDisputados;
    }

    public int getVitorias() {
        return vitorias;
    }

    public void setVitorias(int vitorias) {
        this.vitorias = vitorias;
    }

    public int getEmpates() {
        return empates;
    }

    public void setEmpates(int empates) {
        this.empates = empates;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public void setDerrotas(int derrotas) {
        this.derrotas = derrotas;
    }

    public int getGolsContra() {
        return golsContra;
    }

    public void setGolsContra(int golsContra) {
        this.golsContra = golsContra;
    }

    public int getGolsPro() {
        return golsPro;
    }

    public void setGolsPro(int golsPro) {
        this.golsPro = golsPro;
    }

    public Selecao getSelecao(){return selecao}


}
