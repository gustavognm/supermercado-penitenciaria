package com.gustavomelo.supermercadopenitenciaria.security;

import com.gustavomelo.supermercadopenitenciaria.model.Usuario;
import com.gustavomelo.supermercadopenitenciaria.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Ponte entre o Usuario (nosso model) e o Spring Security: busca o usuário
 * pelo e-mail e converte o perfil dele numa authority no formato que o
 * Spring Security espera ("ROLE_ADMINISTRADOR" / "ROLE_ATENDENTE").
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("E-mail ou senha inválidos."));

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                .authorities("ROLE_" + usuario.getPerfil().name())
                .build();
    }
}
