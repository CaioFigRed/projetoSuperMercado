package com.supermarket.data.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformacaoItemPedidoDTO {

    private Integer posicao;
    private String produto;
    private String descricao;
    private Integer quantidade;

}
