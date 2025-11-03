package model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class Pedido {
    private int id;
    private int idUsuario;
    private Timestamp dataPedido;
    private String status; // 'pendente', 'em_preparacao', 'pronto', 'entregue'
    private BigDecimal total;
    private String formaPagamento; // 'cartao', 'dinheiro', 'pix'
    
    // Relação 1:N com Itens do Pedido
    private List<ItemPedido> itens;

    // Construtor
    public Pedido() {
        this.dataPedido = new Timestamp(System.currentTimeMillis());
        this.status = "pendente";
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public Timestamp getDataPedido() { return dataPedido; }
    public void setDataPedido(Timestamp dataPedido) { this.dataPedido = dataPedido; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public String getFormaPagamento() { return formaPagamento; }
    public void setFormaPagamento(String formaPagamento) { this.formaPagamento = formaPagamento; }

    public List<ItemPedido> getItens() { return itens; }
    public void setItens(List<ItemPedido> itens) { this.itens = itens; }
}