/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.AlunoDAO;
import Model.CadastroAlunoModel;
import View.TelaCadastroAluno;
import javax.swing.*;

public class CadastroAlunoController {
    private final TelaCadastroAluno view;
    private final AlunoDAO dao;

    public CadastroAlunoController(TelaCadastroAluno view, AlunoDAO dao) {
        this.view = view;
        this.dao  = dao;
    }

    public void cadastrarAluno() {
        // CORREÇÃO AQUI: Agora passamos 5 argumentos, incluindo o getId()
        CadastroAlunoModel m = new CadastroAlunoModel(
            view.getNome(),
            view.getRA(),
            view.getEmail(),
            view.getId(), // <--- ESTAVA FALTANDO ESTE
            view.getSenha()
        );

        // O isValido() no seu Model precisa ser ajustado se a senha não for obrigatória
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
            e.printStackTrace(); // Bom para ver o erro detalhado no console
        }
    }
}
