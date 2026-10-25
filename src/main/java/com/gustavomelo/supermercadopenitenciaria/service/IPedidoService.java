package com.gustavomelo.supermercadopenitenciaria.service;

import com.gustavomelo.supermercadopenitenciaria.model.Pedido;
import com.gustavomelo.supermercadopenitenciaria.model.TipoLocalizacao;

import java.util.List;

public interface IPedidoService {
    Integer criarPedido(Pedido pedido);
    List<Pedido> getAllPedidos();
    List<Pedido> getPedidosPorTipoLocalizacao(TipoLocalizacao tipoLocalizacao);
    List<Pedido> getPedidosAtivosPorTipoLocalizacao(TipoLocalizacao tipoLocalizacao);
    List<Pedido> getPedidosFinalizadosPorTipoLocalizacao(TipoLocalizacao tipoLocalizacao);
    List<Pedido> getPedidosAtivos();
    Pedido getPedidoById(Integer id);
    void deletePedidoById(Integer id);
    void adicionarItem(Integer pedidoId, Integer produtoId, Integer quantidade);
    void removerItem(Integer pedidoId, Integer itemId);
    void avancarStatus(Integer pedidoId);
    void recusarPedido(Integer pedidoId, String motivo);
    void atualizarObservacao(Integer pedidoId, String observacao);
}
