package com.copa2026.dao;

import com.copa2026.db.ConexaoBD;
import com.copa2026.model.Grupo;
import com.copa2026.model.Selecao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para a entidade Grupo (tabela `grupos` no banco).
 * Carrega os 12 grupos da Copa com suas selecoes ja preenchidas.
 *
 */
public class GrupoDAO {

    private final SelecaoDAO selecaoDAO;

    public GrupoDAO() {
        this.selecaoDAO = new SelecaoDAO();
    }

    /**
     * Lista os 12 grupos com as 4 selecoes de cada um ja carregadas.
     */
    public List<Grupo> listarTodos() throws SQLException {
        List<Grupo> grupos = new ArrayList<>();
        String sql = "SELECT id_grupos, nome FROM grupos ORDER BY nome";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_grupos");
                String nome = rs.getString("nome");

                Grupo grupo = new Grupo(id, nome);

                // Carrega as 4 selecoes desse grupo
                List<Selecao> selecoes = selecaoDAO.listarPorGrupo(id);
                grupo.setSelecoes(selecoes);

                grupos.add(grupo);
            }
        }
        return grupos;
    }
}
