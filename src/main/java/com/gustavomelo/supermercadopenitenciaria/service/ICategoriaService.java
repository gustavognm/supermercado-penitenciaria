package com.gustavomelo.supermercadopenitenciaria.service;

import com.gustavomelo.supermercadopenitenciaria.model.Categoria;

import java.util.List;

public interface ICategoriaService {
    Integer saveCategoria(Categoria categoria);
    List<Categoria> getAllCategorias();
    Categoria getCategoriaById(Integer id);
    void deleteCategoriaById(Integer id);
}
