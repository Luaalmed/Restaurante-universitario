package Controller;

import DAO.AlunoDAO;
import Model.CadastroAlunoModel;
import View.TelaCadastroAluno;
import View.TelaInicialCadastro;
import javax.swing.*;

public class CadastroAlunoController {
    private final TelaCadastroAluno view;
    private final AlunoDAO dao;

    public CadastroAlunoController(TelaCadastroAluno view, AlunoDAO dao) {
        this.view = view;
        this.dao  = dao;
        registrarEventos();
    }

    private void registrarEventos() {
        view.getBtnVoltar().addActionListener(e -> {
        view.dispose(); // fecha a tela atual
        new View.TelaInicialCadastro().setVisible(true); // volta pra tela inicial de cadastro
    });
    }

    public void cadastrarAluno() {
        CadastroAlunoModel m = new CadastroAlunoModel(
            view.getNome(),
            view.getRA(),
            view.getEmail(),
            view.getId(),
            view.getSenha()
        );

        if (m.getSenha().length() < 6) {
            JOptionPane.showMessageDialog(view, "A senha deve ter no mínimo 6 caracteres.",
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao salvar: " + e.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
