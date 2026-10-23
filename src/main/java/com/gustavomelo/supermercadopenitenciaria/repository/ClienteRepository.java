package com.gustavomelo.supermercadopenitenciaria.repository;

import com.gustavomelo.supermercadopenitenciaria.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
