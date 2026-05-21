package com.copa2026.model;

/**
 * Representa um gol marcado em uma partida.
 * Cada gol pertence a uma partida e foi marcado por uma selecao.
 *
 * CONTRATO: Esta classe e usada por toda a equipe.
 */
public class Gol {

    private int id;
    private int idPartida;
    private int idSelecao;
    private int minuto;

    public Gol() {
    }

    public Gol(int idPartida, int idSelecao, int minuto) {
        this.idPartida = idPartida;
        this.idSelecao = idSelecao;
        this.minuto = minuto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    public int getIdSelecao() {
        return idSelecao;
    }

    public void setIdSelecao(int idSelecao) {
        this.idSelecao = idSelecao;
    }

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }
}
