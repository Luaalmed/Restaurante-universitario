package controller;

import DAO.AdmDAO;
import Model.CadastroAdmModel;
import View.CadastroAdm;

import javax.swing.*;
import java.awt.*;


public class CadastroAdmController {
    private final CadastroAdm view;
    private final AdmDAO dao;
    private final TelaCadastroController nav; // opcional

    public CadastroAdmController(CadastroAdm view, AdmDAO dao) {
        this(view, dao, null);
    }

    public CadastroAdmController(CadastroAdm view, AdmDAO dao, TelaCadastroController nav) {
        this.view = view;
        this.dao  = dao;
        this.nav  = nav;

        this.view.getBtnCadastrar().addActionListener(e -> cadastrarAdm());
    }

    public void cadastrarAdm() {
        String nome  = view.getNome().trim();
        String email = view.getEmail().trim();
        String senha = view.getSenha();

        CadastroAdmModel m = new CadastroAdmModel(nome, email, senha);

        if (!m.isValido()) {
            JOptionPane.showMessageDialog(view,
                "Preencha os campos corretamente (senha com 6+ caracteres).",
                "Dados inválidos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if (dao.emailJaExiste(m.getEmail())) {
                JOptionPane.showMessageDialog(view, "Email já cadastrado.",
                        "Conflito", JOptionPane.ERROR_MESSAGE);
                return;
            }

            dao.inserir(m);
            JOptionPane.showMessageDialog(view, "Admin cadastrado com sucesso!");
            view.limpar();

            // Abre a tela de login do admin
            new View.LoginAdminView().setVisible(true);

            // Opcional: fechar janela que contém este painel OU voltar para Home
            Window janela = SwingUtilities.getWindowAncestor(view);
            if (janela != null) janela.dispose();
            // if (nav != null) nav.irParaHome();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view,
                "Erro ao salvar: " + e.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
