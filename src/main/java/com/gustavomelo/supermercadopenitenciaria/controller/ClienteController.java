package com.gustavomelo.supermercadopenitenciaria.controller;

import com.gustavomelo.supermercadopenitenciaria.model.Cliente;
import com.gustavomelo.supermercadopenitenciaria.service.IClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    @GetMapping("/cliente")
    public String listClientes(Model model) {
        model.addAttribute("clientes", clienteService.getAllClientes());
        return "cliente/list";
    }

    @GetMapping("/cliente/create")
    public String showCreateForm(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "cliente/form";
    }

    @GetMapping("/cliente/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        model.addAttribute("cliente", clienteService.getClienteById(id));
        return "cliente/form";
    }

    @PostMapping("/cliente/save")
    public String saveCliente(@Valid @ModelAttribute Cliente cliente, BindingResult result) {
        if (result.hasErrors()) {
            return "cliente/form";
        }
        clienteService.saveCliente(cliente);
        return "redirect:/cliente";
    }

    @GetMapping("/cliente/delete/{id}")
    public String deleteCliente(@PathVariable Integer id, RedirectAttributes redirect) {
        try {
            clienteService.deleteClienteById(id);
        } catch (RuntimeException e) {
            redirect.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/cliente";
    }
}
