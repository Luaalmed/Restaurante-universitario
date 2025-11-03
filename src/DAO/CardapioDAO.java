package DAO;

// Imports necessários
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Cardapio;
import java.math.BigDecimal; // Importe BigDecimal

public class CardapioDAO {

    /**
     * Salva um novo item no cardápio.
     */
    public boolean salvar(Cardapio cardapio) {
        String sql = "INSERT INTO restaurante_universitario.cardapio "
                + "(nome, descricao, preco, categoria, quantidade_estoque, data_criacao, data_atualizacao) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        // CORRIGIDO: Usando Conexao.getConnection()
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cardapio.getNome());
            stmt.setString(2, cardapio.getDescricao());
            stmt.setBigDecimal(3, cardapio.getPreco());
            // Se 'categoria' for um ENUM no Postgres, java.sql.Types.OTHER é correto
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

    /**
     * Lista TODOS os itens do cardápio (geralmente para admin).
     */
    public List<Cardapio> listarTodos() throws SQLException {
        String sql = "SELECT id, nome, categoria, preco, quantidade_estoque FROM restaurante_universitario.cardapio ORDER BY id";
        List<Cardapio> lista = new ArrayList<>();

        // CORRIGIDO: Usando Conexao.getConnection()
        try (Connection conn = Conexao.getConnection();
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

    /**
     * Busca itens por nome ou categoria (exemplo de busca).
     */
    public List<Cardapio> buscarPorNomeOuCategoria(String termo) throws SQLException {
        String sql = "SELECT id, nome, categoria::text, preco, quantidade_estoque " +
                     "FROM restaurante_universitario.cardapio " +
                     "WHERE LOWER(nome) LIKE LOWER(?) OR LOWER(categoria::text) LIKE LOWER(?) ORDER BY id";
        List<Cardapio> lista = new ArrayList<>();

        // CORRIGIDO: Usando Conexao.getConnection()
        try (Connection conn = Conexao.getConnection();
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
    
    /**
     * Atualiza o estoque de um item.
     */
   public void atualizarEstoque(int id, int novaQuantidade) throws SQLException {
    String sql = "UPDATE restaurante_universitario.cardapio SET quantidade_estoque = ?, data_atualizacao = CURRENT_TIMESTAMP WHERE id = ?";

    try (Connection conn = Conexao.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, novaQuantidade);
        stmt.setInt(2, id);
        stmt.executeUpdate();
        }
    }
    
    // =========================================================================
    // MÉTODOS USADOS PELO FazerPedidoController
    // =========================================================================

    /**
     * Busca todos os itens que estão marcados como "disponível" no banco.
     */
    public List<Cardapio> buscarTodosDisponiveis() throws SQLException {
        List<Cardapio> itens = new ArrayList<>();
        String sql = """
            SELECT id, nome, descricao, preco, categoria, disponivel
            FROM restaurante_universitario.cardapio
            WHERE disponivel = TRUE
            ORDER BY categoria, nome
            """;

        // OK: Já estava usando Conexao.getConnection()
        try (Connection con = Conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cardapio c = new Cardapio();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setDescricao(rs.getString("descricao"));
                c.setPreco(rs.getBigDecimal("preco"));
                c.setCategoria(rs.getString("categoria"));
                // c.setDisponivel(rs.getBoolean("disponivel")); // Opcional, já filtramos por TRUE
                itens.add(c);
            }
        }
        return itens;
    }

    /**
     * Busca itens disponíveis por nome ou descrição.
     * Este é o método que seu Controller usa na barra de busca.
     */
    public List<Cardapio> buscarPorNomeOuDescricao(String termoBusca) throws SQLException {
        List<Cardapio> itens = new ArrayList<>();
        String sql = """
            SELECT id, nome, descricao, preco, categoria, disponivel
            FROM restaurante_universitario.cardapio
            WHERE disponivel = TRUE
            AND (LOWER(nome) LIKE LOWER(?) OR LOWER(descricao) LIKE LOWER(?))
            ORDER BY nome
            """;

        // OK: Já estava usando Conexao.getConnection()
        try (Connection con = Conexao.getConnection();
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

    /**
     * [ADICIONADO] Busca um item específico pelo seu ID.
     * Este era o método que estava faltando.
     */
    public Cardapio buscarPorId(int id) throws SQLException {
        // Seleciona todas as colunas para montar o objeto completo
        String sql = "SELECT * FROM restaurante_universitario.cardapio WHERE id = ?";
        Cardapio item = null; 

        // Usando Conexao.getConnection()
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id); // Define o ID que estamos buscando

            try (ResultSet rs = stmt.executeQuery()) {
                
                if (rs.next()) { // Verifica se encontrou o item
                    item = new Cardapio(); 
                    
                    // Preenche o objeto Cardapio com TODOS os dados do banco
                    item.setId(rs.getInt("id"));
                    item.setNome(rs.getString("nome"));
                    item.setDescricao(rs.getString("descricao")); 
                    item.setPreco(rs.getBigDecimal("preco"));
                    item.setCategoria(rs.getString("categoria"));
                    item.setQuantidadeEstoque(rs.getInt("quantidade_estoque")); 

                    // Se houver mais campos (data_criacao, etc.), adicione os setters aqui
                }
            }
        }
        
        return item; // Retorna o item encontrado ou null se não encontrar
    }
    // Em DAO.CardapioDAO.java
public int getQuantidadeEstoqueAtual(int idCardapio) throws SQLException {
    String sql = "SELECT quantidade_estoque FROM restaurante_universitario.cardapio WHERE id = ?";
    
    try (Connection conn = Conexao.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, idCardapio);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("quantidade_estoque");
            }
        }
    }
    return 0; // Retorna 0 se não encontrar o item ou o estoque
}
}