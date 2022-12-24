package com.supermarket.util;

import com.supermarket.data.dto.InformacaoItemPedidoDTO;
import com.supermarket.data.dto.InformacoesPedidoDTO;
import com.supermarket.data.entity.ItemPedido;
import com.supermarket.data.entity.Pedido;
import org.springframework.util.CollectionUtils;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ConverterObjeto {

    public static InformacoesPedidoDTO converter (Pedido pedido) {
        return InformacoesPedidoDTO
                .builder()
                .id(pedido.getId())
                .itens(converter(pedido.getItens()))
                .build();
    }

    public static List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens) {
        if(CollectionUtils.isEmpty(itens)) {
            return Collections.emptyList();
        }

        List<InformacaoItemPedidoDTO> infoItens = itens.stream().map(
                item -> InformacaoItemPedidoDTO
                        .builder().produto(item.getProduct().getNome())
                        .descricao(item.getProduct().getDescricao())
                        .quantidade(item.getQuantidade())
                        .posicao(item.getPosicao())
                        .build()
        ).collect(Collectors.toList());


        return infoItens;
    }


}
