package com.copa2026;

import com.copa2026.cli.MenuCLI;
import com.copa2026.dao.GrupoDAO;
import com.copa2026.db.ConexaoBD;
import com.copa2026.model.Grupo;

import java.sql.SQLException;
import java.util.List;

/**
 * Ponto de entrada do simulador da Copa do Mundo 2026.
 *
 *
 * Fluxo:
 *   1. Testa conexao com o banco
 *   2. Carrega os 12 grupos (com suas 48 selecoes) via DAO
 *   3. Inicia o menu CLI
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("  SIMULADOR DA COPA DO MUNDO 2026");
        System.out.println("===========================================");
        System.out.println();

        // 1. Testa conexao com o banco
        System.out.println("Testando conexao com o banco de dados...");
        if (!ConexaoBD.testarConexao()) {
            System.err.println("[ERRO] Nao foi possivel conectar ao banco.");
            System.err.println("Verifique:");
            System.err.println("  - XAMPP esta rodando (MySQL verde)?");
            System.err.println("  - O banco copa_mundo existe? (rode o schema.sql)");
            System.err.println("  - As 48 selecoes estao no banco? (rode o seed.sql)");
            System.err.println("  - As credenciais em config.properties estao corretas?");
            return;
        }
        System.out.println("[OK] Conexao com o banco bem-sucedida!");

        // 2. Carrega os grupos via DAO
        System.out.println("Carregando grupos e selecoes do banco...");
        List<Grupo> grupos;
        try {
            GrupoDAO grupoDAO = new GrupoDAO();
            grupos = grupoDAO.listarTodos();

            if (grupos.isEmpty()) {
                System.err.println("[ERRO] Nenhum grupo encontrado no banco.");
                System.err.println("Execute o seed.sql para popular as 48 selecoes.");
                return;
            }

            int totalSelecoes = grupos.stream()
                    .mapToInt(g -> g.getSelecoes().size())
                    .sum();
            System.out.println("[OK] " + grupos.size() + " grupos carregados, "
                    + totalSelecoes + " selecoes.");
        } catch (SQLException e) {
            System.err.println("[ERRO] Falha ao carregar grupos: " + e.getMessage());
            return;
        }

        System.out.println();

        // 3. Inicia o menu CLI
        MenuCLI menu = new MenuCLI(grupos);
        menu.iniciar();
    }
}
