/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package restaurante.universitario;

import javax.swing.*;
import java.awt.*;

// seus pacotes:
import controller.TelaCadastroController;
import controller.CadastroAlunoController;
import controller.CadastroAdmController;

import Model.TelaCadastroModel;

import view.TelaCadastro;
import View.CadastroAluno;
import View.CadastroAdm;

import DAO.AlunoDAO;
import DAO.AdmDAO;

public class RestauranteUniversitario {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // ====== FRAME PRINCIPAL ======
            JFrame frame = new JFrame("Restaurante Universitário");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // ====== CARD LAYOUT ROOT ======
            CardLayout layout = new CardLayout();
            JPanel root = new JPanel(layout);

            // ====== MODEL + NAV CONTROLLER ======
            TelaCadastroModel navModel = new TelaCadastroModel();
            TelaCadastroController nav = new TelaCadastroController(navModel, root, layout);

            // ====== DAOs ======
            AlunoDAO alunoDAO = new AlunoDAO();
            AdmDAO   admDAO   = new AdmDAO();

            // ====== VIEWS ======
            // Observação: se sua TelaCadastro não recebe controller no construtor,
            // troque para: TelaCadastro telaHome = new TelaCadastro();
            TelaCadastro telaHome = new TelaCadastro(nav);

            // As views de cadastro foram criadas com "controller" no construtor.
            // Para evitar ciclo, passamos null e ligamos os botões manualmente abaixo.
            CadastroAluno viewAluno = new CadastroAluno(null);
            CadastroAdm   viewAdm   = new CadastroAdm(null);

            // ====== CONTROLLERS ======
            CadastroAlunoController alunoCtl = new CadastroAlunoController(viewAluno, alunoDAO, nav);
            CadastroAdmController   admCtl   = new CadastroAdmController(viewAdm, admDAO, nav);

            // ====== AMARRAR BOTÕES (sem alterar suas Views) ======
            // Tela inicial -> navegação
            try {
                // Se sua TelaCadastro expõe getters para os botões, use-os:
                telaHome.getCadalunobtm().addActionListener(e -> nav.irParaAluno());
                telaHome.getCadadmbtm().addActionListener(e -> nav.irParaAdm());
            } catch (NoSuchMethodError | Exception ignored) {
                // Se não existir getters, basta garantir que a TelaCadastro já chama o nav internamente.
                // (Se precisar, te mando uma versão dessa View com os getters.)
            }

            // Cadastro Aluno -> salvar
            viewAluno.getCadastraralunobtm().addActionListener(e -> alunoCtl.cadastrarAluno());

            // Cadastro ADM -> salvar
            viewAdm.getCadastraradmbtm().addActionListener(e -> admCtl.cadastrarAdm());

            // ====== REGISTRAR TELAS NO CARDLAYOUT ======
            // Use os mesmos nomes que seu TelaCadastroController expõe (ajuste se forem diferentes)
            root.add(telaHome, TelaCadastroController.CARD_HOME);
            root.add(viewAluno, TelaCadastroController.CARD_ALUNO);
            root.add(viewAdm,   TelaCadastroController.CARD_ADM);

            // ====== MOSTRAR APP ======
            frame.setContentPane(root);
            frame.setSize(600, 420);
            frame.setLocationRelativeTo(null);
            layout.show(root, TelaCadastroController.CARD_HOME);
            frame.setVisible(true);
        });
    }
}
