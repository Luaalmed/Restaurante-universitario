/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Caiop
 */
public class TelaCadastroModel {
    public enum Tela { HOME, ALUNO, ADM }
    private Tela telaAtual = Tela.HOME;

    public Tela getTelaAtual() { return telaAtual; }
    public void setTelaAtual(Tela telaAtual) { this.telaAtual = telaAtual; }
}
