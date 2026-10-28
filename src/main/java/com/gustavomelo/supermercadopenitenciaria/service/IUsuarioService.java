package com.gustavomelo.supermercadopenitenciaria.service;

import com.gustavomelo.supermercadopenitenciaria.model.Usuario;

import java.util.List;

public interface IUsuarioService {
    Integer saveUsuario(Usuario usuario);
    List<Usuario> getAllUsuarios();
    Usuario getUsuarioById(Integer id);
    void deleteUsuarioById(Integer id);
}
