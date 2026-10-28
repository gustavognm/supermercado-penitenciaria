package com.gustavomelo.supermercadopenitenciaria.controller;

import com.gustavomelo.supermercadopenitenciaria.model.Perfil;
import com.gustavomelo.supermercadopenitenciaria.model.Usuario;
import com.gustavomelo.supermercadopenitenciaria.service.IUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/usuario")
    public String listUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.getAllUsuarios());
        return "usuario/list";
    }

    @GetMapping("/usuario/create")
    public String showCreateForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("perfis", Perfil.values());
        return "usuario/form";
    }

    @GetMapping("/usuario/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Usuario usuario = usuarioService.getUsuarioById(id);
        // A senha (já criptografada) nunca é enviada de volta pro formulário.
        usuario.setSenha(null);
        model.addAttribute("usuario", usuario);
        model.addAttribute("perfis", Perfil.values());
        return "usuario/form";
    }

    @PostMapping("/usuario/save")
    public String saveUsuario(@Valid @ModelAttribute Usuario usuario, BindingResult result, Model model) {
        if (usuario.getId() == null && !StringUtils.hasText(usuario.getSenha())) {
            model.addAttribute("erro", "A senha é obrigatória para um novo usuário.");
            model.addAttribute("perfis", Perfil.values());
            return "usuario/form";
        }

        if (result.hasErrors()) {
            model.addAttribute("perfis", Perfil.values());
            return "usuario/form";
        }

        try {
            usuarioService.saveUsuario(usuario);
        } catch (RuntimeException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("perfis", Perfil.values());
            return "usuario/form";
        }

        return "redirect:/usuario";
    }

    @GetMapping("/usuario/delete/{id}")
    public String deleteUsuario(@PathVariable Integer id, RedirectAttributes redirect) {
        try {
            usuarioService.deleteUsuarioById(id);
        } catch (RuntimeException e) {
            redirect.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/usuario";
    }
}
