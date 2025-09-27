/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.AdmDAO;
import Model.CadastroAdmModel;
import View.CadastroAdm;

import javax.swing.JFrame; 
import javax.swing.*;

public class CadastroAdmController {
    private final CadastroAdm view;
    private final AdmDAO dao;
    private final TelaCadastroController nav;

    public CadastroAdmController(CadastroAdm view, AdmDAO dao, TelaCadastroController nav) {
        this.view = view;
        this.dao  = dao;
        this.nav  = nav;
    }

    public void cadastrarAdm() {
        CadastroAdmModel m = new CadastroAdmModel(
            view.getNome(),
            view.getId(),        
            view.getEmail(),
            view.getSenha()
        );

        if (!m.isValido()) {
            JOptionPane.showMessageDialog(view,
                "Preencha os campos corretamente (ID obrigatório e senha com 6+ caracteres).",
                "Dados inválidos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if (dao.emailOuIdJaExiste(m.getEmail(), m.getId())) {

                JOptionPane.showMessageDialog(view, "Email ou ID já cadastrado.",
                        "Conflito", JOptionPane.ERROR_MESSAGE);
                return;
            }
            dao.inserir(m);
            JOptionPane.showMessageDialog(view, "ADM cadastrado com sucesso!");
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

    public CadastroAdmController() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
