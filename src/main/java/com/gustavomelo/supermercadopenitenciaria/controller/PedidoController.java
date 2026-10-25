package com.gustavomelo.supermercadopenitenciaria.controller;

import com.gustavomelo.supermercadopenitenciaria.model.Pedido;
import com.gustavomelo.supermercadopenitenciaria.model.StatusPedido;
import com.gustavomelo.supermercadopenitenciaria.model.TipoLocalizacao;
import com.gustavomelo.supermercadopenitenciaria.service.IClienteService;
import com.gustavomelo.supermercadopenitenciaria.service.IDetentoService;
import com.gustavomelo.supermercadopenitenciaria.service.IPedidoService;
import com.gustavomelo.supermercadopenitenciaria.service.IProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PedidoController {

    @Autowired
    private IPedidoService pedidoService;

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IDetentoService detentoService;

    @Autowired
    private IProdutoService produtoService;

    @GetMapping("/pedido")
    public String listPedidos(@RequestParam(defaultValue = "ALA") TipoLocalizacao tipo, Model model) {
        // Pedidos das Alas e dos Pavilhões são separados em abas porque a
        // entrega para cada um acontece em dias diferentes da semana. Aqui só
        // entram os pedidos em andamento — os finalizados (Entregue/Recusado)
        // vão para a tela de histórico, para não poluir o acompanhamento do
        // atendente.
        model.addAttribute("pedidos", pedidoService.getPedidosAtivosPorTipoLocalizacao(tipo));
        model.addAttribute("tipoAtivo", tipo);
        return "pedido/list";
    }

    @GetMapping("/pedido/historico")
    public String historicoPedidos(@RequestParam(defaultValue = "ALA") TipoLocalizacao tipo, Model model) {
        model.addAttribute("pedidos", pedidoService.getPedidosFinalizadosPorTipoLocalizacao(tipo));
        model.addAttribute("tipoAtivo", tipo);
        return "pedido/historico";
    }

    @GetMapping("/pedido/create")
    public String showCreateForm(Model model) {
        model.addAttribute("pedido", new Pedido());
        model.addAttribute("clientes", clienteService.getAllClientes());
        model.addAttribute("detentos", detentoService.getAllDetentos());
        return "pedido/form";
    }

    @GetMapping("/pedido/view/{id}")
    public String viewPedido(@PathVariable Integer id, Model model) {
        model.addAttribute("pedido", pedidoService.getPedidoById(id));
        model.addAttribute("produtos", produtoService.getAllProdutos());
        return "pedido/view";
    }

    @PostMapping("/pedido/save")
    public String savePedido(@ModelAttribute Pedido pedido, Model model) {
        boolean semCliente = pedido.getCliente() == null || pedido.getCliente().getId() == null;
        boolean semDetento = pedido.getDetento() == null || pedido.getDetento().getId() == null;

        if (semCliente || semDetento) {
            model.addAttribute("clientes", clienteService.getAllClientes());
            model.addAttribute("detentos", detentoService.getAllDetentos());
            model.addAttribute("erro", "Selecione um cliente e um detento para criar o pedido.");
            return "pedido/form";
        }

        Integer id = pedidoService.criarPedido(pedido);
        return "redirect:/pedido/view/" + id;
    }

    @GetMapping("/pedido/delete/{id}")
    public String deletePedido(@PathVariable Integer id, RedirectAttributes redirect) {
        TipoLocalizacao tipo = TipoLocalizacao.ALA;
        boolean finalizado = false;
        try {
            Pedido pedido = pedidoService.getPedidoById(id);
            tipo = pedido.getDetento().getTipoLocalizacao();
            finalizado = pedido.getStatus() == StatusPedido.ENTREGUE || pedido.getStatus() == StatusPedido.RECUSADO;
            pedidoService.deletePedidoById(id);
        } catch (RuntimeException e) {
            redirect.addFlashAttribute("erro", e.getMessage());
        }
        String destino = finalizado ? "/pedido/historico" : "/pedido";
        return "redirect:" + destino + "?tipo=" + tipo;
    }

    @PostMapping("/pedido/{id}/itens/add")
    public String addItem(@PathVariable Integer id,
                           @RequestParam Integer produtoId,
                           @RequestParam Integer quantidade,
                           RedirectAttributes redirect) {
        try {
            pedidoService.adicionarItem(id, produtoId, quantidade);
        } catch (RuntimeException e) {
            redirect.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/pedido/view/" + id;
    }

    @GetMapping("/pedido/{id}/itens/delete/{itemId}")
    public String removeItem(@PathVariable Integer id,
                              @PathVariable Integer itemId,
                              RedirectAttributes redirect) {
        try {
            pedidoService.removerItem(id, itemId);
        } catch (RuntimeException e) {
            redirect.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/pedido/view/" + id;
    }

    @PostMapping("/pedido/{id}/status/avancar")
    public String avancarStatus(@PathVariable Integer id, RedirectAttributes redirect) {
        try {
            pedidoService.avancarStatus(id);
        } catch (RuntimeException e) {
            redirect.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/pedido/view/" + id;
    }

    @PostMapping("/pedido/{id}/status/recusar")
    public String recusarPedido(@PathVariable Integer id,
                                 @RequestParam String motivo,
                                 RedirectAttributes redirect) {
        try {
            pedidoService.recusarPedido(id, motivo);
        } catch (RuntimeException e) {
            redirect.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/pedido/view/" + id;
    }

    @PostMapping("/pedido/{id}/observacao")
    public String salvarObservacao(@PathVariable Integer id,
                                    @RequestParam(required = false) String observacao,
                                    RedirectAttributes redirect) {
        try {
            pedidoService.atualizarObservacao(id, observacao);
        } catch (RuntimeException e) {
            redirect.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/pedido/view/" + id;
    }
}
