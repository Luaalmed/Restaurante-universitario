package Controller;

import DAO.PedidoDAO;
import java.util.List;

public class PedidoController {
    private PedidoDAO pedidoDAO;

    public PedidoController() {
        this.pedidoDAO = new PedidoDAO();
    }

    // Buscar ITENS dos pedidos para mostrar na tabela do ADM
    
     public List<Object[]> buscarDadosParaTabela() {
         
      return pedidoDAO.buscarPedidosParaTabelaADM();

    }
    public boolean cancelarPedido(int idPedido) {
        return pedidoDAO.cancelarPedido(idPedido);
    }

    public boolean marcarComoConcluido(int idPedido) {
        return pedidoDAO.marcarComoConcluido(idPedido);
    }

   
}