// CardapioController.java
package Controller;

import DAO.CardapioDAO;
import view.CadastrarCardapio;
import model.Cardapio;
import javax.swing.JOptionPane;

public class CardapioController {

    private final CadastrarCardapio tela;
    private final CardapioDAO dao;

    public CardapioController(CadastrarCardapio tela) {
        this.tela = tela;
        this.dao   = new CardapioDAO();
        registrarEventos();
    }

    private void registrarEventos() {
        tela.getBtnSalvar().addActionListener(evt -> salvar());
        tela.getBtnCancelar().addActionListener(evt -> cancelar());
        tela.getBtnVoltar().addActionListener(evt -> voltar());
    }

    private void salvar() {
        try {
            String nome       = tela.getTxtNome().getText().trim();
            String descricao  = tela.getTxtDescricao().getText().trim();
            String precoText  = tela.getTxtPreco().getText().trim();
            String categoria  = (String) tela.getCbCategoria().getSelectedItem();
            boolean disponivel= tela.getChkDisponivel().isSelected();

            if (nome.isEmpty() || precoText.isEmpty()) {
                JOptionPane.showMessageDialog(tela, "Nome e Preço são obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Cardapio c = new Cardapio();
            c.setNome(nome);
            c.setDescricao(descricao);
            c.setPreco(new java.math.BigDecimal(precoText));
            c.setCategoria(categoria);
            c.setDisponivel(disponivel);

            boolean ok = dao.salvar(c);
            JOptionPane.showMessageDialog(
                tela,
                ok ? "Salvo com sucesso!" : "Falha ao salvar.",
                ok ? "Sucesso" : "Erro",
                ok ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE
            );
            if (ok) voltar();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(tela, "Preço inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelar() {
        if (JOptionPane.showConfirmDialog(tela, "Descartar alterações?", "Confirmar", JOptionPane.YES_NO_OPTION)
            == JOptionPane.YES_OPTION) {
            tela.getTxtNome().setText("");
            tela.getTxtDescricao().setText("");
            tela.getTxtPreco().setText("");
            tela.getCbCategoria().setSelectedIndex(0);
            tela.getChkDisponivel().setSelected(false);
        }
    }

    private void voltar() {
        tela.dispose();
        new View.PainelAdmin().setVisible(true);
    }
}
