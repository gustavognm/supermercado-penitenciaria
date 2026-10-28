package com.gustavomelo.supermercadopenitenciaria.impl;

import com.gustavomelo.supermercadopenitenciaria.model.Usuario;
import com.gustavomelo.supermercadopenitenciaria.repository.UsuarioRepository;
import com.gustavomelo.supermercadopenitenciaria.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Integer saveUsuario(Usuario usuario) {
        boolean emailEmUso = usuarioRepository.findByEmail(usuario.getEmail())
                .filter(existente -> !existente.getId().equals(usuario.getId()))
                .isPresent();
        if (emailEmUso) {
            throw new RuntimeException("Já existe um usuário cadastrado com este e-mail.");
        }

        if (usuario.getId() != null) {
            // Edição: se o campo de senha foi deixado em branco, mantém a
            // senha (já criptografada) que o usuário já tinha.
            if (!StringUtils.hasText(usuario.getSenha())) {
                Usuario existente = getUsuarioById(usuario.getId());
                usuario.setSenha(existente.getSenha());
            } else {
                usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
            }
        } else {
            if (!StringUtils.hasText(usuario.getSenha())) {
                throw new RuntimeException("A senha é obrigatória para um novo usuário.");
            }
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }

        return usuarioRepository.save(usuario).getId();
    }

    @Override
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario getUsuarioById(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + id));
    }

    @Override
    public void deleteUsuarioById(Integer id) {
        Usuario usuario = getUsuarioById(id);
        usuarioRepository.deleteById(usuario.getId());
    }
}
