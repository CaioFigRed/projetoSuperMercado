package com.supermarket.data.repository;

import com.supermarket.data.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE PRODUCT SET IS_ACTIVE = TRUE WHERE ID = :id", nativeQuery = true)
    void ativar(Integer id);



}
