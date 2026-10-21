package com.gustavomelo.supermercadopenitenciaria.repository;

import com.gustavomelo.supermercadopenitenciaria.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}
