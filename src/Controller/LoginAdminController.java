package Controller;

import DAO.AdmDAO;
import View.PainelAdmin;
import View.TelaLoginAdmin;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class LoginAdminController {

    private final TelaLoginAdmin view;
    private final AdmDAO admDAO = new AdmDAO();

    public LoginAdminController(TelaLoginAdmin view) {
        this.view = view;
        this.view.setLoginAction(e -> onLogin());
    }

    private void onLogin() {
        String email = view.getEmail();
        String senha = view.getSenha();

        if (email.isBlank() || senha.isBlank()) {
            JOptionPane.showMessageDialog(view,
                    "Preencha Email e Senha.",
                    "Login Admin", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            boolean ok = admDAO.autenticarAdmin(email, senha);
            if (ok) {
                SwingUtilities.invokeLater(() -> {
                    new PainelAdmin().setVisible(true);
                    view.dispose();
                });
            } else {
                JOptionPane.showMessageDialog(view,
                        "Administrador não encontrado.",
                        "Login Admin", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view,
                    "Erro ao acessar o banco: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
