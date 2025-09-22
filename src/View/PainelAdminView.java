package View;


import javax.swing.*;
import java.awt.*;

public class PainelAdminView extends JFrame {
    public PainelAdminView() {
        setTitle("Painel do Administrador");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JLabel mensagem = new JLabel("BEM-VINDO, ADMINISTRADOR!", SwingConstants.CENTER);
        mensagem.setFont(new Font("Arial", Font.BOLD, 16));
        add(mensagem);
    }
}