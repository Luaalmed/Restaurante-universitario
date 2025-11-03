package controller;

import DAO.CardapioDAO;
import javax.swing.DefaultListModel;
import java.sql.SQLException;
import java.util.List;

public class CardapioController {

    private final View.Cardapio view;  // JFrame gerado pelo Form
    private final CardapioDAO dao;

    public CardapioController(View.Cardapio view) {
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
        DefaultListModel<String> bebidasModel = new DefaultListModel<>();
        DefaultListModel<String> pratosModel  = new DefaultListModel<>();
        DefaultListModel<String> lanchesModel = new DefaultListModel<>();

        try {
            // Busca apenas itens disponíveis
            List<model.Cardapio> itens = dao.buscarTodosDisponiveis();

            for (model.Cardapio c : itens) {
                String cat = c.getCategoria() == null ? "" : c.getCategoria().trim().toUpperCase();
                // Exibe: Nome — R$ preço
                String linha = String.format("%s — R$ %s", c.getNome(), c.getPreco());

                if (cat.equals("BEBIDA") || cat.equals("BEBIDAS") || cat.contains("BEBID")) {
                    bebidasModel.addElement(linha);
                } else if (cat.equals("PRATO") || cat.equals("PRATOS") || cat.contains("PRATO")) {
                    pratosModel.addElement(linha);
                } else if (cat.equals("LANCHE") || cat.equals("LANCHES") || cat.contains("LANCH")) {
                    lanchesModel.addElement(linha);
                } else {
                    // Caso venha outra categoria, joga em pratos por padrão
                    pratosModel.addElement(linha);
                }
            }

        } catch (SQLException ex) {
            bebidasModel.addElement("Erro ao carregar do banco.");
            pratosModel.addElement("Erro ao carregar do banco.");
            lanchesModel.addElement("Erro ao carregar do banco.");
            ex.printStackTrace();
        }

        view.getListbebidas().setModel(bebidasModel);
        view.getListpratos().setModel(pratosModel);
        view.getListlanches().setModel(lanchesModel);
    }
}
