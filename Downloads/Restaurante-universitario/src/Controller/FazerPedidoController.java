package Controller;

import DAO.CardapioDAO;
import DAO.PedidoDAO;
import View.FazerPedido;
import model.Cardapio;
import model.ItemPedido;
import model.Pedido;
import Model.SessaoUsuario; // Certifique-se de que essa classe existe e armazena o ID do usuário logado!
import View.PainelAdmin;
import View.PainelAluno;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FazerPedidoController {

    private final FazerPedido tela;
    private final CardapioDAO cardapioDao;
    private final PedidoDAO pedidoDao;
    
    // Lista principal para armazenar os itens temporariamente (o "carrinho")
    private final List<ItemPedido> carrinho; 

    public FazerPedidoController(FazerPedido tela) {
        this.tela = tela;
        this.cardapioDao = new CardapioDAO();
        this.pedidoDao = new PedidoDAO();
        this.carrinho = new ArrayList<>();
        registrarEventos();
        carregarCardapioInicial();
    }

    private void registrarEventos() {
        tela.getBtnBuscar().addActionListener(evt -> buscarItens());
        tela.getBtnAdicionarPedido().addActionListener(evt -> {
            try {
                adicionarItemAoCarrinho();
            } catch (SQLException ex) {
                Logger.getLogger(FazerPedidoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        tela.getBtnRemoverItem().addActionListener(evt -> removerItemDoCarrinho());
        tela.getBtnFinalizarPedido().addActionListener(evt -> finalizarPedido());
    }
    
    private void carregarCardapioInicial() {
        try {
            List<Cardapio> itens = cardapioDao.buscarTodosDisponiveis();
            tela.atualizarTabelaCardapio(itens);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(tela, "Erro ao carregar o cardápio: " + e.getMessage(), "Erro de BD", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarItens() {
        String termoBusca = tela.getTxtBusca().getText().trim();
        try {
            List<Cardapio> resultados;
            if (termoBusca.isEmpty()) {
                resultados = cardapioDao.buscarTodosDisponiveis();
            } else {
                resultados = cardapioDao.buscarPorNomeOuDescricao(termoBusca);
            }
            
            if (resultados.isEmpty()) {
                JOptionPane.showMessageDialog(tela, "Nenhum prato encontrado.", "Busca", JOptionPane.INFORMATION_MESSAGE);
            }
            tela.atualizarTabelaCardapio(resultados);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(tela, "Erro na busca: " + e.getMessage(), "Erro de BD", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void adicionarItemAoCarrinho() throws SQLException {
        int selectedRow = tela.getTabelaCardapio().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(tela, "Selecione um prato na tabela do cardápio.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int idCardapio = (int) tela.getTabelaCardapio().getValueAt(selectedRow, 0);
            
            // Busca o objeto completo para garantir que o preço e ID estão corretos
            Cardapio itemSelecionado = cardapioDao.buscarPorId(idCardapio); 

            if (itemSelecionado == null) {
                JOptionPane.showMessageDialog(tela, "Erro: Item não encontrado no banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String qtdText = JOptionPane.showInputDialog(tela, "Informe a quantidade desejada para " + itemSelecionado.getNome() + ":", "Quantidade", JOptionPane.QUESTION_MESSAGE);
            
            if (qtdText == null || qtdText.trim().isEmpty()) return;

            int quantidade = Integer.parseInt(qtdText.trim());
            
            if (quantidade <= 0) {
                 JOptionPane.showMessageDialog(tela, "A quantidade deve ser maior que zero.", "Erro", JOptionPane.ERROR_MESSAGE);
                 return;
            }

            // Cria ItemPedido e adiciona ao carrinho
            ItemPedido novoItem = new ItemPedido(itemSelecionado, quantidade);
            carrinho.add(novoItem);
            
            // Atualiza a tabela do carrinho
            atualizarTabelaCarrinho();
            
        } catch (NumberFormatException e) {
             JOptionPane.showMessageDialog(tela, "Quantidade inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void removerItemDoCarrinho() {
        int selectedRow = tela.getTabelaCarrinho().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(tela, "Selecione um item no carrinho para remover.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Remove o item da lista do carrinho
        carrinho.remove(selectedRow);
        
        // Atualiza a visualização
        atualizarTabelaCarrinho();
    }
    
    private void atualizarTabelaCarrinho() {
        DefaultTableModel modelo = (DefaultTableModel) tela.getTabelaCarrinho().getModel();
        modelo.setRowCount(0); // Limpa a tabela

        BigDecimal totalGeral = BigDecimal.ZERO;
        
        for (ItemPedido item : carrinho) {
            BigDecimal subtotal = item.getSubtotal();
            totalGeral = totalGeral.add(subtotal);
            
            modelo.addRow(new Object[]{
                item.getItemCardapio().getNome(),
                item.getQuantidade(),
                item.getPrecoUnitario(),
                subtotal
            });
        }
        
    }
    // Em Controller.FazerPedidoController.java

private void finalizarPedido() {
    if (carrinho.isEmpty()) {
        JOptionPane.showMessageDialog(tela, "O pedido está vazio.", "Atenção", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // 1. Obter dados finais do Pedido
    Pedido pedido = new Pedido();
    
    // **IMPORTANTE**: Deve ser o ID do usuário ALUNO logado
    try {
        // Usando SessaoUsuario.getInstance().getId() é mais seguro se for uma classe Singleton
        pedido.setIdUsuario(SessaoUsuario.getInstance().getId());
    } catch (Exception e) {
        JOptionPane.showMessageDialog(tela, "Erro: Usuário não logado. Faça login antes de pedir.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // 2. Obter Forma de Pagamento
    String formaPagamento = (String) JOptionPane.showInputDialog(tela,
        "Selecione a Forma de Pagamento:",
        "Finalizar Pedido",
        JOptionPane.QUESTION_MESSAGE,
        null,
        new String[]{"dinheiro", "cartao", "pix"},
        "pix"
    );
    
    if (formaPagamento == null) return;
    
    pedido.setFormaPagamento(formaPagamento);
    pedido.setItens(carrinho);
    
    // 3. Calcular Total
    BigDecimal total = BigDecimal.ZERO;
    for (ItemPedido item : carrinho) {
        total = total.add(item.getSubtotal());
    }
    pedido.setTotal(total);

    // 4. Salvar no Banco
    boolean sucesso = pedidoDao.salvar(pedido);
    
    if (sucesso) {
        
        // =========================================================
        // NOVO BLOCO: ATUALIZAÇÃO DO ESTOQUE
        // =========================================================
        try {
            for (ItemPedido item : carrinho) {
                int idCardapio = item.getItemCardapio().getId();
                int quantidadePedida = item.getQuantidade();
                
                // Busca o objeto Cardapio mais recente para ter o estoque atual (garante que não está usando dado desatualizado)
                Cardapio itemAtual = cardapioDao.buscarPorId(idCardapio); 

                if (itemAtual != null) {
                    int estoqueAtual = itemAtual.getQuantidadeEstoque();
                    int novoEstoque = estoqueAtual - quantidadePedida;
                    
                    // Medida de segurança: evita que o estoque fique negativo no banco
                    if (novoEstoque < 0) {
                        novoEstoque = 0; 
                    }

                    // Chama o método para reduzir o estoque no BD
                    cardapioDao.atualizarEstoque(idCardapio, novoEstoque);
                }
            }
        } catch (SQLException e) {
            // Se houver erro no estoque, apenas logamos. O pedido principal já foi salvo.
            Logger.getLogger(FazerPedidoController.class.getName()).log(Level.SEVERE, "Erro ao atualizar o estoque após o pedido.", e);
        }
        // =========================================================
        
        JOptionPane.showMessageDialog(tela, "Pedido #" + pedido.getId() + " realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        carrinho.clear();
        atualizarTabelaCarrinho();
        new PainelAluno().setVisible(true);
        tela.dispose();
        // Redirecionar para PainelAluno
        // new View.PainelAluno().setVisible(true); 
    } else {
        JOptionPane.showMessageDialog(tela, "Falha ao registrar o pedido. Verifique o log do banco.", "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
}