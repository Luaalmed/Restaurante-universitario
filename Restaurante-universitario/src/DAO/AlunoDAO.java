package DAO;

import Model.CadastroAlunoModel;
import java.sql.*;

public class AlunoDAO {

    private static final String SCHEMA = "restaurante_universitario";
    private static final String ENUM   = "restaurante_universitario.tipo_usuario_enum";

    // INSERT: somente RA, nome, email, senha e enum "cliente"
    private static final String INSERT_SQL =
        "INSERT INTO " + SCHEMA + ".usuarios " +
        "(ra, nome, email, senha, tipo_usuario) " +
        "VALUES (?, ?, ?, ?, 'cliente'::" + ENUM + ")";

    // Verificação de duplicidade (email OU RA)
    private static final String EXISTS_SQL =
        "SELECT 1 FROM " + SCHEMA + ".usuarios WHERE email = ? OR ra = ? LIMIT 1";

    // Login por RA e senha (texto puro)
    private static final String LOGIN_RA_SQL =
        "SELECT 1 FROM " + SCHEMA + ".usuarios " +
        "WHERE ra = ? AND senha = ? AND tipo_usuario = 'cliente'::" + ENUM;

    // Login por email e senha (texto puro)
    private static final String LOGIN_EMAIL_SQL =
        "SELECT 1 FROM " + SCHEMA + ".usuarios " +
        "WHERE email = ? AND senha = ? AND tipo_usuario = 'cliente'::" + ENUM;
    
    private static final String LOGIN_ADMIN_SQL =
    "SELECT 1 FROM restaurante_universitario.usuarios " +
    "WHERE email = ? AND senha = ? AND tipo_usuario = 'admin'::restaurante_universitario.tipo_usuario_enum";

public boolean autenticarAdminPorEmailSenha(String email, String senha) throws SQLException {
    try (Connection con = DAO.getConnection();
         PreparedStatement ps = con.prepareStatement(LOGIN_ADMIN_SQL)) {
        ps.setString(1, email.trim());
        ps.setString(2, senha);
        try (ResultSet rs = ps.executeQuery()) {
            return rs.next();
        }
    }
}

    public void inserir(CadastroAlunoModel m) throws SQLException {
        try (Connection con = DAO.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_SQL)) {
            ps.setString(1, m.getRa());      // RA
            ps.setString(2, m.getNome());    // Nome
            ps.setString(3, m.getEmail());   // Email
            ps.setString(4, m.getSenha());   // Senha (texto puro)
            ps.executeUpdate();              // 1 linha inserida se OK
        }
    }

    public boolean emailOuRaJaExiste(String email, String ra) throws SQLException {
        try (Connection con = DAO.getConnection();
             PreparedStatement ps = con.prepareStatement(EXISTS_SQL)) {
            ps.setString(1, email);
            ps.setString(2, ra);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean autenticarPorRaSenha(String ra, String senha) throws SQLException {
        try (Connection con = DAO.getConnection();
             PreparedStatement ps = con.prepareStatement(LOGIN_RA_SQL)) {
            ps.setString(1, ra);
            ps.setString(2, senha);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean autenticarPorEmailSenha(String email, String senha) throws SQLException {
        try (Connection con = DAO.getConnection();
             PreparedStatement ps = con.prepareStatement(LOGIN_EMAIL_SQL)) {
            ps.setString(1, email);
            ps.setString(2, senha);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}
