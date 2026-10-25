package com.gustavomelo.supermercadopenitenciaria.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "itens_pedido")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(name = "item_quantidade", nullable = false)
    private Integer quantidade;

    /**
     * Preço unitário "congelado" no momento em que o item foi adicionado
     * ao pedido — reajustes futuros no preço do produto não afetam
     * pedidos já registrados.
     */
    @Column(name = "item_preco_unitario", nullable = false)
    private BigDecimal precoUnitario;

    @Transient
    public BigDecimal getSubtotal() {
        if (precoUnitario == null || quantidade == null) {
            return BigDecimal.ZERO;
        }
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }
}
