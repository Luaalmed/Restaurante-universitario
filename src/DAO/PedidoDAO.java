package DAO;

import model.Pedido;
import model.ItemPedido;
import java.sql.*;
import java.math.BigDecimal;
import javax.swing.JOptionPane;

public class PedidoDAO {
    private static final String SCHEMA = "restaurante_universitario";

    public boolean salvar(Pedido pedido) {
        Connection con = null;
        try {
            con = Conexao.getConnection();
            con.setAutoCommit(false); // Inicia a transação

            // 1. Salvar o Pedido (Cabeçalho)
            int idPedido = salvarPedidoHeader(con, pedido);
            if (idPedido <= 0) {
                con.rollback(); 
                return false;
            }
            pedido.setId(idPedido);

            // 2. Salvar os Itens do Pedido
            for (ItemPedido item : pedido.getItens()) {
                if (!salvarItemPedido(con, idPedido, item)) {
                    con.rollback(); 
                    return false;
                }
            }

            // 3. Confirmar a Transação
            con.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            if (con != null) {
                try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            JOptionPane.showMessageDialog(null, "Erro ao salvar o pedido: " + e.getMessage(), "Erro de BD", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            if (con != null) {
                try { con.close(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
        }
    }

   // Este é o método que precisava de correção:
private int salvarPedidoHeader(Connection con, Pedido pedido) throws SQLException {
    String sql = "INSERT INTO " + SCHEMA + ".pedidos (id_usuario, data_pedido, status, total, forma_pagamento) VALUES (?, ?, ?, ?, ?)";
    int idGerado = -1;

    // Adicionando RETURNING id para o PostgreSQL
    sql += " RETURNING id";

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, pedido.getIdUsuario());
        ps.setTimestamp(2, pedido.getDataPedido());
        
        // CORREÇÃO 1: Use setObject para o ENUM 'status'
        ps.setObject(3, pedido.getStatus(), java.sql.Types.OTHER); 
        
        ps.setBigDecimal(4, pedido.getTotal());
        
        // CORREÇÃO 2: Use setObject para o ENUM 'forma_pagamento'
        ps.setObject(5, pedido.getFormaPagamento(), java.sql.Types.OTHER);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                idGerado = rs.getInt(1);
            }
        }
    }
    return idGerado;
}

    private boolean salvarItemPedido(Connection con, int idPedido, ItemPedido item) throws SQLException {
        String sql = "INSERT INTO " + SCHEMA + ".itens_pedido (id_pedido, id_cardapio, quantidade, preco_unitario) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            ps.setInt(2, item.getItemCardapio().getId());
            ps.setInt(3, item.getQuantidade());
            ps.setBigDecimal(4, item.getPrecoUnitario());
            
            return ps.executeUpdate() > 0;
        }
    }
}