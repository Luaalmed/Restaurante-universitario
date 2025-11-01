package Controller;

import DAO.AlunoDAO;
import View.PainelAluno;
import View.TelaLoginAluno;
import Model.SessaoUsuario;
import Model.UsuarioInfo;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class LoginAlunoController {

    private final TelaLoginAluno view;
    private final AlunoDAO alunoDAO = new AlunoDAO();

    public LoginAlunoController(TelaLoginAluno view) {
        this.view = view;
        // conecta o botão Login ao handler
        this.view.setLoginAction(e -> onLogin());
    }

    private void onLogin() {
        String ra    = view.getRa();
        String senha = view.getSenha();

        if (ra.isBlank() || senha.isBlank()) {
            JOptionPane.showMessageDialog(view,
                    "Preencha RA e Senha.",
                    "Login", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // 1. CHAMA O NOVO MÉTODO DO DAO QUE RETORNA O OBJETO UsuarioInfo
            UsuarioInfo usuarioInfo = alunoDAO.autenticarAluno(ra, senha);
            
            if (usuarioInfo != null) {
                
                // 2. INICIA A SESSÃO: Armazena o ID e o Tipo de Usuário (cliente)
                SessaoUsuario.getInstance().iniciarSessao(
                    usuarioInfo.getId(), 
                    usuarioInfo.getTipoUsuario()
                );
                
                JOptionPane.showMessageDialog(view, "Login realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                // 3. ABRE A NOVA TELA E FECHA AS ANTIGAS
                SwingUtilities.invokeLater(() -> {
                    new PainelAluno().setVisible(true);
                });
                
                // Fecha todas as janelas abertas
                for (java.awt.Window window : java.awt.Window.getWindows()) {
                    window.dispose();
                }
            
            } else {
                JOptionPane.showMessageDialog(view,
                        "RA ou Senha inválidos.",
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