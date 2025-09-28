package DAO;

import Model.CadastroAdmModel;
import java.sql.*;

public class AdmDAO {

    
    private static final String SCHEMA = "restaurante_universitario";
    private static final String ENUM   = "restaurante_universitario.tipo_usuario_enum";

    // Tabela usuarios deve ter id gerado (serial/identity/uuid) e colunas: nome, email, senha, tipo_usuario
    private static final String INSERT_SQL =
        "INSERT INTO " + SCHEMA + ".usuarios (nome, email, senha, tipo_usuario) " +
        "VALUES (?, ?, ?, 'admin'::" + ENUM + ")";

    private static final String EXISTS_SQL =
        "SELECT 1 FROM " + SCHEMA + ".usuarios WHERE email = ? LIMIT 1";

    private static final String LOGIN_EMAIL_SQL =
        "SELECT 1 FROM " + SCHEMA + ".usuarios " +
        "WHERE email = ? AND senha = ? AND tipo_usuario = 'admin'::" + ENUM;

    public void inserir(CadastroAdmModel m) throws SQLException {
        try (Connection con = DAO.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_SQL)) {
            ps.setString(1, m.getNome().trim());
            ps.setString(2, m.getEmail().trim());
            ps.setString(3, m.getSenha());
            ps.executeUpdate();
        }
    }

    public boolean emailJaExiste(String email) throws SQLException {
        try (Connection con = DAO.getConnection();
             PreparedStatement ps = con.prepareStatement(EXISTS_SQL)) {
            ps.setString(1, email.trim());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean autenticarPorEmailSenha(String email, String senha) throws SQLException {
        try (Connection con = DAO.getConnection();
             PreparedStatement ps = con.prepareStatement(LOGIN_EMAIL_SQL)) {
            ps.setString(1, email.trim());
            ps.setString(2, senha);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}
