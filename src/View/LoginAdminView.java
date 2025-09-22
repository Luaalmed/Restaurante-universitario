// GARANTA QUE ESTE ARQUIVO ESTEJA EM /View/LoginAdminView.java
package View;

import javax.swing.*;
import java.awt.*;

public class LoginAdminView extends JFrame { // Precisa ser um JFrame para ter o .dispose()
    private JTextField txtId;
    private JPasswordField txtSenha;
    private JButton btnEntrar;

    public LoginAdminView() {
        setTitle("Login - Administrador");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("ID:"));
        txtId = new JTextField();
        add(txtId);

        add(new JLabel("Senha:"));
        txtSenha = new JPasswordField();
        add(txtSenha);

        add(new JLabel()); // Espaço em branco
        btnEntrar = new JButton("Entrar");
        add(btnEntrar);

        // A conexão com o controller é feita separadamente agora
    }

    // --- MÉTODOS OBRIGATÓRIOS PARA O CONTROLLER FUNCIONAR ---
    public String getId() {
        return txtId.getText();
    }

    public String getSenha() {
        return new String(txtSenha.getPassword());
    }

    public JButton getBtnEntrar() {
        return btnEntrar;
    }
}