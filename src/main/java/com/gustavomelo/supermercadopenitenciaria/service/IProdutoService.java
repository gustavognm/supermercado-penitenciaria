package com.gustavomelo.supermercadopenitenciaria.service;

import com.gustavomelo.supermercadopenitenciaria.model.Produto;

import java.util.List;

public interface IProdutoService {
    Integer saveProduto(Produto produto);
    List<Produto> getAllProdutos();
    Produto getProdutoById(Integer id);
    void deleteProdutoById(Integer id);
}
