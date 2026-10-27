package com.gustavomelo.supermercadopenitenciaria.model;

public enum Perfil {
    ADMINISTRADOR("Administrador"),
    ATENDENTE("Atendente");

    private final String label;

    Perfil(String label) {
        this.label = label;
    }

    /**
     * Rótulo em português, legível para exibição nas telas (ex: "Administrador"
     * em vez de "ADMINISTRADOR").
     */
    public String getLabel() {
        return label;
    }
}
