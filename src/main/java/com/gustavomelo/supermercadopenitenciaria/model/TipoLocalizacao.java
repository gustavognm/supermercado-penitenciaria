package com.gustavomelo.supermercadopenitenciaria.model;

public enum TipoLocalizacao {
    ALA("Ala", "Alas"),
    PAVILHAO("Pavilhão", "Pavilhões");

    private final String label;
    private final String labelPlural;

    TipoLocalizacao(String label, String labelPlural) {
        this.label = label;
        this.labelPlural = labelPlural;
    }

    /**
     * Rótulo em português, legível para exibição nas telas (ex: "Pavilhão"
     * em vez de "PAVILHAO").
     */
    public String getLabel() {
        return label;
    }

    /**
     * Plural do rótulo — o plural de "Pavilhão" não segue a regra simples de
     * adicionar "s" ("Pavilhões"), por isso fica explícito aqui em vez de
     * ser calculado a partir do singular.
     */
    public String getLabelPlural() {
        return labelPlural;
    }
}
