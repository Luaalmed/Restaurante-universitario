/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;


public class CadastroAdmModel {
    private final String nome;
    private final String email;
    private final String senha;

    public CadastroAdmModel(String nome, String email, String senha) {
        this.nome  = nome == null ? "" : nome;
        this.email = email == null ? "" : email;
        this.senha = senha == null ? "" : senha;
    }

    
    
    public String getNome()  { return nome; }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }

    public boolean isValido() {
        return nome.trim().length() >= 1 &&
               email.trim().length() >= 3 &&
               senha.length() >= 6;
    }
}


