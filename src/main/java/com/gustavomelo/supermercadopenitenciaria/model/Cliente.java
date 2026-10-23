package com.gustavomelo.supermercadopenitenciaria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id")
    private Integer id;

    @NotBlank(message = "Nome é obrigatório")
    @Column(name = "cliente_nome", nullable = false)
    private String nome;

    @NotBlank(message = "Cadastro na penitenciária é obrigatório")
    @Column(name = "cliente_cadastro_penitenciaria", nullable = false)
    private String cadastroPenitenciaria;

    @NotBlank(message = "Telefone é obrigatório")
    @Column(name = "cliente_telefone", nullable = false)
    private String telefone;
}
