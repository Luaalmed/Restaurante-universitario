package Controller;

import View.TelaInicialCadastro;
import View.TelaCadastroAluno;
import View.TelaCadastroAdmin;
import View.TelaInicial;
import DAO.AlunoDAO;
import DAO.AdmDAO;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaInicialCadastroController {
    private final TelaInicialCadastro view;

    public TelaInicialCadastroController(TelaInicialCadastro view) {
        this.view = view;
        registrarEventos();
    }

    private void registrarEventos() {
        // Confirma que o botão não é nulo
        System.out.println("getBtnVoltar() = " + view.getBtnVoltar());

        view.getBtnAluno().addActionListener(e -> {
            view.dispose();
            new TelaCadastroAluno(new AlunoDAO()).setVisible(true);
        });

        view.getBtnAdmin().addActionListener(e -> {
            view.dispose();
            new TelaCadastroAdmin(new AdmDAO()).setVisible(true);
        });

        view.getBtnVoltar().addActionListener(e -> {
            System.out.println("Voltar clicado");
            view.dispose();
            new TelaInicial().setVisible(true);
        });
    }
}