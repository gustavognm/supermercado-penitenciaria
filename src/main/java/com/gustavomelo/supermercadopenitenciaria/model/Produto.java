package com.gustavomelo.supermercadopenitenciaria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "produto_id")
    private Integer id;

    @NotBlank(message = "Nome é obrigatório")
    @Column(name = "produto_nome", nullable = false)
    private String nome;

    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
    @Column(name = "produto_preco", nullable = false)
    private BigDecimal preco;

    @Column(name = "produto_descricao")
    private String descricao;

    @NotNull(message = "Categoria é obrigatória")
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
}
