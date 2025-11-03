/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Model;

/**
 * Classe utilitária para armazenar o estado do usuário logado (Sessão).
 * Usa o padrão Singleton.
 */
public class SessaoUsuario {
    private static SessaoUsuario instance;
    
    private int idUsuarioLogado;
    private String tipoUsuarioLogado; // 'cliente' (aluno) ou 'admin'

    // Construtor privado para implementar o Singleton
    private SessaoUsuario() {}

    public static synchronized SessaoUsuario getInstance() {
        if (instance == null) {
            instance = new SessaoUsuario();
        }
        return instance;
    }
    
    // Método para iniciar a sessão após login bem-sucedido
    public void iniciarSessao(int id, String tipoUsuario) {
        this.idUsuarioLogado = id;
        this.tipoUsuarioLogado = tipoUsuario;
    }
    
    // Método para encerrar a sessão
    public void encerrarSessao() {
        this.idUsuarioLogado = -1; 
        this.tipoUsuarioLogado = null;
    }
    
    // Acesso ao ID do usuário logado (o que o PedidoController precisa)
    public static int getId() {
        return getInstance().idUsuarioLogado;
    }

    // Verifica se há um usuário logado
    public static boolean isLogado() {
        return getInstance().idUsuarioLogado > 0;
    }
    
    public static String getTipoUsuario() {
        return getInstance().tipoUsuarioLogado;
    }
}