package com.gustavomelo.supermercadopenitenciaria.controller;

import com.gustavomelo.supermercadopenitenciaria.model.Categoria;
import com.gustavomelo.supermercadopenitenciaria.service.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CategoriaController {

    @Autowired
    private ICategoriaService categoriaService;

    @GetMapping("/categoria")
    public String listCategorias(Model model) {
        model.addAttribute("categorias", categoriaService.getAllCategorias());
        return "categoria/list";
    }

    @GetMapping("/categoria/create")
    public String showCreateForm(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categoria/form";
    }

    @GetMapping("/categoria/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        model.addAttribute("categoria", categoriaService.getCategoriaById(id));
        return "categoria/form";
    }

    @PostMapping("/categoria/save")
    public String saveCategoria(@Valid @ModelAttribute Categoria categoria, BindingResult result) {
        if (result.hasErrors()) {
            return "categoria/form";
        }
        categoriaService.saveCategoria(categoria);
        return "redirect:/categoria";
    }

    @GetMapping("/categoria/delete/{id}")
    public String deleteCategoria(@PathVariable Integer id, RedirectAttributes redirect) {
        try {
            categoriaService.deleteCategoriaById(id);
        } catch (RuntimeException e) {
            redirect.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/categoria";
    }
}
