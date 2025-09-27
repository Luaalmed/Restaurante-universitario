package Controller;


import javax.swing.JOptionPane;
import View.LoginAlunoView;
import View.PainelAlunoView;

public class LoginAlunoController {
    private LoginAlunoView view;

    public LoginAlunoController(LoginAlunoView view) {
        this.view = view;
        this.view.getBtnEntrar().addActionListener(e -> realizarLogin());
    }

    public void realizarLogin() {
        String ra = view.getRa();
        String senha = view.getSenha();

        // Lógica de login SUPER SIMPLES (dados fixos)
        if ("112233".equals(ra) && "456".equals(senha)) {
            JOptionPane.showMessageDialog(view, "Login de Aluno realizado com sucesso!");
            view.dispose(); // Fecha a tela de login
            new PainelAlunoView().setVisible(true); // Abre o painel do Aluno
        } else {
            JOptionPane.showMessageDialog(view, "RA ou Senha incorretos!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}