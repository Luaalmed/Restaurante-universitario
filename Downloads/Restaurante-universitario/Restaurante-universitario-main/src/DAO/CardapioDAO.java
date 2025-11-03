package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Cardapio;
import java.math.BigDecimal;

public class CardapioDAO {

    /** Salva um novo item no cardápio. */
    public boolean salvar(Cardapio cardapio) {
        String sql = "INSERT INTO restaurante_universitario.cardapio "
                + "(nome, descricao, preco, categoria, quantidade_estoque, data_criacao, data_atualizacao) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cardapio.getNome());
            stmt.setString(2, cardapio.getDescricao());
            stmt.setBigDecimal(3, cardapio.getPreco());
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

    /** Lista todos os itens do cardápio. */
    public List<Cardapio> listarTodos() throws SQLException {
        String sql = "SELECT id, nome, categoria, preco, quantidade_estoque "
                   + "FROM restaurante_universitario.cardapio ORDER BY id";
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

    /** Busca itens por nome ou categoria. */
    public List<Cardapio> buscarPorNomeOuCategoria(String termo) throws SQLException {
        String sql = "SELECT id, nome, categoria::text, preco, quantidade_estoque "
                   + "FROM restaurante_universitario.cardapio "
                   + "WHERE LOWER(nome) LIKE LOWER(?) OR LOWER(categoria::text) LIKE LOWER(?) ORDER BY id";
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

    /** Atualiza o estoque de um item. */
    public void atualizarEstoque(int id, int novaQuantidade) throws SQLException {
        String sql = "UPDATE restaurante_universitario.cardapio "
                   + "SET quantidade_estoque = ?, data_atualizacao = CURRENT_TIMESTAMP WHERE id = ?";

        try (Connection conn = DAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, novaQuantidade);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }

    /** Busca todos os itens disponíveis. */
    public List<Cardapio> buscarTodosDisponiveis() throws SQLException {
        List<Cardapio> itens = new ArrayList<>();
        String sql = """
            SELECT id, nome, descricao, preco, categoria, disponivel
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

    /** Busca itens disponíveis por nome ou descrição. */
    public List<Cardapio> buscarPorNomeOuDescricao(String termoBusca) throws SQLException {
        List<Cardapio> itens = new ArrayList<>();
        String sql = """
            SELECT id, nome, descricao, preco, categoria, disponivel
            FROM restaurante_universitario.cardapio
            WHERE disponivel = TRUE
            AND (LOWER(nome) LIKE LOWER(?) OR LOWER(descricao) LIKE LOWER(?))
            ORDER BY nome
            """;

        try (Connection con = DAO.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String buscaComWildcard = "%" + termoBusca + "%";
            ps.setString(1, buscaComWildcard);
            ps.setString(2, buscaComWildcard);

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

    /** Busca um item específico pelo ID. */
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

    /** Retorna o estoque atual de um item. */
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
}
