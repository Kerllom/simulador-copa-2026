package com.copa2026.model;

import java.time.LocalDateTime;

/**
 * Representa uma partida da Copa do Mundo 2026.
 * Pode ser da fase de grupos ou da fase eliminatoria.
 *
 * Na fase eliminatoria, se houver empate apos o tempo regulamentar,
 * o vencedor e definido por penaltis (campos penaltisCasa/penaltisVisitante).
 *
 * CONTRATO: Esta classe e usada por toda a equipe.
 */
public class Partida {

    private int id;
    private Selecao casa;
    private Selecao visitante;
    private int golsCasa;
    private int golsVisitante;
    private Fase fase;
    private boolean decididoNosPenaltis;
    private int penaltisCasa;
    private int penaltisVisitante;
    private LocalDateTime dataPartida;

    public Partida() {
    }

    public Partida(Selecao casa, Selecao visitante, Fase fase) {
        this.casa = casa;
        this.visitante = visitante;
        this.fase = fase;
        this.decididoNosPenaltis = false;
    }

    /**
     * Retorna a selecao vencedora da partida.
     * Em caso de empate na fase de grupos, retorna null.
     * Em fase eliminatoria, retorna o vencedor dos penaltis se houver empate.
     */
    public Selecao getVencedor() {
        if (golsCasa > golsVisitante) {
            return casa;
        }
        if (golsVisitante > golsCasa) {
            return visitante;
        }
        // Empate
        if (decididoNosPenaltis) {
            return penaltisCasa > penaltisVisitante ? casa : visitante;
        }
        return null;
    }

    public boolean isEmpate() {
        return golsCasa == golsVisitante;
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Selecao getCasa() {
        return casa;
    }

    public void setCasa(Selecao casa) {
        this.casa = casa;
    }

    public Selecao getVisitante() {
        return visitante;
    }

    public void setVisitante(Selecao visitante) {
        this.visitante = visitante;
    }

    public int getGolsCasa() {
        return golsCasa;
    }

    public void setGolsCasa(int golsCasa) {
        this.golsCasa = golsCasa;
    }

    public int getGolsVisitante() {
        return golsVisitante;
    }

    public void setGolsVisitante(int golsVisitante) {
        this.golsVisitante = golsVisitante;
    }

    public Fase getFase() {
        return fase;
    }

    public void setFase(Fase fase) {
        this.fase = fase;
    }

    public boolean isDecididoNosPenaltis() {
        return decididoNosPenaltis;
    }

    public void setDecididoNosPenaltis(boolean decididoNosPenaltis) {
        this.decididoNosPenaltis = decididoNosPenaltis;
    }

    public int getPenaltisCasa() {
        return penaltisCasa;
    }

    public void setPenaltisCasa(int penaltisCasa) {
        this.penaltisCasa = penaltisCasa;
    }

    public int getPenaltisVisitante() {
        return penaltisVisitante;
    }

    public void setPenaltisVisitante(int penaltisVisitante) {
        this.penaltisVisitante = penaltisVisitante;
    }

    public LocalDateTime getDataPartida() {
        return dataPartida;
    }

    public void setDataPartida(LocalDateTime dataPartida) {
        this.dataPartida = dataPartida;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(casa.getNome()).append(" ").append(golsCasa);
        sb.append(" x ");
        sb.append(golsVisitante).append(" ").append(visitante.getNome());
        if (decididoNosPenaltis) {
            sb.append(" (penaltis ").append(penaltisCasa).append("-").append(penaltisVisitante).append(")");
        }
        return sb.toString();
    }
}
