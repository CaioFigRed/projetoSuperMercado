package com.supermarket.domain.service.impl;

import com.supermarket.data.dto.*;
import com.supermarket.data.entity.ItemPedido;
import com.supermarket.data.entity.Pedido;
import com.supermarket.data.entity.Product;
import com.supermarket.data.repository.ItemPedidoRepository;
import com.supermarket.data.repository.PedidoRepository;
import com.supermarket.data.repository.ProductRepository;
import com.supermarket.util.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static com.supermarket.util.ConverterObjeto.converter;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl {


    private final PedidoRepository pedidoRepository;
    private final ProductRepository productRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    public Pedido save(PedidoDTO dto) {

        Pedido pedido = new Pedido();
        pedido.setItens(convertItems(pedido, dto.getItens()));
        pedidoRepository.save(pedido);
        for(int i = 0; i < pedido.getItens().size(); i++) {
            ItemPedido item = pedido.getItens().get(i);
            item.setPosicao(i+1);
            itemPedidoRepository.save(item);
        }
        return pedido;
    }

    public InformacoesPedidoDTO obterPedidoCompleto(Integer id) {
        return pedidoRepository.findByIdFetchItens(id).map( p -> converter(p))
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Pedido não encontrado"));
    }

    private List<ItemPedido> convertItems(Pedido pedido, List<ItemPedidoDTO> items) {
        if(items.isEmpty()) {
            throw new RegraNegocioException("Não é possível realizar um pedido sem itens.");
        }

        return items
                .stream()
                .map( dto -> {
                    Integer idProduto = dto.getProduto();
                    Product product = productRepository
                            .findById(idProduto)
                            .orElseThrow(
                                    () -> new RegraNegocioException("Código de produto inválido: " + idProduto)
                            );
                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduct(product);
                    return itemPedido;
                }).collect(Collectors.toList());
    }

    public void delete(Integer id) {
         pedidoRepository.findByIdFetchItens(id)
                 .map( p -> {
                     pedidoRepository.delete(p);
                     return p;
                 })
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Pedido não encontrado"));


    }


    public Pedido update(Integer id, PedidoUpdateDTO dto) {

        Pedido pedido = pedidoRepository.findById(id).orElseThrow();

        ItemPedido itemPedido = itemPedidoRepository.findByProduct_idLike(dto.getItens().get(0).getProduto());
        if(itemPedido != null) {
            Integer quantidade = itemPedido.getQuantidade() + dto.getItens().get(0).getQuantidade();
            itemPedido.setQuantidade(quantidade);
            itemPedidoRepository.save(itemPedido);
        } else  {
            pedido.getItens().addAll(convertItems(pedido, dto.getItens()));
        }


        for(int i = 0; i < pedido.getItens().size(); i++) {
            ItemPedido item = pedido.getItens().get(i);
            item.setPosicao(i+1);
            itemPedidoRepository.save(item);
        }
        pedidoRepository.save(pedido);
        return pedido;


    }


    @Transactional
    public void deleteItemPedido(Integer id, Integer posicao) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow();
        itemPedidoRepository.deleteByPosition(id, posicao);

        for(int i = 0; i < pedido.getItens().size(); i++) {
            ItemPedido item = pedido.getItens().get(i);
            item.setPosicao(i+1);
            itemPedidoRepository.save(item);
        }

    }


}
