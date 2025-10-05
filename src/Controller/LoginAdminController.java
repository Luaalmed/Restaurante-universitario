package controller;

import DAO.AdmDAO;
import View.PainelAdmin;
import View.TelaLoginAdmin;
import View.TelaInicial; 
import javax.swing.SwingUtilities;

public class LoginAdminController {
    private final TelaLoginAdmin view;
    private final AdmDAO admDAO = new AdmDAO();

    public LoginAdminController(TelaLoginAdmin view) {
        this.view = view;
        this.view.setLoginAction(e -> onLogin());
        this.view.getBtnVoltar().addActionListener(e -> voltar()); // <-- adiciona ação do botão Voltar
    }

    private void onLogin() {
        String email = view.getEmail();
        String senha = view.getSenha();

        if (email.isBlank() || senha.isBlank()) {
            javax.swing.JOptionPane.showMessageDialog(view,
                    "Preencha Email e Senha.",
                    "Login Admin", javax.swing.JOptionPane.WARNING_MESSAGE);
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
                javax.swing.JOptionPane.showMessageDialog(view,
                        "Administrador não encontrado.",
                        "Login Admin", javax.swing.JOptionPane.WARNING_MESSAGE);
            }
        } catch (java.sql.SQLException ex) {
            javax.swing.JOptionPane.showMessageDialog(view,
                    "Erro ao acessar o banco: " + ex.getMessage(),
                    "Erro", javax.swing.JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // ✅ Método Voltar
    public void voltar() {
        SwingUtilities.invokeLater(() -> {
            new TelaInicial().setVisible(true); // Abra a tela principal
            view.dispose(); // Fecha a tela de login
        });
    }
}
