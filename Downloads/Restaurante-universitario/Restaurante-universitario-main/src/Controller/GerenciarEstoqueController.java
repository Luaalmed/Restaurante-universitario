package Controller;

import DAO.CardapioDAO;
import View.TelaEstoque;
import model.Cardapio;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class GerenciarEstoqueController {
    private final TelaEstoque view;
    private final CardapioDAO dao = new CardapioDAO();

    public GerenciarEstoqueController(TelaEstoque view) {
        this.view = view;
   //     adicionarListeners();
        carregarTabela(); 
    }

   /* private void adicionarListeners() {
        view.getBtn_pesquisar().addActionListener(e -> pesquisar());
        view.getBtn_adicionarmaisestoque().addActionListener(e -> alterarEstoque(1));
        view.getBtn_remover().addActionListener(e -> alterarEstoque(-1));
        view.getBtn_salvar().addActionListener(e -> salvarAlteracoes());
        view.getBtn_voltar().addActionListener(e -> {
            view.dispose();
            new View.PainelAdmin().setVisible(true);
        });
    }
*/
    private void carregarTabela() {
        try {
            List<Cardapio> lista = dao.listarTodos();
            preencherTabela(lista);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Erro ao carregar estoque: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void preencherTabela(List<Cardapio> lista) {
        DefaultTableModel model = (DefaultTableModel) view.getTbl_estoque().getModel();
        model.setRowCount(0);
        for (Cardapio c : lista) {
            model.addRow(new Object[]{
                c.getNome(),
                c.getCategoria(),
                c.getPreco(),
                c.getQuantidadeEstoque(), 
                c.getId()                
            });
        }
    }

    public void pesquisar() {
        String termo = view.getTxtfield_pesquisar().getText().trim(); 
    try {
        List<Cardapio> lista = termo.isEmpty() ? dao.listarTodos() : dao.buscarPorNomeOuCategoria(termo);
        preencherTabela(lista);
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(view, "Erro na pesquisa: " + e.getMessage());
        e.printStackTrace();
    }
    }

    public void alterarEstoque(int delta) {
        int linha = view.getTbl_estoque().getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(view, "Selecione um item na tabela!");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) view.getTbl_estoque().getModel();

     
        int estoqueAtual;
        try {
            Object val = model.getValueAt(linha, 3);
            estoqueAtual = Integer.parseInt(val.toString());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Valor de estoque inválido nesta linha.");
            return;
        }

        int novoEstoque = estoqueAtual + delta;
        if (novoEstoque < 0) novoEstoque = 0;

        int id;
        try {
            id = Integer.parseInt(model.getValueAt(linha, 4).toString());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "ID inválido nesta linha.");
            return;
        }

        try {
            dao.atualizarEstoque(id, novoEstoque);
            model.setValueAt(novoEstoque, linha, 3); 
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Erro ao atualizar estoque: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void salvarAlteracoes() {
    try {
        DefaultTableModel model = (DefaultTableModel) view.getTbl_estoque().getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            int id = Integer.parseInt(model.getValueAt(i, 4).toString());   
            int novaQtd = Integer.parseInt(model.getValueAt(i, 3).toString());
            dao.atualizarEstoque(id, novaQtd); 
        }
        JOptionPane.showMessageDialog(view, "Estoque atualizado com sucesso!");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(view, "Erro ao salvar alterações: " + e.getMessage());
        e.printStackTrace();
    }
}
}
