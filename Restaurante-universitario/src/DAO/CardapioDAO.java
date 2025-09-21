package DAO;

import model.Cardapio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardapioDAO {

    private Connection connection;

    public CardapioDAO() {
        connection = Conexao.getConnection();
    }

    // Salvar 
    public boolean salvar(Cardapio c) {
        String sql = "INSERT INTO restaurante_universitario.cardapio (nome, descricao, preco, categoria, disponivel) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, c.getNome());
            ps.setString(2, c.getDescricao());
            ps.setBigDecimal(3, c.getPreco());
            ps.setString(4, c.getCategoria());
            ps.setBoolean(5, c.isDisponivel());

            int r = ps.executeUpdate();
            return r > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
  

 
}
