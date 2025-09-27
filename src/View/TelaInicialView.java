/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

/**
 *
 * @author ester
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Janela principal para escolher entre Admin e Aluno
public class TelaInicialView extends JFrame {

    public TelaInicialView() {
        setTitle("Bem-vindo!");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 40));

        JButton btnAdmin = new JButton("Sou Administrador");
        JButton btnAluno = new JButton("Sou Aluno");

        // Ação do botão Admin
        btnAdmin.addActionListener(e -> {
            dispose(); // Fecha a tela inicial
            new LoginAdminView().setVisible(true); // Abre a tela de login do ADM
        });

        // Ação do botão Aluno
        btnAluno.addActionListener(e -> {
            dispose(); // Fecha a tela inicial
            new LoginAlunoView().setVisible(true); // Abre a tela de login do Aluno
        });

        add(btnAdmin);
        add(btnAluno);
    }
}
