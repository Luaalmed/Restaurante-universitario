package DAO;

// Importe o modelo para o cadastro de aluno
import Model.CadastroAlunoModel; 
import Model.UsuarioInfo;
// Importe o modelo que criamos para armazenar o ID e Tipo de Usuário após o login
import java.sql.*;

public class AlunoDAO {

    // Constantes do Schema e ENUM para segurança e clareza
    private static final String SCHEMA = "restaurante_universitario";
    private static final String ENUM   = SCHEMA + ".tipo_usuario_enum";

    // SQL para Inserção (Cadastro)
    private static final String INSERT_SQL =
    "INSERT INTO " + SCHEMA + ".usuarios (nome, email, ra, senha, tipo_usuario) " +
    "VALUES (?, ?, ?, ?, 'cliente'::" + ENUM + ")";

    // SQL para Verificar Existência (Cadastro)
    private static final String EXISTS_SQL =
        "SELECT 1 FROM " + SCHEMA + ".usuarios WHERE email = ? OR ra = ? LIMIT 1";
    
    // SQL para Autenticação (Login) - Retorna ID e Tipo de Usuário
    private static final String LOGIN_SQL =
        "SELECT id, tipo_usuario " + 
        "FROM " + SCHEMA + ".usuarios " +
        "WHERE ra = ? AND senha = ? " +
        "  AND tipo_usuario = 'cliente'::" + ENUM + " " +
        "LIMIT 1";


    // --- MÉTODOS DE CADASTRO ---
    
    public void inserir(CadastroAlunoModel m) throws SQLException {
        try (Connection con = Conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_SQL)) {
            ps.setString(1, m.getNome());
            ps.setString(2, m.getEmail());
            ps.setString(3, m.getRa());
            ps.setString(4, m.getSenha());
            ps.executeUpdate();
        }
    }

    public boolean emailOuRaJaExiste(String email, String ra) throws SQLException {
        try (Connection con = Conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(EXISTS_SQL)) {
            ps.setString(1, email);
            ps.setString(2, ra);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        }
    }
    
    
    // --- NOVO MÉTODO DE AUTENTICAÇÃO ---
    
    /**
     * Autentica o aluno e retorna o ID e o tipo de usuário para iniciar a sessão.
     * @param ra O Registro Acadêmico do aluno.
     * @param senha A senha (não criptografada) do aluno.
     * @return Um objeto UsuarioInfo se a autenticação for bem-sucedida, ou null.
     */
    public UsuarioInfo autenticarAluno(String ra, String senha) throws SQLException {
        try (Connection con = Conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(LOGIN_SQL)) {
            
            ps.setString(1, ra);
            ps.setString(2, senha);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String tipoUsuario = rs.getString("tipo_usuario");
                    return new UsuarioInfo(id, tipoUsuario); 
                }
                return null; // Login inválido
            }
        }
    }
}