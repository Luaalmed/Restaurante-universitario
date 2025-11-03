package controller;

import DAO.CardapioDAO;
import model.Cardapio;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CardapioViewController {

    private final View.Cardapio view;
    private final CardapioDAO dao;
    private final NumberFormat brl = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public CardapioViewController(View.Cardapio view) {
        this.view = view;
        this.dao  = new CardapioDAO();

        registrarEventos();
        carregarListas();
    }

    private void registrarEventos() {
        view.getBtnhome().addActionListener(e -> {
            view.dispose();
            new View.TelaInicial().setVisible(true);
        });
    }

    private void carregarListas() {
        try {
            // 1) limpa models (garante que não fiquem “Item 1..5”)
            DefaultListModel<String> mdlBebidas = new DefaultListModel<>();
            DefaultListModel<String> mdlLanches = new DefaultListModel<>();
            DefaultListModel<String> mdlPratos  = new DefaultListModel<>();
            view.getListbebidas().setModel(mdlBebidas);
            view.getListlanches().setModel(mdlLanches);
            view.getListpratos().setModel(mdlPratos);

            // 2) busca no banco
            List<Cardapio> itens = dao.buscarParaCardapio();

            // 3) distribui
            for (Cardapio c : itens) {
                String cat = normalizarCategoria(c.getCategoria());
                String linha = formatarLinha(c);
                if (cat.equals("BEBIDA") || cat.equals("BEBIDAS")) {
                    mdlBebidas.addElement(linha);
                } else if (cat.equals("LANCHE") || cat.equals("LANCHES")) {
                    mdlLanches.addElement(linha);
                } else if (cat.equals("PRATO") || cat.equals("PRATOS")) {
                    mdlPratos.addElement(linha);
                }
            }

            // 4) diagnóstico se tudo vazio (agora o alerta aparece de verdade)
            if (mdlBebidas.isEmpty() && mdlLanches.isEmpty() && mdlPratos.isEmpty()) {
                String diagTabela = dao.diagnosticoCardapio();
                String diagEnv    = dao.diagnosticoAmbiente();
                JOptionPane.showMessageDialog(view,
                        "Nenhum item exibido no Cardápio.\n\n" + diagEnv + "\n\n" + diagTabela
                        + "\nSugestões:\n"
                        + "• Confira se há registros na tabela e se o schema é o certo.\n"
                        + "• Veja os valores exatos de 'categoria' retornados acima.",
                        "Cardápio vazio",
                        JOptionPane.WARNING_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view,
                "Erro ao carregar o cardápio:\n" + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private String formatarLinha(Cardapio c) {
        String preco = (c.getPreco() != null) ? brl.format(c.getPreco()) : "R$ 0,00";
        return c.getNome() + " — " + preco;
    }

    private String normalizarCategoria(String categoria) {
        if (categoria == null) return "";
        return categoria.trim().toUpperCase()
                .replace("Á","A").replace("Â","A").replace("Ã","A")
                .replace("É","E").replace("Ê","E")
                .replace("Í","I")
                .replace("Ó","O").replace("Ô","O").replace("Õ","O")
                .replace("Ú","U")
                .replace("Ç","C");
    }
}
