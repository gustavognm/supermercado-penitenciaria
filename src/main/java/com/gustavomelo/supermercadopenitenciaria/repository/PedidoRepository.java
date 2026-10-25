package com.gustavomelo.supermercadopenitenciaria.repository;

import com.gustavomelo.supermercadopenitenciaria.model.Pedido;
import com.gustavomelo.supermercadopenitenciaria.model.StatusPedido;
import com.gustavomelo.supermercadopenitenciaria.model.TipoLocalizacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    // Pedidos filtrados pela localização do detento vinculado — usado para
    // separar a listagem em "Pedidos para Alas" e "Pedidos para Pavilhões",
    // já que a entrega acontece em dias diferentes da semana para cada um.
    List<Pedido> findByDetentoTipoLocalizacao(TipoLocalizacao tipoLocalizacao);

    // Usados para separar pedidos em andamento (tela principal) dos
    // pedidos finalizados — Entregue ou Recusado — que vão para o histórico.
    List<Pedido> findByDetentoTipoLocalizacaoAndStatusNotIn(TipoLocalizacao tipoLocalizacao, Collection<StatusPedido> statusExcluidos);
    List<Pedido> findByDetentoTipoLocalizacaoAndStatusIn(TipoLocalizacao tipoLocalizacao, Collection<StatusPedido> statusIncluidos);

    // Todos os pedidos em andamento (qualquer localização) — usado no painel
    // de estatísticas da tela inicial.
    List<Pedido> findByStatusNotIn(Collection<StatusPedido> statusExcluidos);
}
