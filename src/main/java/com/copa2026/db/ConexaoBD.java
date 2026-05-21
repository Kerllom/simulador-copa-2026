package com.copa2026.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Classe responsavel por gerenciar a conexao com o banco de dados MySQL.
 * Le as credenciais do arquivo config.properties (que deve estar na raiz do projeto).
 *
 * IMPORTANTE: O arquivo config.properties NAO pode ser commitado no Git.
 * Use config.properties.example como modelo e crie seu config.properties local.
 *
 * Uso:
 *   try (Connection conn = ConexaoBD.conectar()) {
 *       // operacoes com o banco
 *   } catch (SQLException e) {
 *       e.printStackTrace();
 *   }
 */
public class ConexaoBD {

    private static final Properties props = new Properties();
    private static boolean carregado = false;

    /**
     * Carrega as propriedades do arquivo config.properties.
     * Tenta primeiro como arquivo na raiz do projeto, depois como recurso do classpath.
     */
    private static void carregarPropriedades() {
        if (carregado) {
            return;
        }

        // Tenta carregar do arquivo na raiz do projeto
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            props.load(fis);
            carregado = true;
            return;
        } catch (IOException e) {
            // Se nao achar, tenta carregar do classpath (src/main/resources)
        }

        try (InputStream is = ConexaoBD.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (is == null) {
                throw new RuntimeException(
                    "Arquivo config.properties nao encontrado. " +
                    "Copie config.properties.example para config.properties e configure suas credenciais."
                );
            }
            props.load(is);
            carregado = true;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar config.properties", e);
        }
    }

    /**
     * Abre uma nova conexao com o banco de dados.
     * IMPORTANTE: sempre feche a conexao apos o uso (use try-with-resources).
     *
     * @return Connection ativa com o banco
     * @throws SQLException se houver erro na conexao
     */
    public static Connection conectar() throws SQLException {
        carregarPropriedades();

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        if (url == null || user == null) {
            throw new SQLException(
                "Credenciais do banco nao configuradas em config.properties"
            );
        }

        return DriverManager.getConnection(url, user, password);
    }

    /**
     * Testa a conexao com o banco. Util para verificar se a configuracao esta OK.
     *
     * @return true se conseguiu conectar, false caso contrario
     */
    public static boolean testarConexao() {
        try (Connection conn = conectar()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Falha ao conectar no banco: " + e.getMessage());
            return false;
        }
    }
}
