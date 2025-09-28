package View;

import javax.swing.*;
import java.awt.*;
import Controller.LoginAdminController;

public class LoginAdminView extends JFrame {
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JButton btnEntrar;

    public LoginAdminView() {
        setTitle("Login - Admin");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Email:"));
        txtEmail = new JTextField();
        add(txtEmail);

        add(new JLabel("Senha:"));
        txtSenha = new JPasswordField();
        add(txtSenha);

        add(new JLabel());
        btnEntrar = new JButton("Entrar");
        add(btnEntrar);

        new LoginAdminController(this); // injeta DAO automaticamente
    }

    public String getEmail() { return txtEmail.getText(); }
    public String getSenha() { return new String(txtSenha.getPassword()); }
    public JButton getBtnEntrar() { return btnEntrar; }

    public void limpar() {
        txtEmail.setText("");
        txtSenha.setText("");
    }
}
