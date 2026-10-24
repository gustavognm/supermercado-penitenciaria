package com.gustavomelo.supermercadopenitenciaria.service;

import com.gustavomelo.supermercadopenitenciaria.model.Detento;

import java.util.List;

public interface IDetentoService {
    Integer saveDetento(Detento detento);
    List<Detento> getAllDetentos();
    Detento getDetentoById(Integer id);
    void deleteDetentoById(Integer id);
}
