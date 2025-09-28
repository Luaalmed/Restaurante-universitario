package View;

import javax.swing.*;
import java.awt.*;

public class CadastroAdm extends JPanel {
    private JTextField txtNome, txtEmail;
    private JPasswordField txtSenha;
    private JButton btnCadastrar;

    public CadastroAdm() { initComponents(); }

    private void initComponents() {
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Nome:"));
        txtNome = new JTextField();
        add(txtNome);

        add(new JLabel("Email:"));
        txtEmail = new JTextField();
        add(txtEmail);

        add(new JLabel("Senha:"));
        txtSenha = new JPasswordField();
        add(txtSenha);

        btnCadastrar = new JButton("Cadastrar");
        add(btnCadastrar);

        add(new JLabel()); // espaço
    }

    public String getNome()   { return txtNome.getText(); }
    public String getEmail()  { return txtEmail.getText(); }
    public String getSenha()  { return new String(txtSenha.getPassword()); }
    public JButton getBtnCadastrar() { return btnCadastrar; }

    public void limpar() {
        txtNome.setText("");
        txtEmail.setText("");
        txtSenha.setText("");
    }
}
