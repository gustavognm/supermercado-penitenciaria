package com.gustavomelo.supermercadopenitenciaria.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Login obrigatório para todo o sistema, com dois perfis:
 * - ATENDENTE: acesso a Categoria, Produto, Cliente, Detento e Pedido.
 * - ADMINISTRADOR: acesso a tudo que o Atendente tem, mais o CRUD de
 *   Usuários (rotas /usuario/**).
 * A autenticação usa CustomUserDetailsService, que busca o Usuario pelo
 * e-mail e converte o perfil dele numa authority "ROLE_...".
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    /**
     * Usado para criptografar (e verificar) a senha dos usuários — nunca
     * guardamos a senha em texto puro no banco.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/css/**", "/images/**").permitAll()
                .requestMatchers("/usuario/**").hasRole("ADMINISTRADOR")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("senha")
                .defaultSuccessUrl("/", false)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }
}
