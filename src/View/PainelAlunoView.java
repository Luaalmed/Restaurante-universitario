package View;


import javax.swing.*;
import java.awt.*;

public class PainelAlunoView extends JFrame {
    public PainelAlunoView() {
        setTitle("Painel do Aluno");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JLabel mensagem = new JLabel("BEM-VINDO, ALUNO!", SwingConstants.CENTER);
        mensagem.setFont(new Font("Arial", Font.BOLD, 16));
        add(mensagem);
    }
}