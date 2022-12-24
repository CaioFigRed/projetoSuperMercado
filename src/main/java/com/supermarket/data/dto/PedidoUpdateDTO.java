package com.supermarket.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoUpdateDTO {

    private List<ItemPedidoDTO> itens;

}
