package com.gustavomelo.supermercadopenitenciaria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "detentos")
public class Detento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detento_id")
    private Integer id;

    @NotBlank(message = "Nome é obrigatório")
    @Column(name = "detento_nome", nullable = false)
    private String nome;

    @NotBlank(message = "Código Infopen é obrigatório")
    @Column(name = "detento_codigo_infopen", nullable = false)
    private String codigoInfopen;

    @NotNull(message = "Tipo de localização é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "detento_tipo_localizacao", nullable = false)
    private TipoLocalizacao tipoLocalizacao;

    @NotBlank(message = "Identificador da ala/pavilhão é obrigatório")
    @Column(name = "detento_identificador_localizacao", nullable = false)
    private String identificadorLocalizacao;

    @NotBlank(message = "Número da cela é obrigatório")
    @Column(name = "detento_numero_cela", nullable = false)
    private String numeroCela;
}
