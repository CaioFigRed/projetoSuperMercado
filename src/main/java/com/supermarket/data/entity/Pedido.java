package com.supermarket.data.entity;

import javax.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @OneToMany(mappedBy = "pedido",cascade = {CascadeType.ALL})
    private List<ItemPedido> itens;

}
