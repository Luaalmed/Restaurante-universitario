package controller;

import DAO.CardapioDAO;
import model.Cardapio;
import java.math.BigDecimal;
import java.util.List;

public class CardapioController {
    private CardapioDAO dao;

    public CardapioController() {
        dao = new CardapioDAO();
    }

    public boolean cadastrarCardapio(String nome, String descricao, BigDecimal preco, String categoria, boolean disponivel) {
        Cardapio c = new Cardapio();
        c.setNome(nome);
        c.setDescricao(descricao);
        c.setPreco(preco);
        c.setCategoria(categoria);
        c.setDisponivel(disponivel);

        return dao.salvar(c);
    }

   
}
