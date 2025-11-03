package Controller;

import DAO.PedidoDAO;
import Model.SessaoUsuario;
import java.util.List;
import javax.swing.JOptionPane;

public class PedidoClienteController {
    private PedidoDAO pedidoDAO;

    public PedidoClienteController() {
        this.pedidoDAO = new PedidoDAO();
    }

    // Buscar pedidos do usuário logado
    public List<Object[]> buscarMeusPedidos() {
        try {
            int idUsuario = SessaoUsuario.getInstance().getId();
            return pedidoDAO.buscarPedidosPorUsuario(idUsuario);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: Usuário não logado!");
            return null;
        }
    }

    // Buscar itens de um pedido específico
    public List<Object[]> buscarItensDoPedido(int idPedido) {
        return pedidoDAO.buscarItensDoPedido(idPedido);
    }

    // Verificar se usuário está logado
    public boolean usuarioEstaLogado() {
        try {
            SessaoUsuario.getInstance().getId();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Cancelar pedido do usuário (com verificação de segurança)
    public boolean cancelarMeuPedido(int idPedido) {
        try {
            int idUsuario = SessaoUsuario.getInstance().getId();
            
            // Verificar se o pedido realmente pertence ao usuário
            List<Object[]> meusPedidos = pedidoDAO.buscarPedidosPorUsuario(idUsuario);
            boolean pedidoPertenceAoUsuario = false;
            String statusAtual = "";
            
            for (Object[] pedido : meusPedidos) {
                if ((int) pedido[0] == idPedido) {
                    pedidoPertenceAoUsuario = true;
                    statusAtual = (String) pedido[1]; // Status formatado
                    break;
                }
            }
            
            if (!pedidoPertenceAoUsuario) {
                JOptionPane.showMessageDialog(null, "Este pedido não pertence a você!");
                return false;
            }
            
            // Verificar se o pedido pode ser cancelado
            if ("Entregue".equals(statusAtual) || "Cancelado".equals(statusAtual) || "Pronto".equals(statusAtual)) {
                JOptionPane.showMessageDialog(null, 
                    "Não é possível cancelar um pedido com status: " + statusAtual + "\n" +
                    "Apenas pedidos 'Pendente' ou 'Em Preparação' podem ser cancelados.");
                return false;
            }
            
            // Cancelar o pedido
            boolean sucesso = pedidoDAO.cancelarPedido(idPedido);
            if (sucesso) {
                System.out.println("Pedido " + idPedido + " cancelado pelo usuário " + idUsuario);
            }
            return sucesso;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cancelar pedido: " + e.getMessage());
            return false;
        }
    }
}