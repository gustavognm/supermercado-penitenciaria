package com.gustavomelo.supermercadopenitenciaria.model;

public enum StatusPedido {
    EM_MONTAGEM("Em Montagem"),
    AGUARDANDO_PAGAMENTO("Aguardando Pagamento"),
    PAGO("Pago"),
    AGUARDANDO_ENTREGA("Aguardando Entrega"),
    ENTREGUE("Entregue"),
    RECUSADO("Recusado");

    private final String label;

    StatusPedido(String label) {
        this.label = label;
    }

    /**
     * Rótulo em português, legível para exibição nas telas (ex: "Em Montagem"
     * em vez de "EM_MONTAGEM").
     */
    public String getLabel() {
        return label;
    }

    /**
     * Classe CSS de badge (definida em custom.css) usada para colorir o status
     * nas telas: amarelo para qualquer status intermediário, verde para
     * Entregue e vermelho para Recusado.
     */
    public String getBadgeClass() {
        return switch (this) {
            case ENTREGUE -> "badge-green";
            case RECUSADO -> "badge-red";
            default -> "badge-amber";
        };
    }
}
