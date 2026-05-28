package com.copa2026.dao;

import com.copa2026.db.ConexaoBD;
import com.copa2026.model.Selecao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para a entidade Selecao (tabela `times` no banco do Ricardo).
 * Responsavel por todas as operacoes de leitura de selecoes.
 *
 */
public class SelecaoDAO {

    /**
     * Lista todas as 48 selecoes cadastradas no banco.
     */
    public List<Selecao> listarTodas() throws SQLException {
        List<Selecao> selecoes = new ArrayList<>();
        String sql = "SELECT id_times, nome, sigla, confederacao, id_grupos " +
                     "FROM times ORDER BY id_grupos, id_times";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                selecoes.add(mapearSelecao(rs));
            }
        }
        return selecoes;
    }

    /**
     * Busca todas as selecoes de um grupo especifico.
     */
    public List<Selecao> listarPorGrupo(int idGrupo) throws SQLException {
        List<Selecao> selecoes = new ArrayList<>();
        String sql = "SELECT id_times, nome, sigla, confederacao, id_grupos " +
                     "FROM times WHERE id_grupos = ? ORDER BY id_times";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idGrupo);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    selecoes.add(mapearSelecao(rs));
                }
            }
        }
        return selecoes;
    }

    /**
     * Busca uma selecao pelo id.
     */
    public Selecao buscarPorId(int id) throws SQLException {
        String sql = "SELECT id_times, nome, sigla, confederacao, id_grupos " +
                     "FROM times WHERE id_times = ?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearSelecao(rs);
                }
            }
        }
        return null;
    }

    /**
     * Converte uma linha do ResultSet em um objeto Selecao.
     * Note que o nome da selecao e usado tambem como "pais" (mesma string).
     */
    private Selecao mapearSelecao(ResultSet rs) throws SQLException {
        return new Selecao(
                rs.getInt("id_times"),
                rs.getString("nome"),
                rs.getString("nome"),  // pais = nome (nao temos campo separado)
                rs.getString("confederacao"),
                rs.getInt("id_grupos")
        );
    }
}
