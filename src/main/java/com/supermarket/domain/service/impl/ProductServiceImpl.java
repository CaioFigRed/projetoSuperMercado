package com.supermarket.domain.service.impl;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.supermarket.data.dto.ProductDTO;
import com.supermarket.data.dto.ProdutoImagemDTO;
import com.supermarket.data.entity.Product;
import com.supermarket.data.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ProductServiceImpl {

    private final ProductRepository repository;

    @Autowired
    private AmazonS3 s3Client;

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
        Product product = repository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Produto não encontrado"));
        String imagem = file.getOriginalFilename();
        product.setImagem(imagem);
        repository.save(product);

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            s3Client.putObject("produto", imagem, file.getInputStream(), metadata);
        } catch (AmazonClientException | IOException e) {
            throw new RuntimeException("Não foi possível salvar arquivo!", e);
        }
        return product;
    }

    public String mostrarImagem(Integer id) {
        Product product = repository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Produto não encontrado"));
        ProdutoImagemDTO dto = new ProdutoImagemDTO("http://localhost:9444/ui/produto/" + product.getImagem());
        return dto.getImagem();

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
