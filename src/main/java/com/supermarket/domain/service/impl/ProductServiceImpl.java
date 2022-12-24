package com.supermarket.domain.service.impl;

import com.supermarket.data.dto.ProductDTO;
import com.supermarket.data.entity.Product;
import com.supermarket.data.repository.ProductRepository;
import com.supermarket.util.Salvar;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Where;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ProductServiceImpl {

    private final ProductRepository repository;

    public Product save(ProductDTO dto) {
        Product product = new Product();
        product.setNome(dto.getNome());
        product.setDescricao(dto.getDescricao());
        return repository.save(product);
    }



    public Product update(Integer id, ProductDTO dto) {
        Product product = new Product();
        product.setNome(dto.getNome());
        product.setDescricao(dto.getDescricao());

        repository
                .findById(id)
                . map ( ExistingProduct -> {
                    product.setId(ExistingProduct.getId());
                    repository.save(product);
                    return ExistingProduct;
                }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "produto não encontrado"));
        return product;
    }

    public List<Product> find(Product filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }

    public Product saveImage(Integer id, MultipartFile file) {
        Product product = repository.findById(id)
                .map(p -> {
                    p.setId(id);
                    p.setImagem(Salvar.saveImage(file));
                    repository.save(p);
                    return p;
                }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Produto não encontrado"));
        return product;
    }

    public void delete(Integer id) {
        Product product = repository.findById(id).orElseThrow();
        product.setActive(false);
        repository.save(product);
    }

    @Transactional
    public void ativar(Integer id) {
        repository.ativar(id);
    }
}
