package Model;

public class CadastroAlunoModel {
    private String nome;
    private String ra;
    private String email;
    private String senha;

    public CadastroAlunoModel(String nome, String ra, String email, String senha) {
        this.nome = nome;
        this.ra   = ra;
        this.email = email;
        this.senha = senha;
    }

    public String getNome() { return nome; }
    public String getRa()   { return ra; }
    public String getEmail(){ return email; }
    public String getSenha(){ return senha; }

    public boolean isValido() {
        if (nome == null || nome.trim().isEmpty()) return false;
        if (ra == null || ra.trim().isEmpty()) return false;
        if (email == null || email.trim().isEmpty() || !email.contains("@")) return false;
        if (senha == null || senha.length() < 6) return false;
        return true;
    }

    @Override
    public String toString() {
        return "CadastroAlunoModel{nome='" + nome + "', ra='" + ra + "', email='" + email + "', senhaLen=" + (senha==null?0:senha.length()) + "}";
    }
}
