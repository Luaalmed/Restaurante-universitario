/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package restaurante.universitario;

import View.TelaInicial; // Importe sua tela
import View.TelaInicialCadastro;
import javax.swing.SwingUtilities;
import Controller.PainelAdminController;    
import Controller.TelaInicialCadastroController;

public class RestauranteUniversitario {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Apenas cria e mostra a primeira tela. Nada mais.
            new TelaInicial().setVisible(true);
        
            TelaInicial tela = new TelaInicial();
            // Registra o controller aqui, depois de initComponents()
            tela.setVisible(true);
        });
    }
}