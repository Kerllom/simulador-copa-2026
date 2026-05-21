package com.copa2026.model;

/**
 * Representa uma selecao participante da Copa do Mundo 2026.
 * Cada selecao pertence a um grupo durante a fase de grupos.
 *
 * CONTRATO: Esta classe e usada por toda a equipe.
 * Nao alterar nomes de campos sem avisar no grupo.
 */
public class Selecao {

    private int id;
    private String nome;
    private String pais;
    private String confederacao; // CONMEBOL, UEFA, AFC, CAF, CONCACAF, OFC
    private int idGrupo;

    public Selecao() {
    }

    public Selecao(int id, String nome, String pais, String confederacao, int idGrupo) {
        this.id = id;
        this.nome = nome;
        this.pais = pais;
        this.confederacao = confederacao;
        this.idGrupo = idGrupo;
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

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getConfederacao() {
        return confederacao;
    }

    public void setConfederacao(String confederacao) {
        this.confederacao = confederacao;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    @Override
    public String toString() {
        return nome;
    }
}
