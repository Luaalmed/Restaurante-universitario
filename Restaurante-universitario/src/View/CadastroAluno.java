package View;

import javax.swing.*;
import java.awt.*;

public class CadastroAluno extends JPanel {
    private JTextField txtNome, txtRa, txtEmail;
    private JPasswordField txtSenha;
    private JButton btnCadastrar;

    
    
    
    public CadastroAluno() { initComponents(); }

    private void initComponents() {
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("Nome:"));
        txtNome = new JTextField();
        add(txtNome);

        add(new JLabel("RA:"));
        txtRa = new JTextField();
        add(txtRa);

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

    // Getters para o Controller/Main
    public String getNome()   { return txtNome.getText(); }
    public String getRA()     { return txtRa.getText(); }
    public String getEmail()  { return txtEmail.getText(); }
    public String getSenha()  { return new String(txtSenha.getPassword()); }
    public JButton getBtnCadastrar() { return btnCadastrar; }

    public void limpar() {
        txtNome.setText("");
        txtRa.setText("");
        txtEmail.setText("");
        txtSenha.setText("");
    }
}
