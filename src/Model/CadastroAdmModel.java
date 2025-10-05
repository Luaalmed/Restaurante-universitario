/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

public class CadastroAdmModel {
    private String nome;
    private String email;
    private String senha;

   
    public CadastroAdmModel(String nome, String email, String senha) {
        this.nome  = nome;
        this.email = email;
        this.senha = senha;
    }

    // Métodos Getters
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }

    // Método de validação (exemplo)
    public boolean isValido() {
        return nome != null && !nome.isBlank() &&
               email != null && !email.isBlank() &&
               senha != null && senha.length() >= 6;
    }
}
