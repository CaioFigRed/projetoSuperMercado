package com.supermarket.presentation.controller;

import com.supermarket.data.dto.InformacoesPedidoDTO;

import com.supermarket.data.dto.PedidoDTO;
import com.supermarket.data.dto.PedidoUpdateDTO;
import com.supermarket.data.entity.Pedido;
import com.supermarket.domain.service.impl.PedidoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoServiceImpl service;

    @PostMapping
    @ResponseStatus(CREATED)
    public InformacoesPedidoDTO save (@RequestBody PedidoDTO dto) {
        Pedido pedido = service.save(dto);
        return service.obterPedidoCompleto(pedido.getId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    @GetMapping("/{id}")
    public InformacoesPedidoDTO find(@PathVariable Integer id) {
        return service
                .obterPedidoCompleto(id);

    }

    @PatchMapping("/{id}")
    @ResponseStatus(ACCEPTED)
    public InformacoesPedidoDTO update(@PathVariable Integer id, @RequestBody PedidoUpdateDTO dto) {
         service.update(id, dto);
         return service.obterPedidoCompleto(id);
    }

    @DeleteMapping("/produto")
    @ResponseStatus(NO_CONTENT)
    public void deleteItemPedido(Integer id, Integer posicao) {
        service.deleteItemPedido(id, posicao);
    }
}
