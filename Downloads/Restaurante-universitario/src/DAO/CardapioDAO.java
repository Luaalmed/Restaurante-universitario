package DAO;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Cardapio;

public class CardapioDAO {

    // ==================== SALVAR ====================
    public boolean salvar(Cardapio cardapio) {
        String sql = "INSERT INTO restaurante_universitario.cardapio "
                + "(nome, descricao, preco, categoria, quantidade_estoque, data_criacao, data_atualizacao) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cardapio.getNome());
            stmt.setString(2, cardapio.getDescricao());
            stmt.setBigDecimal(3, cardapio.getPreco());
            // ENUM ou TEXT funcionam com setObject(Types.OTHER) e com setString
            stmt.setObject(4, cardapio.getCategoria(), java.sql.Types.OTHER);
            stmt.setInt(5, cardapio.getQuantidadeEstoque());
            stmt.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ==================== LISTAR TODOS ====================
    public List<Cardapio> listarTodos() throws SQLException {
        String sql = "SELECT id, nome, categoria, preco, quantidade_estoque "
                   + "FROM restaurante_universitario.cardapio "
                   + "ORDER BY id";
        List<Cardapio> lista = new ArrayList<>();

        try (Connection conn = DAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cardapio c = new Cardapio();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setCategoria(rs.getString("categoria"));
                c.setPreco(rs.getBigDecimal("preco"));
                c.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
                lista.add(c);
            }
        }
        return lista;
    }

    // ==================== BUSCAR POR NOME/CATEGORIA ====================
    public List<Cardapio> buscarPorNomeOuCategoria(String termo) throws SQLException {
        String sql = "SELECT id, nome, categoria::text, preco, quantidade_estoque "
                   + "FROM restaurante_universitario.cardapio "
                   + "WHERE LOWER(nome) LIKE LOWER(?) OR LOWER(categoria::text) LIKE LOWER(?) "
                   + "ORDER BY id";
        List<Cardapio> lista = new ArrayList<>();

        try (Connection conn = DAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + termo + "%");
            stmt.setString(2, "%" + termo + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Cardapio c = new Cardapio(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("categoria"),
                        rs.getBigDecimal("preco"),
                        rs.getInt("quantidade_estoque")
                    );
                    lista.add(c);
                }
            }
        }
        return lista;
    }

    // ==================== ATUALIZAR ESTOQUE ====================
    public void atualizarEstoque(int id, int novaQuantidade) throws SQLException {
        String sql = "UPDATE restaurante_universitario.cardapio "
                   + "SET quantidade_estoque = ?, data_atualizacao = CURRENT_TIMESTAMP "
                   + "WHERE id = ?";

        try (Connection conn = DAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, novaQuantidade);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }

    // ==================== (NOVO) BUSCAR PARA CARDÁPIO (sem depender de 'disponivel') ====================
    public List<Cardapio> buscarParaCardapio() throws SQLException {
        List<Cardapio> itens = new ArrayList<>();
        String sql = """
            SELECT id,
                   nome,
                   descricao,
                   preco,
                   categoria::text AS categoria_txt
            FROM restaurante_universitario.cardapio
            ORDER BY nome
            """;

        try (Connection con = DAO.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cardapio c = new Cardapio();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setDescricao(rs.getString("descricao"));
                c.setPreco(rs.getBigDecimal("preco"));
                c.setCategoria(rs.getString("categoria_txt")); // ok se for enum/text
                itens.add(c);
            }
        }
        return itens;
    }

    // ==================== AINDA ÚTEIS ====================
    public List<Cardapio> buscarTodosDisponiveis() throws SQLException {
        List<Cardapio> itens = new ArrayList<>();
        String sql = """
            SELECT id, nome, descricao, preco, categoria
            FROM restaurante_universitario.cardapio
            WHERE disponivel = TRUE
            ORDER BY categoria, nome
            """;

        try (Connection con = DAO.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cardapio c = new Cardapio();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setDescricao(rs.getString("descricao"));
                c.setPreco(rs.getBigDecimal("preco"));
                c.setCategoria(rs.getString("categoria"));
                itens.add(c);
            }
        }
        return itens;
    }

    public List<Cardapio> buscarDisponiveisPorCategoria(String categoria) throws SQLException {
        List<Cardapio> itens = new ArrayList<>();
        String sql = """
            SELECT id, nome, descricao, preco, categoria
            FROM restaurante_universitario.cardapio
            WHERE disponivel = TRUE
              AND UPPER(categoria) = UPPER(?)
            ORDER BY nome
            """;
        try (Connection con = DAO.getConnection();
             PreparedStatement ps  = con.prepareStatement(sql)) {

            ps.setString(1, categoria);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Cardapio c = new Cardapio();
                    c.setId(rs.getInt("id"));
                    c.setNome(rs.getString("nome"));
                    c.setDescricao(rs.getString("descricao"));
                    c.setPreco(rs.getBigDecimal("preco"));
                    c.setCategoria(rs.getString("categoria"));
                    itens.add(c);
                }
            }
        }
        return itens;
    }

    public Cardapio buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM restaurante_universitario.cardapio WHERE id = ?";
        Cardapio item = null;

        try (Connection conn = DAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    item = new Cardapio();
                    item.setId(rs.getInt("id"));
                    item.setNome(rs.getString("nome"));
                    item.setDescricao(rs.getString("descricao"));
                    item.setPreco(rs.getBigDecimal("preco"));
                    item.setCategoria(rs.getString("categoria"));
                    item.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
                }
            }
        }
        return item;
    }

    public int getQuantidadeEstoqueAtual(int idCardapio) throws SQLException {
        String sql = "SELECT quantidade_estoque FROM restaurante_universitario.cardapio WHERE id = ?";

        try (Connection conn = DAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCardapio);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("quantidade_estoque");
                }
            }
        }
        return 0;
    }

    // ==================== DIAGNÓSTICOS ====================
    public String diagnosticoCardapio() {
        StringBuilder sb = new StringBuilder();
        String sqlCount = "SELECT COUNT(*) AS total FROM restaurante_universitario.cardapio";
        String sqlCats  = "SELECT DISTINCT categoria::text AS cat FROM restaurante_universitario.cardapio ORDER BY 1";

        try (Connection con = DAO.getConnection()) {
            if (con == null) return "Conexão nula: verifique driver/URL/usuário/senha/servidor.";

            try (PreparedStatement ps = con.prepareStatement(sqlCount);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) sb.append("Linhas na tabela: ").append(rs.getInt("total")).append("\n");
            }

            sb.append("Categorias encontradas:\n");
            try (PreparedStatement ps = con.prepareStatement(sqlCats);
                 ResultSet rs = ps.executeQuery()) {
                boolean alguma = false;
                while (rs.next()) {
                    alguma = true;
                    sb.append(" - ").append(rs.getString("cat")).append("\n");
                }
                if (!alguma) sb.append(" (nenhuma)\n");
            }
        } catch (SQLException e) {
            return "Erro ao diagnosticar tabela: " + e.getMessage();
        }
        return sb.toString();
    }

    public String diagnosticoAmbiente() {
        String sql = "SELECT current_database() AS db, current_schema() AS schema, setting AS search_path " +
                     "FROM pg_settings WHERE name = 'search_path'";
        try (Connection con = DAO.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return "DB: " + rs.getString("db") +
                       " | schema atual: " + rs.getString("schema") +
                       " | search_path: " + rs.getString("search_path");
            }
        } catch (SQLException e) {
            return "Erro ao ler ambiente: " + e.getMessage();
        }
        return "(sem info de ambiente)";
    }
}
