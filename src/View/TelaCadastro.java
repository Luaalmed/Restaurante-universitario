/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import javax.swing.*;
import java.awt.*;
import controller.TelaCadastroController;

public class TelaCadastro extends JPanel {
    private final JButton cadalunobtm = new JButton("Cadastro Aluno");
    private final JButton cadadmbtm  = new JButton("Cadastro ADM");

    public TelaCadastro(TelaCadastroController controller) {
        setLayout(new GridBagLayout());
        var c = new GridBagConstraints();
        c.insets = new Insets(10,10,10,10);
        c.gridx = 0; c.gridy = 0;
        add(new JLabel("Escolha o tipo de cadastro:"), c);
        c.gridy = 1;
        add(cadalunobtm, c);
        c.gridy = 2;
        add(cadadmbtm, c);

        cadalunobtm.addActionListener(e -> controller.irParaAluno());
        cadadmbtm.addActionListener(e -> controller.irParaAdm());
    }

    public JButton getCadalunobtm() { return cadalunobtm; }
    public JButton getCadadmbtm() { return cadadmbtm; }
}
