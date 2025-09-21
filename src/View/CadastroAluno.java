/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import javax.swing.*;
import java.awt.*;
import controller.CadastroAlunoController;

public class CadastroAluno extends JPanel {
    private final JTextField txtNome  = new JTextField(20);
    private final JTextField txtId    = new JTextField(20);
    private final JTextField txtRA    = new JTextField(15);
    private final JTextField txtEmail = new JTextField(20);
    private final JPasswordField txtSenha = new JPasswordField(20);

    // Botão solicitado
    private final JButton Cadastraralunobtm = new JButton("Cadastrar Aluno");

    // >>> referência ao controller (igual ao feito em CadastroAdm)
    private final CadastroAlunoController controller;

    public CadastroAluno(CadastroAlunoController controller) {
        this.controller = controller; // guarda o controller

        setLayout(new BorderLayout());
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,6,6,6);
        c.anchor = GridBagConstraints.WEST;

        int y = 0;
        c.gridx=0; c.gridy=y; form.add(new JLabel("Nome:"), c);
        c.gridx=1;            form.add(txtNome, c); y++;

        c.gridx=0; c.gridy=y; form.add(new JLabel("Id:"), c);
        c.gridx=1;            form.add(txtId, c); y++;

        c.gridx=0; c.gridy=y; form.add(new JLabel("RA:"), c);
        c.gridx=1;            form.add(txtRA, c); y++;

        c.gridx=0; c.gridy=y; form.add(new JLabel("Email:"), c);
        c.gridx=1;            form.add(txtEmail, c); y++;

        c.gridx=0; c.gridy=y; form.add(new JLabel("Senha:"), c);
        c.gridx=1;            form.add(txtSenha, c); y++;

        JPanel buttons = new JPanel();
        buttons.add(Cadastraralunobtm);

        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        // >>> correção do listener (antes chamava "Controller.CadastroAlunoController()")
        Cadastraralunobtm.addActionListener(e -> {
            if (this.controller != null) {
                this.controller.cadastrarAluno();
            }
        });
    }

    // Getters usados pelo controller
    public String getNome()  { return txtNome.getText().trim(); }
    public String getId()    { return txtId.getText().trim(); }
    public String getRA()    { return txtRA.getText().trim(); }
    public String getEmail() { return txtEmail.getText().trim(); }
    public String getSenha() { return new String(txtSenha.getPassword()); }

    public JButton getCadastraralunobtm() { return Cadastraralunobtm; }

    // opcional, limpar após sucesso
    public void limpar() {
        txtNome.setText("");
        txtId.setText("");
        txtRA.setText("");
        txtEmail.setText("");
        txtSenha.setText("");
    }
}
