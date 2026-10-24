package com.gustavomelo.supermercadopenitenciaria.repository;

import com.gustavomelo.supermercadopenitenciaria.model.Detento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetentoRepository extends JpaRepository<Detento, Integer> {
}
