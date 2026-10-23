package com.gustavomelo.supermercadopenitenciaria.service;

import com.gustavomelo.supermercadopenitenciaria.model.Cliente;

import java.util.List;

public interface IClienteService {
    Integer saveCliente(Cliente cliente);
    List<Cliente> getAllClientes();
    Cliente getClienteById(Integer id);
    void deleteClienteById(Integer id);
}
