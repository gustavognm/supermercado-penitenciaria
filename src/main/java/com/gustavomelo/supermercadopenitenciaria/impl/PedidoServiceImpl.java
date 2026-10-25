package com.gustavomelo.supermercadopenitenciaria.impl;

import com.gustavomelo.supermercadopenitenciaria.model.Categoria;
import com.gustavomelo.supermercadopenitenciaria.model.ItemPedido;
import com.gustavomelo.supermercadopenitenciaria.model.Pedido;
import com.gustavomelo.supermercadopenitenciaria.model.Produto;
import com.gustavomelo.supermercadopenitenciaria.model.StatusPedido;
import com.gustavomelo.supermercadopenitenciaria.model.TipoLocalizacao;
import com.gustavomelo.supermercadopenitenciaria.repository.ItemPedidoRepository;
import com.gustavomelo.supermercadopenitenciaria.repository.PedidoRepository;
import com.gustavomelo.supermercadopenitenciaria.service.IPedidoService;
import com.gustavomelo.supermercadopenitenciaria.service.IProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class PedidoServiceImpl implements IPedidoService {

    /**
     * Sequência linear de avanço do status do pedido. O status RECUSADO não
     * entra aqui pois não é um avanço automático — só acontece através de
     * {@link #recusarPedido(Integer, String)}, quando o pedido está
     * aguardando entrega.
     */
    private static final Map<StatusPedido, StatusPedido> PROXIMO_STATUS = Map.of(
            StatusPedido.EM_MONTAGEM, StatusPedido.AGUARDANDO_PAGAMENTO,
            StatusPedido.AGUARDANDO_PAGAMENTO, StatusPedido.PAGO,
            StatusPedido.PAGO, StatusPedido.AGUARDANDO_ENTREGA,
            StatusPedido.AGUARDANDO_ENTREGA, StatusPedido.ENTREGUE
    );

    /**
     * Status que marcam um pedido como finalizado — saem da tela principal
     * (pedidos em andamento) e passam a aparecer só no histórico.
     */
    private static final List<StatusPedido> STATUS_FINALIZADOS = List.of(
            StatusPedido.ENTREGUE, StatusPedido.RECUSADO
    );

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private IProdutoService produtoService;

    @Override
    public Integer criarPedido(Pedido pedido) {
        // Status inicial (EM_MONTAGEM) já vem definido por padrão na entidade.
        pedido.setDataCriacao(LocalDateTime.now());
        return pedidoRepository.save(pedido).getId();
    }

    @Override
    public List<Pedido> getAllPedidos() {
        return pedidoRepository.findAll();
    }

    @Override
    public List<Pedido> getPedidosPorTipoLocalizacao(TipoLocalizacao tipoLocalizacao) {
        return pedidoRepository.findByDetentoTipoLocalizacao(tipoLocalizacao);
    }

    @Override
    public List<Pedido> getPedidosAtivosPorTipoLocalizacao(TipoLocalizacao tipoLocalizacao) {
        return pedidoRepository.findByDetentoTipoLocalizacaoAndStatusNotIn(tipoLocalizacao, STATUS_FINALIZADOS);
    }

    @Override
    public List<Pedido> getPedidosFinalizadosPorTipoLocalizacao(TipoLocalizacao tipoLocalizacao) {
        return pedidoRepository.findByDetentoTipoLocalizacaoAndStatusIn(tipoLocalizacao, STATUS_FINALIZADOS);
    }

    @Override
    public List<Pedido> getPedidosAtivos() {
        return pedidoRepository.findByStatusNotIn(STATUS_FINALIZADOS);
    }

    @Override
    public Pedido getPedidoById(Integer id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado: " + id));
    }

    @Override
    public void deletePedidoById(Integer id) {
        Pedido pedido = getPedidoById(id);
        pedidoRepository.deleteById(pedido.getId());
    }

    @Override
    @Transactional
    public void adicionarItem(Integer pedidoId, Integer produtoId, Integer quantidade) {
        if (quantidade == null || quantidade < 1) {
            throw new RuntimeException("A quantidade deve ser maior que zero.");
        }

        Pedido pedido = getPedidoById(pedidoId);
        verificarPedidoEditavel(pedido);

        Produto produto = produtoService.getProdutoById(produtoId);
        Categoria categoria = produto.getCategoria();

        // A quantidade já presente no pedido para essa categoria (somando todos
        // os itens de produtos da mesma categoria) mais a quantidade que está
        // sendo adicionada agora não pode ultrapassar o limiteQuantidade da
        // categoria.
        if (categoria != null && categoria.getLimiteQuantidade() != null) {
            int quantidadeAtualNaCategoria = 0;
            if (pedido.getItens() != null) {
                for (ItemPedido itemExistente : pedido.getItens()) {
                    Categoria categoriaDoItem = itemExistente.getProduto().getCategoria();
                    if (categoriaDoItem != null && categoriaDoItem.getId().equals(categoria.getId())) {
                        quantidadeAtualNaCategoria += itemExistente.getQuantidade();
                    }
                }
            }

            if (quantidadeAtualNaCategoria + quantidade > categoria.getLimiteQuantidade()) {
                throw new RuntimeException("Limite da categoria '" + categoria.getNome() + "' excedido. "
                        + "Limite: " + categoria.getLimiteQuantidade()
                        + ", já no pedido: " + quantidadeAtualNaCategoria
                        + ", tentando adicionar: " + quantidade + ".");
            }
        }

        ItemPedido item = new ItemPedido();
        item.setPedido(pedido);
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        // Preço "congelado" no momento da adição — reajustes futuros no produto
        // não afetam itens já adicionados ao pedido.
        item.setPrecoUnitario(produto.getPreco());

        itemPedidoRepository.save(item);
    }

    @Override
    @Transactional
    public void removerItem(Integer pedidoId, Integer itemId) {
        ItemPedido item = itemPedidoRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado: " + itemId));

        if (item.getPedido() == null || !item.getPedido().getId().equals(pedidoId)) {
            throw new RuntimeException("Este item não pertence a este pedido.");
        }

        verificarPedidoEditavel(item.getPedido());

        itemPedidoRepository.deleteById(itemId);
    }

    @Override
    @Transactional
    public void avancarStatus(Integer pedidoId) {
        Pedido pedido = getPedidoById(pedidoId);
        StatusPedido proximo = PROXIMO_STATUS.get(pedido.getStatus());

        if (proximo == null) {
            throw new RuntimeException("Este pedido está no status " + pedido.getStatus().getLabel()
                    + " e não pode mais avançar.");
        }

        pedido.setStatus(proximo);
        pedidoRepository.save(pedido);
    }

    @Override
    @Transactional
    public void recusarPedido(Integer pedidoId, String motivo) {
        Pedido pedido = getPedidoById(pedidoId);

        if (pedido.getStatus() != StatusPedido.AGUARDANDO_ENTREGA) {
            throw new RuntimeException("Só é possível recusar um pedido que esteja aguardando entrega.");
        }

        if (!StringUtils.hasText(motivo)) {
            throw new RuntimeException("Informe o motivo da recusa.");
        }

        pedido.setStatus(StatusPedido.RECUSADO);
        pedido.setMotivoRecusa(motivo);
        pedidoRepository.save(pedido);
    }

    @Override
    @Transactional
    public void atualizarObservacao(Integer pedidoId, String observacao) {
        // Observação é uma anotação livre do atendente — pode ser adicionada
        // ou alterada em qualquer status do pedido, já que não afeta valores
        // nem o fluxo do pedido.
        Pedido pedido = getPedidoById(pedidoId);
        pedido.setObservacao(StringUtils.hasText(observacao) ? observacao : null);
        pedidoRepository.save(pedido);
    }

    private void verificarPedidoEditavel(Pedido pedido) {
        if (!pedido.isEditavel()) {
            throw new RuntimeException("Este pedido não pode mais ser editado (status atual: "
                    + pedido.getStatus().getLabel() + "). A edição só é permitida até a confirmação do pagamento.");
        }
    }
}
