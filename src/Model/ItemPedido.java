package model;

import java.math.BigDecimal;

public class ItemPedido {
    private int id;
    private int idPedido;
    private Cardapio itemCardapio; // O item do cardápio em si
    private int quantidade;
    private BigDecimal precoUnitario;
    private int idCardapio;
    
    // Construtor usado no Controller

    /**
     *
     * @param item
     * @param quantidade
     */
    public ItemPedido() {
       
    }

    public ItemPedido(Cardapio item, int quantidade) {
         this.itemCardapio = item;
        this.quantidade = quantidade;
        this.precoUnitario = item.getPreco(); // Usa o preço do Cardapio
        if (item != null) {
            this.idCardapio = item.getId();
        }
    }

    // Getters e Setters
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }

    public int getIdCardapio() { 
        if (itemCardapio != null) {
            return itemCardapio.getId();
        }
        return idCardapio; 
    }
    
    public void setIdCardapio(int idCardapio) { 
        this.idCardapio = idCardapio; 
    }

    public Cardapio getItemCardapio() { return itemCardapio; }
    public void setItemCardapio(Cardapio itemCardapio) { 
        this.itemCardapio = itemCardapio; 
        if (itemCardapio != null) {
            this.idCardapio = itemCardapio.getId();
        }
    }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public BigDecimal getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }
    
    public BigDecimal getSubtotal() {
        if (precoUnitario != null && quantidade > 0) {
            return precoUnitario.multiply(new BigDecimal(quantidade));
        }
        return BigDecimal.ZERO;
    }
}