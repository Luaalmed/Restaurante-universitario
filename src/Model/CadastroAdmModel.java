/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

public class CadastroAdmModel {
    private String nome;
    private String id;       
    private String email;
    private String senha;

    public CadastroAdmModel() {}

    public CadastroAdmModel(String nome, String id, String email, String senha) {
        this.nome = nome;
        this.id = id;
        this.email = email;
        this.senha = senha;
    }

    public String getNome() { return nome; }
    public String getId() { return id; }                
    public String getEmail() { return email; }
    public String getSenha() { return senha; }

    public void setNome(String nome) { this.nome = nome; }
    public void setId(String id) { this.id = id; }     
    public void setEmail(String email) { this.email = email; }
    public void setSenha(String senha) { this.senha = senha; }

    public boolean isValido() {
        return nome != null && !nome.isBlank()
            && id != null && !id.isBlank()              
            && email != null && email.contains("@")
            && senha != null && senha.length() >= 6;
    }
}

