package DAO;

import model.Pedido;
import model.ItemPedido;
import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Cardapio;

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
     // Buscar dados para a tabela
    public List<Object[]> buscarPedidosParaTabelaADM() {
        List<Object[]> resultados = new ArrayList<>();
        
        // Mostrar TODOS os pedidos, incluindo cancelados e entregues
        String sql = "SELECT p.id, p.status, p.total, p.forma_pagamento " +
                    "FROM " + SCHEMA + ".pedidos p " +
                    "ORDER BY p.data_pedido DESC";

        try (Connection con = Conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Object[] linha = {
                    rs.getInt("id"),
                    formatarStatus(rs.getString("status")),
                    "R$ " + String.format("%.2f", rs.getDouble("total")),
                    formatarPagamento(rs.getString("forma_pagamento"))
                };
                resultados.add(linha);
            }
            
        } catch (SQLException e) {
            System.out.println("ERRO SQL: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao carregar pedidos: " + e.getMessage());
        }
        
        return resultados;
    }

    // Cancelar pedido
    public boolean cancelarPedido(int idPedido) {
        // Usando CAST para converter string para ENUM
         String sql = "UPDATE " + SCHEMA + ".pedidos SET status = ?::restaurante_universitario.status_pedido_enum WHERE id = ?";
        
        try (Connection con = Conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, "cancelado");
            ps.setInt(2, idPedido);
            
            int affectedRows = ps.executeUpdate();
            System.out.println("Pedido " + idPedido + " CANCELADO. Linhas afetadas: " + affectedRows);
            
            if (affectedRows > 0) {
                // Verificar se realmente foi cancelado
                String verifySql = "SELECT status FROM " + SCHEMA + ".pedidos WHERE id = ?";
                try (PreparedStatement verifyPs = con.prepareStatement(verifySql)) {
                    verifyPs.setInt(1, idPedido);
                    ResultSet rs = verifyPs.executeQuery();
                    if (rs.next()) {
                        String novoStatus = rs.getString("status");
                        System.out.println("Status atual do pedido " + idPedido + ": " + novoStatus);
                    }
                }
            }
            
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.out.println("ERRO ao cancelar pedido " + idPedido + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

 

    // Marcar pedido como concluído 
     public boolean marcarComoConcluido(int idPedido) {
        String sql = "UPDATE " + SCHEMA + ".pedidos SET status = ?::restaurante_universitario.status_pedido_enum WHERE id = ?";
        
        try (Connection con = Conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, "entregue");
            ps.setInt(2, idPedido);
            
            int affectedRows = ps.executeUpdate();
            System.out.println("Pedido " + idPedido + " CONCLUÍDO. Linhas afetadas: " + affectedRows);
            
            if (affectedRows > 0) {
                // Verificar se realmente foi entregue
                String verifySql = "SELECT status FROM " + SCHEMA + ".pedidos WHERE id = ?";
                try (PreparedStatement verifyPs = con.prepareStatement(verifySql)) {
                    verifyPs.setInt(1, idPedido);
                    ResultSet rs = verifyPs.executeQuery();
                    if (rs.next()) {
                        String novoStatus = rs.getString("status");
                        System.out.println("Status atual do pedido " + idPedido + ": " + novoStatus);
                    }
                }
            }
            
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.out.println("ERRO ao concluir pedido " + idPedido + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Métodos auxiliares para formatar
    private String formatarStatus(String status) {
        if (status == null) return "Desconhecido";
        
        switch (status) {
            case "pendente": return "Pendente";
            case "em_preparacao": return "Em Preparação";
            case "pronto": return "Pronto";
            case "entregue": return "Entregue";
            case "cancelado": return "Cancelado";
            default: return status;
        }
    }

    private String formatarPagamento(String pagamento) {
        if (pagamento == null) return "Desconhecido";
        
        switch (pagamento) {
            case "cartao": return "Cartão";
            case "dinheiro": return "Dinheiro";
            case "pix": return "PIX";
            default: return pagamento;
        }
    }
    public List<Object[]> buscarPedidosPorUsuario(int idUsuario) {
    List<Object[]> resultados = new ArrayList<>();
    
    String sql = "SELECT p.id, p.status, p.total, p.forma_pagamento, p.data_pedido " +
                "FROM " + SCHEMA + ".pedidos p " +
                "WHERE p.id_usuario = ? " +
                "ORDER BY p.data_pedido DESC";

    try (Connection con = Conexao.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setInt(1, idUsuario);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            Object[] linha = {
                rs.getInt("id"),
                formatarStatus(rs.getString("status")),
                "R$ " + String.format("%.2f", rs.getDouble("total")),
                formatarPagamento(rs.getString("forma_pagamento")),
                rs.getTimestamp("data_pedido")
            };
            resultados.add(linha);
        }
        
    } catch (SQLException e) {
        System.out.println("ERRO ao buscar pedidos do usuário: " + e.getMessage());
    }
    
    return resultados;
}

// Buscar detalhes de um pedido específico
public List<Object[]> buscarItensDoPedido(int idPedido) {
    List<Object[]> resultados = new ArrayList<>();
    
    String sql = "SELECT c.nome, ip.quantidade, ip.preco_unitario, " +
                "(ip.quantidade * ip.preco_unitario) as subtotal " +
                "FROM " + SCHEMA + ".itens_pedido ip " +
                "JOIN " + SCHEMA + ".cardapio c ON ip.id_cardapio = c.id " +
                "WHERE ip.id_pedido = ?";

    try (Connection con = Conexao.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setInt(1, idPedido);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            Object[] linha = {
                rs.getString("nome"),
                rs.getInt("quantidade"),
                "R$ " + String.format("%.2f", rs.getDouble("preco_unitario")),
                "R$ " + String.format("%.2f", rs.getDouble("subtotal"))
            };
            resultados.add(linha);
        }
        
    } catch (SQLException e) {
        System.out.println("ERRO ao buscar itens do pedido: " + e.getMessage());
    }
    
    return resultados;
  }

}
