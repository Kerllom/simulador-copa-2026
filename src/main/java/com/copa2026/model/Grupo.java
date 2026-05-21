package com.copa2026.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa um grupo da fase de grupos (A, B, C, ..., L).
 * Cada grupo tem 4 selecoes na Copa de 2026.
 *
 * CONTRATO: Esta classe e usada por toda a equipe.
 */

public class Grupo {

    private int id;
    private String nome; // "A", "B", ..., "L"
    private List<Selecao> selecoes;

    public Grupo() {
        this.selecoes = new ArrayList<>();
    }

    public Grupo(int id, String nome) {
        this.id = id;
        this.nome = nome;
        this.selecoes = new ArrayList<>();
    }

    public Grupo(int id, String nome, List<Selecao> selecoes) {
        this.id = id;
        this.nome = nome;
        this.selecoes = selecoes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Selecao> getSelecoes() {
        return selecoes;
    }

    public void setSelecoes(List<Selecao> selecoes) {
        this.selecoes = selecoes;
    }

    public void adicionarSelecao(Selecao selecao) {
        this.selecoes.add(selecao);
    }

    @Override
    public String toString() {
        return "Grupo " + nome;
    }
}
