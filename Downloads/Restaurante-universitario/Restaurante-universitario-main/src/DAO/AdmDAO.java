package DAO;

import Model.CadastroAdmModel;
import java.sql.*;

public class AdmDAO {

    
    private static final String SCHEMA = "restaurante_universitario";
    private static final String ENUM   = "restaurante_universitario.tipo_usuario_enum";

    // Insere admin em usuarios (usando email como RA se não houver RA para ADM)
    private static final String INSERT_SQL =
        "INSERT INTO " + SCHEMA + ".usuarios (nome, email, ra, senha, tipo_usuario) " +
        "VALUES (?, ?, ?, ?, 'admin'::" + ENUM + ")";

    // Verifica existência por email ou ra
    private static final String EXISTS_SQL =
        "SELECT 1 FROM " + SCHEMA + ".usuarios WHERE email = ? OR ra = ? LIMIT 1";

    // Autenticação simples (sem hash)
    private static final String AUTH_SQL =
        "SELECT 1 FROM " + SCHEMA + ".usuarios " +
        "WHERE email = ? AND senha = ? " +
        "AND tipo_usuario = 'admin'::" + ENUM + " LIMIT 1";

    public void inserir(CadastroAdmModel m) throws SQLException {
        try (Connection con = Conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_SQL)) {

            ps.setString(1, m.getNome());
            ps.setString(2, m.getEmail());

            // Se não usa RA/ID para admin, pode salvar email também no campo ra
            String ra = m.getEmail(); // ou m.getId() se houver campo ID/RA real
            ps.setString(3, ra);

            ps.setString(4, m.getSenha());
            ps.executeUpdate();
        }
    }

    public boolean emailOuIdJaExiste(String email, String id) throws SQLException {
        try (Connection con = Conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(EXISTS_SQL)) {

            ps.setString(1, email);
            ps.setString(2, id != null ? id : email); // se não tiver id, compara com email
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean emailJaExiste(String email) throws SQLException {
        try (Connection con = Conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(
                 "SELECT 1 FROM " + SCHEMA + ".usuarios WHERE email = ? LIMIT 1")) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean autenticarAdmin(String email, String senha) throws SQLException {
        try (Connection con = Conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(AUTH_SQL)) {

            ps.setString(1, email);
            ps.setString(2, senha);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}
