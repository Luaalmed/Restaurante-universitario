package controller;

import DAO.AlunoDAO;
import Model.CadastroAlunoModel;
import View.CadastroAluno;

import javax.swing.*;
import java.awt.*;

public class CadastroAlunoController {
    private final CadastroAluno view;
    private final AlunoDAO dao;
    private final TelaCadastroController nav; 

    public CadastroAlunoController(CadastroAluno view, AlunoDAO dao) {
        this(view, dao, null);
    }

    public CadastroAlunoController(CadastroAluno view, AlunoDAO dao, TelaCadastroController nav) {
        this.view = view;
        this.dao  = dao;
        this.nav  = nav;

        // Conecta o botão da view
        this.view.getBtnCadastrar().addActionListener(e -> cadastrarAluno());
    }

    public void cadastrarAluno() {
        String nome  = view.getNome().trim();
        String ra    = view.getRA().trim();
        String email = view.getEmail().trim();
        String senha = view.getSenha();

        CadastroAlunoModel m = new CadastroAlunoModel(nome, ra, email, senha);

        if (!m.isValido()) {
            JOptionPane.showMessageDialog(view,
                "Preencha os campos corretamente (senha com 6+ caracteres).",
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

            // Abre a tela de login do aluno
            new View.LoginAlunoView().setVisible(true);

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
