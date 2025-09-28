package DAO;

import Model.CadastroAlunoModel;
import java.sql.*;

public class AlunoDAO {

    private static final String SCHEMA = "restaurante_universitario";
    private static final String ENUM   = "restaurante_universitario.tipo_usuario_enum";

   private static final String INSERT_SQL =
    "INSERT INTO " + SCHEMA + ".usuarios (nome, email, ra, senha, tipo_usuario) " +
    "VALUES (?, ?, ?, ?, 'cliente'::" + ENUM + ")";

    private static final String EXISTS_SQL =
        "SELECT 1 FROM " + SCHEMA + ".usuarios WHERE email = ? OR ra = ? LIMIT 1";

    public void inserir(CadastroAlunoModel m) throws SQLException {
        try (Connection con = DAO.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_SQL)) {
            ps.setString(1, m.getNome());
            ps.setString(2, m.getEmail());
            ps.setString(3, m.getRa());
            ps.setString(4, m.getSenha());
            ps.executeUpdate();
        }
    }

    public boolean emailOuRaJaExiste(String email, String ra) throws SQLException {
        try (Connection con = DAO.getConnection();
             PreparedStatement ps = con.prepareStatement(EXISTS_SQL)) {
            ps.setString(1, email);
            ps.setString(2, ra);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        }
    }
}
