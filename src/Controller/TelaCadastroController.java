/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import Model.TelaCadastroModel;
import Model.TelaCadastroModel.Tela;
import View.TelaCadastroAluno;
import View.TelaCadastroAdmin;
import view.TelaCadastro;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroController {
    private final TelaCadastroModel model;
    private final JPanel root;           // painel com CardLayout
    private final CardLayout layout;

    public static final String CARD_HOME  = "TELA_HOME";
    public static final String CARD_ALUNO = "TELA_ALUNO";
    public static final String CARD_ADM   = "TELA_ADM";

    public TelaCadastroController(TelaCadastroModel model, JPanel root, CardLayout layout) {
        this.model  = model;
        this.root   = root;
        this.layout = layout;
    }

    public void irParaAluno() {
        model.setTelaAtual(Tela.ALUNO);
        layout.show(root, CARD_ALUNO);
    }

    public void irParaAdm() {
        model.setTelaAtual(Tela.ADM);
        layout.show(root, CARD_ADM);
    }

    public void irParaHome() {
        model.setTelaAtual(Tela.HOME);
        layout.show(root, CARD_HOME);
    }
}
