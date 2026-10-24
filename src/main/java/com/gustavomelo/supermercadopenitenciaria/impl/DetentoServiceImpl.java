package com.gustavomelo.supermercadopenitenciaria.impl;

import com.gustavomelo.supermercadopenitenciaria.model.Detento;
import com.gustavomelo.supermercadopenitenciaria.repository.DetentoRepository;
import com.gustavomelo.supermercadopenitenciaria.service.IDetentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetentoServiceImpl implements IDetentoService {

    @Autowired
    private DetentoRepository detentoRepository;

    // TODO: quando a fatia de Pedido existir, injetar PedidoRepository aqui
    // e impedir a exclusão de um detento que já tenha pedidos associados.

    @Override
    public Integer saveDetento(Detento detento) {
        return detentoRepository.save(detento).getId();
    }

    @Override
    public List<Detento> getAllDetentos() {
        return detentoRepository.findAll();
    }

    @Override
    public Detento getDetentoById(Integer id) {
        return detentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detento não encontrado: " + id));
    }

    @Override
    public void deleteDetentoById(Integer id) {
        Detento detento = getDetentoById(id);
        detentoRepository.deleteById(detento.getId());
    }
}
