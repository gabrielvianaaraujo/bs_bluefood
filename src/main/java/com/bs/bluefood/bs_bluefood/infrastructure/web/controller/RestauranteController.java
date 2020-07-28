package com.bs.bluefood.bs_bluefood.infrastructure.web.controller;

import java.util.List;
import java.util.NoSuchElementException;

import com.bs.bluefood.bs_bluefood.domain.restaurante.CategoriaRestauranteRepository;
import com.bs.bluefood.bs_bluefood.domain.restaurante.ItemCardapio;
import com.bs.bluefood.bs_bluefood.domain.restaurante.ItemCardapioRepository;
import com.bs.bluefood.bs_bluefood.domain.restaurante.Restaurante;
import com.bs.bluefood.bs_bluefood.domain.restaurante.RestauranteRepository;
import com.bs.bluefood.bs_bluefood.utils.SecurityUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(path = "/restaurante")
@Controller
public class RestauranteController {
    
    @Autowired
    private RestauranteRepository rr;

    @Autowired
    private CategoriaRestauranteRepository crr;

    @Autowired
    private ItemCardapioRepository itemCardapioRepository;

    @GetMapping("/home")
    public String home(){
        return "restaurante-home";
    }

    @GetMapping(path = "/pedidos")
    public String pedidos(){
        return "restaurante-pedidos";
    }

    @GetMapping(path = "/edit")
	public String edit(Model model){
		//Integer clienteId = SecurityUtils.loggedCliente().getId();
		//Cliente cliente = cr.findById(SecurityUtils.loggedCliente().getId()).orElseThrow();
		model.addAttribute("restaurante", rr.findById(SecurityUtils.loggedRestaurante().getId()).orElseThrow());
        HelperController.setEditMode(model, true);
        HelperController.addCategoriasToRequest(crr, model);
        return "restaurante-cadastro";
    }

    @GetMapping(path = "/cardapio-cadastro")
    public String cadastroCardapio(){
        return "restaurante-cardapio-cadastro";
    }

    @GetMapping(path = "/cardapio")
	public String viewComidas(Model model) {
		Integer restauranteId = SecurityUtils.loggedRestaurante().getId();
		Restaurante restaurante = rr.findById(restauranteId).orElseThrow(NoSuchElementException::new);
		model.addAttribute("restaurante", restaurante);
		
		List<ItemCardapio> itensCardapio = itemCardapioRepository.findByRestaurante_IdOrderByNome(restauranteId);
		model.addAttribute("itensCardapio", itensCardapio);
		
		model.addAttribute("itemCardapio", new ItemCardapio());
		
		return "restaurante-cardapio";
	}

    @GetMapping(path = "/cardapio/cadastrar")
    public String cadastrarItemCardapio(@RequestParam("nomeItem") String nome, @RequestParam("descricao") String descricao, @RequestParam("nomeItem") String nome){
        return "restaurante-cardapio-cadastro";
    }

    
}