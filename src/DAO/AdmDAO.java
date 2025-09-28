package DAO;

import Model.CadastroAdmModel;
import java.sql.*;

public class AdmDAO {

    private static final String SCHEMA = "restaurante_universitario";
    private static final String ENUM   = "restaurante_universitario.tipo_usuario_enum";

    // SQL modificado para não usar criptografia
    private static final String INSERT_SQL =
        "INSERT INTO " + SCHEMA + ".usuarios (nome, email, ra, senha, tipo_usuario) " +
        "VALUES (?, ?, ?, ?, 'admin'::" + ENUM + ")";

    private static final String EXISTS_SQL =
        "SELECT 1 FROM " + SCHEMA + ".usuarios WHERE email = ? OR ra = ? LIMIT 1";

    public void inserir(CadastroAdmModel m) throws SQLException {
        try (Connection con = DAO.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_SQL)) {
            ps.setString(1, m.getNome());
            ps.setString(2, m.getEmail());
            ps.setString(3, m.getId());   // ID do ADM salvo em 'ra'
            ps.setString(4, m.getSenha());
            ps.executeUpdate();
        }
    }

    public boolean emailOuIdJaExiste(String email, String id) throws SQLException {
        try (Connection con = DAO.getConnection();
             PreparedStatement ps = con.prepareStatement(EXISTS_SQL)) {
            ps.setString(1, email);
            ps.setString(2, id); // compara com 'ra'
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        }
    }
    
    public boolean autenticarAdmin(String email, String senha) throws SQLException {
    final String SQL =
        "SELECT 1 " +
        "FROM restaurante_universitario.usuarios " +
        "WHERE email = ? AND senha = ? " +
        "  AND tipo_usuario = 'admin'::restaurante_universitario.tipo_usuario_enum " +
        "LIMIT 1";

    try (Connection con = DAO.getConnection();
         PreparedStatement ps = con.prepareStatement(SQL)) {
        ps.setString(1, email);
        ps.setString(2, senha);
        try (ResultSet rs = ps.executeQuery()) {
            return rs.next();
        }
    }
}

    
}