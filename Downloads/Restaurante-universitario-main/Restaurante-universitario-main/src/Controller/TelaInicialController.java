package controller;

import View.TelaInicial;
import View.TelaLoginAluno;
import View.TelaLoginAdmin;
import View.TelaInicialCadastro;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaInicialController {

    private TelaInicial view;

    public TelaInicialController(TelaInicial view) {
        this.view = view;

        // Liga os botões aos métodos de ação
        this.view.getBtnAluno().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirLoginAluno();
            }
        });

        this.view.getBtnAdmin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirLoginAdm();
            }
        });

        this.view.getJButtonCadastro().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirCadastro();
            }
        });
    }

    private void abrirLoginAluno() {
        
        for (java.awt.Window window : java.awt.Window.getWindows()) {
        window.dispose(); // fecha todas as janelas abertas
        }
        TelaLoginAluno telaAluno = new TelaLoginAluno();
        telaAluno.setVisible(true);
       
    }

    private void abrirLoginAdm() {
        for (java.awt.Window window : java.awt.Window.getWindows()) {
        window.dispose(); // fecha todas as janelas abertas
        }
        TelaLoginAdmin telaAdm = new TelaLoginAdmin();
        telaAdm.setVisible(true);
        view.dispose();
    }

    private void abrirCadastro() {
        for (java.awt.Window window : java.awt.Window.getWindows()) {
        window.dispose(); // fecha todas as janelas abertas
        }
        TelaInicialCadastro telaCadastro = new TelaInicialCadastro();
        telaCadastro.setVisible(true);
        view.dispose();
    }
}
