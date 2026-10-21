package com.gustavomelo.supermercadopenitenciaria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoria_id")
    private Integer id;

    @NotBlank(message = "Nome é obrigatório")
    @Column(name = "categoria_nome", nullable = false)
    private String nome;

    @Column(name = "categoria_descricao")
    private String descricao;

    @NotNull(message = "Limite de quantidade é obrigatório")
    @Min(value = 1, message = "Limite de quantidade deve ser maior que zero")
    @Column(name = "categoria_limite_quantidade", nullable = false)
    private Integer limiteQuantidade;
}
