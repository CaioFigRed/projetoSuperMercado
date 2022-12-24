package com.supermarket.presentation.controller;

import com.supermarket.data.dto.ProductDTO;
import com.supermarket.data.entity.Product;
import com.supermarket.domain.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductServiceImpl service;

    @PostMapping
    @ResponseStatus(CREATED)
    public Product save(ProductDTO dto) {
        return (Product) service.save(dto);
    }

    @PatchMapping("/{id}/desativar")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    @PatchMapping("/{id}/ativar")
    @ResponseStatus(NO_CONTENT)
    public void ativar(@PathVariable Integer id) {
        service.ativar(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(ACCEPTED)
    public Product update(@PathVariable Integer id, ProductDTO dto) {
        return (Product) service.update(id, dto);
    }

    @GetMapping
    public List<Product> find(Product filtro) {
        return service.find(filtro);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public Product saveImage(@PathVariable Integer id, MultipartFile file) {
        return service.saveImage(id, file);
    }


}
