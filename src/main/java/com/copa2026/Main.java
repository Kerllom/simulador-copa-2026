package com.copa2026;

import com.copa2026.db.ConexaoBD;

/**
 * Ponto de entrada do simulador da Copa do Mundo 2026.
 *
 * RESPONSAVEL: Kerllom (integracao final).
 *
 * Por enquanto, apenas testa a conexao com o banco de dados.
 * A logica completa sera implementada apos as fases de grupos e eliminatorias
 * estarem prontas.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("  SIMULADOR DA COPA DO MUNDO 2026");
        System.out.println("===========================================");
        System.out.println();

        System.out.println("Testando conexao com o banco de dados...");
        if (ConexaoBD.testarConexao()) {
            System.out.println("[OK] Conexao com o banco bem-sucedida!");
        } else {
            System.out.println("[ERRO] Nao foi possivel conectar ao banco.");
            System.out.println("Verifique o arquivo config.properties.");
        }
    }
}