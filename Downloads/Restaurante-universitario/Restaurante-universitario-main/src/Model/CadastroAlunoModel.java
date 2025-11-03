/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Caiop
 */
public class CadastroAlunoModel {
    private String Nome;
    private String Ra;
    private String Email;
    private String Id;
    private String Senha;
    
    public CadastroAlunoModel(){}
    
    public CadastroAlunoModel(String Nome, String Ra, String Email, String Id, String Senha){
        this.Nome = Nome;
        this.Ra = Ra;
        this.Email = Email;
        this.Id = Id;
        this.Senha = Senha;
        
    }
    public String getNome(){return Nome;}
    public String getRa(){return Ra;}
    public String getEmail(){return Email;}
    public String getId(){return Id;}
    public String getSenha(){return Senha;}
    
    public void setNome(String Nome) {this.Nome = Nome;}
    public void setRa(String Ra) {this.Ra = Ra;}
    public void setEmail(String Email) {this.Email = Email;}
    public void setId(String Id) {this.Id = Id;}
    public void setSenha(String Senha) {this.Senha = Senha;}
    
    public boolean isValido(){
        return Nome != null && !Nome.isBlank()
                && Ra != null && !Ra.isBlank()
                && Email != null && !Email.isBlank()
                && Id != null && !Id.isBlank()
                && Senha != null && !Senha.isBlank();
    }

}
