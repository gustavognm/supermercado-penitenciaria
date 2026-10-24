package com.gustavomelo.supermercadopenitenciaria.controller;

import com.gustavomelo.supermercadopenitenciaria.model.Detento;
import com.gustavomelo.supermercadopenitenciaria.model.TipoLocalizacao;
import com.gustavomelo.supermercadopenitenciaria.service.IDetentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DetentoController {

    @Autowired
    private IDetentoService detentoService;

    @GetMapping("/detento")
    public String listDetentos(Model model) {
        model.addAttribute("detentos", detentoService.getAllDetentos());
        return "detento/list";
    }

    @GetMapping("/detento/create")
    public String showCreateForm(Model model) {
        model.addAttribute("detento", new Detento());
        model.addAttribute("tiposLocalizacao", TipoLocalizacao.values());
        return "detento/form";
    }

    @GetMapping("/detento/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        model.addAttribute("detento", detentoService.getDetentoById(id));
        model.addAttribute("tiposLocalizacao", TipoLocalizacao.values());
        return "detento/form";
    }

    @PostMapping("/detento/save")
    public String saveDetento(@Valid @ModelAttribute Detento detento, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("tiposLocalizacao", TipoLocalizacao.values());
            return "detento/form";
        }
        detentoService.saveDetento(detento);
        return "redirect:/detento";
    }

    @GetMapping("/detento/delete/{id}")
    public String deleteDetento(@PathVariable Integer id, RedirectAttributes redirect) {
        try {
            detentoService.deleteDetentoById(id);
        } catch (RuntimeException e) {
            redirect.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/detento";
    }
}
