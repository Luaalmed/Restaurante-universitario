package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/Restaurante-universitario", 
                "postgres", "1234"
            );
        } catch (SQLException e) {
            System.out.println("Erro na conexão com o banco: " + e.getMessage());
            return null;
        }
    }
}
