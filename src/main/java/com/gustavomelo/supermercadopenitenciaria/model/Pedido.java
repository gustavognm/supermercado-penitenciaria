package com.gustavomelo.supermercadopenitenciaria.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pedido_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "detento_id", nullable = false)
    private Detento detento;

    @Enumerated(EnumType.STRING)
    @Column(name = "pedido_status", nullable = false)
    private StatusPedido status = StatusPedido.EM_MONTAGEM;

    @Column(name = "pedido_data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "pedido_motivo_recusa")
    private String motivoRecusa;

    @Column(name = "pedido_observacao", columnDefinition = "TEXT")
    private String observacao;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens;

    /**
     * Valor total do pedido, calculado em tempo real a partir dos itens
     * (não é uma coluna persistida — evita divergência entre o valor
     * armazenado e a soma real dos itens).
     */
    @Transient
    public BigDecimal getValorTotal() {
        if (itens == null) {
            return BigDecimal.ZERO;
        }
        return itens.stream()
                .map(ItemPedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Indica se o pedido ainda pode ter itens adicionados/removidos.
     * A edição só é permitida até a confirmação do pagamento — a partir do
     * status PAGO ela fica bloqueada, evitando divergência entre o valor
     * recebido via PIX e o valor final do pedido.
     */
    @Transient
    public boolean isEditavel() {
        return status == StatusPedido.EM_MONTAGEM || status == StatusPedido.AGUARDANDO_PAGAMENTO;
    }
}
