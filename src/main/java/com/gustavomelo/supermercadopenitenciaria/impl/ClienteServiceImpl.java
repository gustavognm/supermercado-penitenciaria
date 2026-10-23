package com.gustavomelo.supermercadopenitenciaria.impl;

import com.gustavomelo.supermercadopenitenciaria.model.Cliente;
import com.gustavomelo.supermercadopenitenciaria.repository.ClienteRepository;
import com.gustavomelo.supermercadopenitenciaria.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements IClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // TODO: quando a fatia de Pedido existir, injetar PedidoRepository aqui
    // e impedir a exclusão de um cliente que já tenha pedidos associados
    // (mesmo padrão usado no projeto anterior para Cliente x Venda).

    @Override
    public Integer saveCliente(Cliente cliente) {
        return clienteRepository.save(cliente).getId();
    }

    @Override
    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente getClienteById(Integer id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado: " + id));
    }

    @Override
    public void deleteClienteById(Integer id) {
        Cliente cliente = getClienteById(id);
        clienteRepository.deleteById(cliente.getId());
    }
}
