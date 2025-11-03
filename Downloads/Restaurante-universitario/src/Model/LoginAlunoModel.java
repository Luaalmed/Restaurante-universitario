package Model;

import java.util.Objects;

public class LoginAlunoModel {
    private final String Ra;
    private final String Senha;

    private LoginAlunoModel(String ra, String senha) {
        this.Ra   = ra == null ? "" : ra.trim();
        this.Senha = senha == null ? "" : senha;
    }

    // Fábrica para centralizar limpeza
    public static LoginAlunoModel of(String ra, String senha) {
        return new LoginAlunoModel(ra, senha);
    }

    public String getRa()    { return Ra; }
    public String getSenha() { return Senha; }

    // Validações simples (ajuste às suas regras)
    public boolean raValido() {
        // Ex.: RA não vazio; se tiver padrão numérico, valide com regex: ra.matches("\\d{5,12}")
        return !Ra.isBlank();
    }

    public boolean senhaValida() {
        // Ex.: pelo menos 4 chars (ajuste conforme sua regra)
        return Senha != null && Senha.length() >= 4;
    }

    public boolean valido() {
        return raValido() && senhaValida();
    }

    @Override public String toString() {
        return "LoginAlunoModel{ra='" + Ra + "'}";
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoginAlunoModel)) return false;
        LoginAlunoModel that = (LoginAlunoModel) o;
        return Objects.equals(Ra, that.Ra);
    }

    @Override public int hashCode() {
        return Objects.hash(Ra);
    }
}
