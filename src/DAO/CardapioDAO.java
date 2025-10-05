package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import model.Cardapio;

public class CardapioDAO {

    public boolean salvar(Cardapio cardapio) {
              String sql = "INSERT INTO cardapio (nome, descricao, preco, categoria, disponivel, data_criacao, data_atualizacao) "
               + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = DAO.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, cardapio.getNome());
                stmt.setString(2, cardapio.getDescricao());
                stmt.setBigDecimal(3, cardapio.getPreco());
                stmt.setObject(4, cardapio.getCategoria(), java.sql.Types.OTHER);
                stmt.setBoolean(5, cardapio.isDisponivel());
                stmt.setTimestamp(6, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
                stmt.setTimestamp(7, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));

                stmt.executeUpdate();
                return true; // ✅ agora retorna true se deu certo

            } catch (SQLException e) {
                e.printStackTrace();
                return false; // ❌ retorna false se deu erro
            }
    }
}
