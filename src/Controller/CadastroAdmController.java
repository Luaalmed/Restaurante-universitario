/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.AdmDAO;
import Model.CadastroAdmModel;
import View.TelaCadastroAdmin;
import javax.swing.JOptionPane;

public class CadastroAdmController {
    private final TelaCadastroAdmin view;
    private final AdmDAO dao;

    // Construtor simplificado
    public CadastroAdmController(TelaCadastroAdmin view, AdmDAO dao) {
        this.view = view;
        this.dao  = dao;
    }

    public void cadastrarAdm() {
        // Pega os dados da View através dos getters
        CadastroAdmModel m = new CadastroAdmModel(
            view.getNome(),
            view.getId(),
            view.getEmail(),
            view.getSenha()
        );

        if (!m.isValido()) {
            JOptionPane.showMessageDialog(view,
                "Preencha todos os campos. A senha deve ter no mínimo 6 caracteres.",
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao salvar: " + e.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}