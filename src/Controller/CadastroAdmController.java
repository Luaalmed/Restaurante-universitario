package Controller;

import DAO.AdmDAO;
import Model.CadastroAdmModel;
import View.TelaCadastroAdmin;
import View.TelaInicialCadastro;
import View.TelaInicial;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastroAdmController {

    private final TelaCadastroAdmin view;
    private final AdmDAO dao;

    public CadastroAdmController(TelaCadastroAdmin view, AdmDAO dao) {
        this.view = view;
        this.dao  = dao;
        registrarEventos();
    }

    private void registrarEventos() {
        view.getBtnCadastrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarAdm();
            }
        });
        view.getBtnVoltar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                voltarParaInicial();
            }
        });
    }

    private void cadastrarAdm() {
        CadastroAdmModel m = new CadastroAdmModel(
            view.getNome(),
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
            if (dao.emailJaExiste(m.getEmail())) {
                JOptionPane.showMessageDialog(view,
                    "Email já cadastrado.",
                    "Conflito", JOptionPane.ERROR_MESSAGE);
                return;
            }
            dao.inserir(m);
            JOptionPane.showMessageDialog(view,
                "ADM cadastrado com sucesso!",
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            view.dispose();
            new TelaInicial().setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view,
                "Erro ao salvar: " + ex.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void voltarParaInicial() {
        view.dispose();
        new TelaInicialCadastro().setVisible(true);
    }
}