package Controller;

import javax.swing.JButton;
import javax.swing.JFrame;
import view.CadastrarCardapio;

public class PainelAdminController {

    private final JFrame painel;
    private final JButton btnCadastrar;

    /**
     * Construtor recebe a view e o botão para configurar navegação.
     */
    public PainelAdminController(JFrame painelAdmin, JButton btnCadastroCardapio) {
        this.painel = painelAdmin;
        this.btnCadastrar = btnCadastroCardapio;
        registrarEvento();
    }

    // Registra o ActionListener no botão recebido
    private void registrarEvento() {
        btnCadastrar.addActionListener(evt -> {
            painel.dispose();
            new CadastrarCardapio().setVisible(true);
        });
    }
}