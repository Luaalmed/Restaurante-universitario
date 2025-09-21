/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package restaurante.universitario;
import view.CadastrarCardapio;

/**
 *
 * @author Bomfi
 */
public class RestauranteUniversitario {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CadastrarCardapio().setVisible(true);
            }
        });
    }
    
}
