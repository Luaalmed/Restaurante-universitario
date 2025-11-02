package Model;

import java.util.Objects;


public class LoginAdminModel {
    private final String email;
    private final String senha;

    private LoginAdminModel(String email, String senha) {
        this.email = email == null ? "" : email.trim();
        this.senha = senha == null ? "" : senha;
    }

    // fábrica (centraliza limpeza)
    public static LoginAdminModel of(String email, String senha) {
        return new LoginAdminModel(email, senha);
    }

    public String getEmail() { return email; }
    public String getSenha() { return senha; }

    // validações simples — ajuste às suas regras
    public boolean emailValido() {
        return !email.isBlank() && email.contains("@"); // regra básica
    }

    public boolean senhaValida() {
        return senha.length() >= 4; // exemplo mínimo
    }

    public boolean valido() {
        return emailValido() && senhaValida();
    }

    @Override public String toString() {
        return "LoginAdminModel{email='" + email + "'}";
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoginAdminModel)) return false;
        LoginAdminModel that = (LoginAdminModel) o;
        return Objects.equals(email, that.email);
    }

   @Override
public int hashCode() {
    return email.hashCode();
}
}

