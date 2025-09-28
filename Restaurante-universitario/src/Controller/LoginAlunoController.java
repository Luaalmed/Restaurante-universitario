package Controller;

import javax.swing.JOptionPane;
import View.LoginAlunoView;
import View.PainelAlunoView;
import DAO.AlunoDAO; 
import java.sql.SQLException;

public class LoginAlunoController {
    private final LoginAlunoView view;
    private final AlunoDAO dao; 
    
    
public LoginAlunoController(View.LoginAlunoView view) {
    this(view, new DAO.AlunoDAO());
}
public LoginAlunoController(View.LoginAlunoView view, DAO.AlunoDAO dao) {
    this.view = view;
    this.dao  = dao;
    this.view.getBtnEntrar().addActionListener(e -> realizarLogin());
}


  


    public void realizarLogin() {
        String ra    = view.getRa().trim();
        String senha = view.getSenha(); // mantenha exatamente como digitada

        try {
            boolean ok = dao.autenticarPorRaSenha(ra, senha);
            if (ok) {
                JOptionPane.showMessageDialog(view, "Login de Aluno realizado com sucesso!");
                view.dispose();
                new PainelAlunoView().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(view, "RA ou Senha incorretos!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Erro ao autenticar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
