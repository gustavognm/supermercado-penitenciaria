package com.gustavomelo.supermercadopenitenciaria.impl;

import com.gustavomelo.supermercadopenitenciaria.model.Produto;
import com.gustavomelo.supermercadopenitenciaria.repository.ProdutoRepository;
import com.gustavomelo.supermercadopenitenciaria.service.IProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoServiceImpl implements IProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    // TODO: quando a fatia de Pedido/ItemPedido existir, injetar ItemPedidoRepository
    // aqui e impedir a exclusão de um produto que já esteja associado a algum
    // item de pedido (mesmo padrão usado no projeto anterior para Produto x ItemVenda).

    @Override
    public Integer saveProduto(Produto produto) {
        return produtoRepository.save(produto).getId();
    }

    @Override
    public List<Produto> getAllProdutos() {
        return produtoRepository.findAll();
    }

    @Override
    public Produto getProdutoById(Integer id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + id));
    }

    @Override
    public void deleteProdutoById(Integer id) {
        Produto produto = getProdutoById(id);
        produtoRepository.deleteById(produto.getId());
    }
}
