package View;

import javax.swing.*;
import java.awt.*;
import DAO.AlunoDAO;
import Controller.LoginAlunoController;

public class LoginAlunoView extends JFrame {
    private JTextField txtRa;
    private JPasswordField txtSenha;
    private JButton btnEntrar;

    public LoginAlunoView() {
        setTitle("Login - Aluno");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        
        add(new JLabel("RA:"));
        txtRa = new JTextField();
        add(txtRa);

        add(new JLabel("Senha:"));
        txtSenha = new JPasswordField();
        add(txtSenha);

        add(new JLabel()); // Espaço em branco
        btnEntrar = new JButton("Entrar");
        add(btnEntrar);

        // Conecta esta View com o Controller (usa construtor que cria o DAO)
        new LoginAlunoController(this);
    }

    public String getRa() { return txtRa.getText(); }
    public String getSenha() { return new String(txtSenha.getPassword()); }
    public JButton getBtnEntrar() { return btnEntrar; }
}
