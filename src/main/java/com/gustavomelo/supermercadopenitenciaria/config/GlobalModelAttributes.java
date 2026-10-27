package com.gustavomelo.supermercadopenitenciaria.config;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Deixa disponíveis, em toda tela renderizada por um @Controller, quem é o
 * usuário logado e se ele é Administrador — usado na navbar (fragments/head)
 * para mostrar o nome, o botão de sair, e esconder o link de Usuários de
 * quem não é Administrador.
 */
@ControllerAdvice
public class GlobalModelAttributes {

    @ModelAttribute("usuarioLogado")
    public String usuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        return authentication.getName();
    }

    @ModelAttribute("isAdministrador")
    public boolean isAdministrador() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMINISTRADOR"));
    }
}
