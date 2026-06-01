package com.copa2026.dao;

import com.copa2026.db.ConexaoBD;
import com.copa2026.model.Fase;
import com.copa2026.model.Partida;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DAO para a entidade Partida (tabela `jogos` no banco).
 * Salva e recupera partidas do banco.
 *
 */
public class PartidaDAO {

    // Cache de id_fases (carrega uma vez no construtor pra evitar consultas repetidas)
    private final Map<Fase, Integer> faseParaId = new HashMap<>();

    public PartidaDAO() {
        carregarFases();
    }

    /**
     * Carrega o mapeamento Fase (enum Java) -> id_fases (do banco) em memoria.
     */
    private void carregarFases() {
        String sql = "SELECT id_fases, nome FROM fases";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String nome = rs.getString("nome");
                int id = rs.getInt("id_fases");
                try {
                    Fase fase = Fase.valueOf(nome);
                    faseParaId.put(fase, id);
                } catch (IllegalArgumentException ignored) {
                    // Fase do banco que nao esta no enum Java - ignora
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao carregar fases: " + e.getMessage());
        }
    }

    /**
     * Salva uma partida no banco. Usa id_estadios = 1 como padrao
     * (nao chamamos estadios em todos os jogos, mas a FK existe).
     */
    public int salvar(Partida partida) throws SQLException {
        String sql = "INSERT INTO jogos " +
                "(data_hora, id_estadios, id_fases, time1_id, time2_id, " +
                "gols_time1, gols_time2, vencedor_id, " +
                "decidido_nos_penaltis, penaltis_time1, penaltis_time2) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            LocalDateTime dataPartida = partida.getDataPartida() != null
                    ? partida.getDataPartida() : LocalDateTime.now();
            stmt.setTimestamp(1, Timestamp.valueOf(dataPartida));

            stmt.setInt(2, 1); // id_estadios padrao
            stmt.setInt(3, faseParaId.getOrDefault(partida.getFase(), 1));
            stmt.setInt(4, partida.getCasa().getId());
            stmt.setInt(5, partida.getVisitante().getId());
            stmt.setInt(6, partida.getGolsCasa());
            stmt.setInt(7, partida.getGolsVisitante());

            // Vencedor: pode ser null (empate na fase de grupos)
            if (partida.getVencedor() != null) {
                stmt.setInt(8, partida.getVencedor().getId());
            } else {
                stmt.setNull(8, java.sql.Types.INTEGER);
            }

            stmt.setBoolean(9, partida.isDecididoNosPenaltis());
            stmt.setInt(10, partida.getPenaltisCasa());
            stmt.setInt(11, partida.getPenaltisVisitante());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGerado = rs.getInt(1);
                    partida.setId(idGerado);
                    return idGerado;
                }
            }
        }

        throw new SQLException("Falha ao salvar partida");
    }

    /**
     * Salva uma lista de partidas em lote.
     */
    public void salvarLote(List<Partida> partidas) throws SQLException {
        for (Partida p : partidas) {
            salvar(p);
        }
    }

    /**
     * Apaga todas as partidas do banco (util para resetar uma simulacao).
     */
    public void limparTodas() throws SQLException {
        try (Connection conn = ConexaoBD.conectar()) {
            // Apaga gols primeiro (FK)
            try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM gols")) {
                stmt.executeUpdate();
            }
            // Depois apaga as partidas
            try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM jogos")) {
                stmt.executeUpdate();
            }
        }
    }
}
