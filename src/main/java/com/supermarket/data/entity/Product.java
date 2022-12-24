package com.supermarket.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@SQLDelete(sql = "UPDATE table_product SET is_active = false WHERE id=?")
@Where(clause="is_active=true")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String nome;

    @Column
    private String descricao;

    @Column
    private String Imagem;

    @Column(name="is_active")
    @JsonIgnore
    private Boolean active = true;
}
