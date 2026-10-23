package com.gustavomelo.supermercadopenitenciaria.controller;

import com.gustavomelo.supermercadopenitenciaria.model.Produto;
import com.gustavomelo.supermercadopenitenciaria.service.ICategoriaService;
import com.gustavomelo.supermercadopenitenciaria.service.IProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProdutoController {

    @Autowired
    private IProdutoService produtoService;

    @Autowired
    private ICategoriaService categoriaService;

    @GetMapping("/produto")
    public String listProdutos(Model model) {
        model.addAttribute("produtos", produtoService.getAllProdutos());
        return "produto/list";
    }

    @GetMapping("/produto/create")
    public String showCreateForm(Model model) {
        model.addAttribute("produto", new Produto());
        model.addAttribute("categorias", categoriaService.getAllCategorias());
        return "produto/form";
    }

    @GetMapping("/produto/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        model.addAttribute("produto", produtoService.getProdutoById(id));
        model.addAttribute("categorias", categoriaService.getAllCategorias());
        return "produto/form";
    }

    @PostMapping("/produto/save")
    public String saveProduto(@Valid @ModelAttribute Produto produto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categorias", categoriaService.getAllCategorias());
            return "produto/form";
        }
        produtoService.saveProduto(produto);
        return "redirect:/produto";
    }

    @GetMapping("/produto/delete/{id}")
    public String deleteProduto(@PathVariable Integer id, RedirectAttributes redirect) {
        try {
            produtoService.deleteProdutoById(id);
        } catch (RuntimeException e) {
            redirect.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/produto";
    }
}
