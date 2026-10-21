package com.gustavomelo.supermercadopenitenciaria.impl;

import com.gustavomelo.supermercadopenitenciaria.model.Categoria;
import com.gustavomelo.supermercadopenitenciaria.repository.CategoriaRepository;
import com.gustavomelo.supermercadopenitenciaria.repository.ProdutoRepository;
import com.gustavomelo.supermercadopenitenciaria.service.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl implements ICategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Override
    public Integer saveCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria).getId();
    }

    @Override
    public List<Categoria> getAllCategorias() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categoria getCategoriaById(Integer id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada: " + id));
    }

    @Override
    public void deleteCategoriaById(Integer id) {
        Categoria categoria = getCategoriaById(id);
        if (produtoRepository.existsByCategoria(categoria)) {
            throw new RuntimeException("Não é possível excluir esta categoria pois existem produtos associados a ela.");
        }
        categoriaRepository.deleteById(id);
    }
}
