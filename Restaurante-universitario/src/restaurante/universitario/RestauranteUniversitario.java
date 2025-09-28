package restaurante.universitario;

import javax.swing.*;
import java.awt.*;

import controller.TelaCadastroController;
import controller.CadastroAlunoController;
import controller.CadastroAdmController;

import Model.TelaCadastroModel;



import View.CadastroAluno;
import View.CadastroAdm;
import View.LoginAlunoView;  // opcional, se quiser abrir o login em algum ponto

import DAO.AlunoDAO;
import DAO.AdmDAO;
import view.TelaCadastro;

public class RestauranteUniversitario {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Frame principal
            JFrame frame = new JFrame("Restaurante Universitário");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Root com CardLayout
            CardLayout layout = new CardLayout();
            JPanel root = new JPanel(layout);

            // Modelo de navegação + controller de navegação
            TelaCadastroModel navModel = new TelaCadastroModel();
            TelaCadastroController nav = new TelaCadastroController(navModel, root, layout);

            // DAOs
            AlunoDAO alunoDAO = new AlunoDAO();
            AdmDAO   admDAO   = new AdmDAO();

            // Views
            TelaCadastro telaHome = new TelaCadastro(nav); // sua home/menu principal
            CadastroAluno viewAluno = new CadastroAluno();  // com getBtnCadastrar()
            CadastroAdm   viewAdm   = new CadastroAdm();    // com getBtnCadastrar()

            // Controllers
            CadastroAlunoController alunoCtl = new CadastroAlunoController(viewAluno, alunoDAO, nav);
            CadastroAdmController   admCtl   = new CadastroAdmController(viewAdm,   admDAO,   nav);

            // Liga botões de salvar
            viewAluno.getBtnCadastrar().addActionListener(e -> alunoCtl.cadastrarAluno());
            viewAdm.getBtnCadastrar().addActionListener(e -> admCtl.cadastrarAdm());

            // Liga navegação da tela Home (ajuste conforme seus botões reais)
            try {
                telaHome.getCadalunobtm().addActionListener(e -> nav.irParaAluno());
                telaHome.getCadadmbtm().addActionListener(e -> nav.irParaAdm());
                // se houver um botão de login, pode chamar new View.LoginAlunoView().setVisible(true);
            } catch (Throwable ignored) {
                // Se sua TelaCadastro já lida com nav internamente, pode ignorar
            }

            // Registra telas no CardLayout
            root.add(telaHome, TelaCadastroController.CARD_HOME);
            root.add(viewAluno, TelaCadastroController.CARD_ALUNO);
            root.add(viewAdm,   TelaCadastroController.CARD_ADM);

            // Exibe
            frame.setContentPane(root);
            frame.setSize(720, 480);
            frame.setLocationRelativeTo(null);
            layout.show(root, TelaCadastroController.CARD_HOME);
            frame.setVisible(true);
        });
    }
}
