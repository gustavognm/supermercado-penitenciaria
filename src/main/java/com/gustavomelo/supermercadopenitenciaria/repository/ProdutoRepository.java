package com.gustavomelo.supermercadopenitenciaria.repository;

import com.gustavomelo.supermercadopenitenciaria.model.Categoria;
import com.gustavomelo.supermercadopenitenciaria.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    boolean existsByCategoria(Categoria categoria);
}
