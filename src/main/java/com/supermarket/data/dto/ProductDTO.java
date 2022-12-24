package com.supermarket.data.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private String nome;
    private String descricao;

}
