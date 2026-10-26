package com.gustavomelo.supermercadopenitenciaria.controller;

import com.gustavomelo.supermercadopenitenciaria.model.Pedido;
import com.gustavomelo.supermercadopenitenciaria.model.StatusPedido;
import com.gustavomelo.supermercadopenitenciaria.model.TipoLocalizacao;
import com.gustavomelo.supermercadopenitenciaria.service.IPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private IPedidoService pedidoService;

    @GetMapping("/")
    public String home(Model model) {
        adicionarEstatisticas(model);
        return "index";
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        adicionarEstatisticas(model);
        return "index";
    }

    /**
     * Painel de estatísticas da tela inicial — um retrato rápido dos
     * pedidos em andamento, para o atendente ter uma visão geral do que
     * precisa de atenção sem entrar na listagem de Pedidos.
     */
    private void adicionarEstatisticas(Model model) {
        List<Pedido> pedidosAtivos = pedidoService.getPedidosAtivos();

        long totalAlas = pedidosAtivos.stream()
                .filter(p -> p.getDetento().getTipoLocalizacao() == TipoLocalizacao.ALA)
                .count();

        long totalPavilhoes = pedidosAtivos.stream()
                .filter(p -> p.getDetento().getTipoLocalizacao() == TipoLocalizacao.PAVILHAO)
                .count();

        long totalAguardandoEntrega = pedidosAtivos.stream()
                .filter(p -> p.getStatus() == StatusPedido.AGUARDANDO_ENTREGA)
                .count();

        model.addAttribute("totalPedidosAtivos", pedidosAtivos.size());
        model.addAttribute("totalAlas", totalAlas);
        model.addAttribute("totalPavilhoes", totalPavilhoes);
        model.addAttribute("totalAguardandoEntrega", totalAguardandoEntrega);
    }
}
