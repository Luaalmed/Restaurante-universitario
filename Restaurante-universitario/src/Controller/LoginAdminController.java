package Controller;

import javax.swing.JOptionPane;
import View.LoginAdminView;
import View.PainelAdminView;
import DAO.AdmDAO;
import java.sql.SQLException;

public class LoginAdminController {
    private final LoginAdminView view;
    private final AdmDAO dao;

    public LoginAdminController(LoginAdminView view) {
        this(view, new AdmDAO());
    }

    public LoginAdminController(LoginAdminView view, AdmDAO dao) {
        this.view = view;
        this.dao  = dao;
        this.view.getBtnEntrar().addActionListener(e -> realizarLogin());
    }

    
    
    private void realizarLogin() {
        String email = view.getEmail().trim(); // ou view.getRa().trim() se usar RA
        String senha = view.getSenha();
        try {
            boolean ok = dao.autenticarPorEmailSenha(email, senha); // troque para RA se preciso
            if (ok) {
                JOptionPane.showMessageDialog(view, "Login de Admin realizado com sucesso!");
                view.dispose();
                new PainelAdminView().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(view, "Credenciais inválidas!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Erro ao autenticar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
