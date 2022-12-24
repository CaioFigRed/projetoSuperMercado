package com.supermarket.data.repository;

import com.supermarket.data.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {

    @Transactional
    @Modifying
    @Query(value = "DELETE ITEM_PEDIDO WHERE POSICAO = :posicao AND PEDIDO_ID = :id", nativeQuery = true)
    void deleteByPosition(Integer id, Integer posicao);

    @Transactional
    @Modifying
    @Query(value = "UPDATE ITEM_PEDIDO SET QUANTIDADE = :quantidade WHERE PRODUCT_ID = :id",nativeQuery = true)
    void somarQuantidade(Integer quantidade, Integer id);

    ItemPedido findByProduct_idLike(Integer id);
}
