package com.gustavomelo.supermercadopenitenciaria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Integer id;

    @NotBlank(message = "Nome é obrigatório")
    @Column(name = "usuario_nome", nullable = false)
    private String nome;

    // Sem @NotBlank de propósito: na edição, o campo de senha pode ficar em
    // branco para manter a senha atual (ver UsuarioServiceImpl). A
    // obrigatoriedade para um usuário novo é verificada manualmente.
    @Column(name = "usuario_senha", nullable = false)
    private String senha;

    @NotBlank(message = "E-mail é obrigatório")
    @Column(name = "usuario_email", nullable = false, unique = true)
    private String email;

    // Um único perfil por usuário: Administrador engloba tudo que o
    // Atendente pode fazer, além do CRUD de usuários.
    @NotNull(message = "Perfil é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "usuario_perfil", nullable = false)
    private Perfil perfil;
}
