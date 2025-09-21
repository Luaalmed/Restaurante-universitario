/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import javax.swing.*;
import java.awt.*;
import controller.CadastroAdmController;

/**
 * Tela de Cadastro de ADM
 */
public class CadastroAdm extends JPanel {

    // Campos de entrada
    private final JTextField txtNome  = new JTextField(20);
    private final JTextField txtEmail = new JTextField(20);
    private final JTextField txtId    = new JTextField(15);
    private final JPasswordField txtSenha = new JPasswordField(20);

    // Botão solicitado
    private final JButton Cadastraradmbtm = new JButton("Cadastrar ADM");

    // Referência ao controller (importante para acionar a ação)
    private final CadastroAdmController controller;

    public CadastroAdm(CadastroAdmController controller) {
        this.controller = controller; // guarda o controller

        setLayout(new BorderLayout());

        // ----- Formulário (mesmo layout que você vinha usando) -----
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.anchor = GridBagConstraints.WEST;

        int y = 0;
        c.gridx = 0; c.gridy = y; form.add(new JLabel("Nome:"), c);
        c.gridx = 1;             form.add(txtNome, c); y++;

        c.gridx = 0; c.gridy = y; form.add(new JLabel("Email:"), c);
        c.gridx = 1;              form.add(txtEmail, c); y++;

        c.gridx = 0; c.gridy = y; form.add(new JLabel("ID:"), c);
        c.gridx = 1;              form.add(txtId, c); y++;

        c.gridx = 0; c.gridy = y; form.add(new JLabel("Senha:"), c);
        c.gridx = 1;              form.add(txtSenha, c); y++;

        // ----- Botões -----
        JPanel buttons = new JPanel();
        buttons.add(Cadastraradmbtm);

        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        // Listener correto: chama o método da INSTÂNCIA do controller
        Cadastraradmbtm.addActionListener(e -> {
            if (this.controller != null) {
                this.controller.cadastrarAdm();
            }
        });
    }

    // ======= Getters usados pelo controller =======
    public String getNome()  { return txtNome.getText().trim(); }
    public String getEmail() { return txtEmail.getText().trim(); }

    // IMPORTANTE: o controller chama view.getId(), então fornecemos esse getter
    public String getId()    { return txtId.getText().trim(); }

    public String getSenha() { return new String(txtSenha.getPassword()); }

    public JButton getCadastraradmbtm() { return Cadastraradmbtm; }

    // Opcional: limpar formulário após sucesso
    public void limpar() {
        txtNome.setText("");
        txtEmail.setText("");
        txtId.setText("");
        txtSenha.setText("");
    }

}
