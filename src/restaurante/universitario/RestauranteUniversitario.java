/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package restaurante.universitario;

import javax.swing.SwingUtilities;
import View.TelaInicial;
import controller.TelaInicialController;

public class RestauranteUniversitario {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaInicial tela = new TelaInicial();           // cria uma única instância
            TelaInicialController controller = new TelaInicialController(tela); // conecta o controller
            tela.setVisible(true);                          // mostra essa tela
        });
    }
}