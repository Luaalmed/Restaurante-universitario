package Controller;

import DAO.AlunoDAO;                    
import View.PainelAluno;               // <-- IMPORTA A VIEW DO PAINEL
import View.TelaLoginAluno;            // <-- IMPORTA A VIEW DO LOGIN
import java.sql.SQLException;          // <-- EXCEÇÕES DO JDBC
import javax.swing.JOptionPane;        // <-- DIALOGS
import javax.swing.SwingUtilities;     // <-- ABRIR A OUTRA TELA

public class LoginAlunoController {

    private final TelaLoginAluno view;
    private final AlunoDAO alunoDAO = new AlunoDAO();   // <-- FIELD DO DAO

    public LoginAlunoController(TelaLoginAluno view) {
        this.view = view;
        // conecta o botão Login ao handler
        this.view.setLoginAction(e -> onLogin());
    }

    private void onLogin() {
        String ra    = view.getRa();       // <- pega da view
        String senha = view.getSenha();    // <- pega da view (tudo minúsculo)

        if (ra.isBlank() || senha.isBlank()) {
            JOptionPane.showMessageDialog(view,
                    "Preencha RA e Senha.",
                    "Login", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            boolean ok = alunoDAO.autenticarAluno(ra, senha);  // <- senha minúsculo
            if (ok) {
                SwingUtilities.invokeLater(() -> {
                    new PainelAluno().setVisible(true);
                });
           for (java.awt.Window window : java.awt.Window.getWindows()) {
        window.dispose(); // fecha todas as janelas abertas
        }
            } else {
                JOptionPane.showMessageDialog(view,
                        "Aluno não encontrado.",
                        "Login", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view,
                    "Erro ao acessar o banco: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
