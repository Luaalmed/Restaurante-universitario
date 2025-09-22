/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package Controller;

import View.LoginAdminView;
import javax.swing.JOptionPane;
import View.PainelAdminView;

public class LoginAdminController {
    private LoginAdminView view;

    public LoginAdminController(LoginAdminView view) {
        this.view = view;
        this.view.getBtnEntrar().addActionListener(e -> realizarLogin());
    } // A chave que estava aqui foi MOVIDA para o final

    public void realizarLogin() {
        String id = view.getId();
        String senha = view.getSenha();

        // Lógica de login SUPER SIMPLES (dados fixos)
        if ("admin".equals(id) && "123".equals(senha)) {
            JOptionPane.showMessageDialog(view, "Login de ADM realizado com sucesso!");
            view.dispose(); // Fecha a tela de login
            new PainelAdminView().setVisible(true); // Abre o painel do ADM
        } else {
            JOptionPane.showMessageDialog(view, "ID ou Senha incorretos!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

} 