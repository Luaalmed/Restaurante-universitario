/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.AlunoDAO;
import Model.CadastroAlunoModel;
import View.CadastroAluno;

import javax.swing.*;

public class CadastroAlunoController {
    private final CadastroAluno view;
    private final AlunoDAO dao;
    private final TelaCadastroController nav;

    public CadastroAlunoController(CadastroAluno view, AlunoDAO dao, TelaCadastroController nav) {
        this.view = view;
        this.dao  = dao;
        this.nav  = nav;
    }

    public void cadastrarAluno() {
        Model.CadastroAlunoModel m = new Model.CadastroAlunoModel(
            view.getNome(),
            view.getId(),
            view.getRA(),
            view.getEmail(),
            view.getSenha()
        );

        if (!m.isValido()) {
            JOptionPane.showMessageDialog(view, "Preencha os campos corretamente. Senha mínimo 6 caracteres.",
                    "Dados inválidos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if (dao.emailOuRaJaExiste(m.getEmail(), m.getRa())) {
                JOptionPane.showMessageDialog(view, "Email ou RA já cadastrado.",
                        "Conflito", JOptionPane.ERROR_MESSAGE);
                return;
            }
            dao.inserir(m);
            JOptionPane.showMessageDialog(view, "Aluno cadastrado com sucesso!");
            view.limpar();
            nav.irParaHome();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao salvar: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void voltar() {
        nav.irParaHome();
    }

    public CadastroAlunoController() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

