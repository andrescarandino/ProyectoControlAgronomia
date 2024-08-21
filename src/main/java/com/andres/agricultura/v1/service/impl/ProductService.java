package com.andres.agricultura.v1.service.impl;

import java.util.List;

import com.andres.agricultura.v1.Exceptions.NotFoundException;
import com.andres.agricultura.v1.dto.ProductDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andres.agricultura.v1.entities.Product;
import com.andres.agricultura.v1.repository.ProductRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService  {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ObjectMapper mapper;

    @Transactional
    public ProductDto save(ProductDto productdto) {
        Product product = mapper.convertValue(productdto, Product.class);
        return mapper.convertValue(productRepository.save(product), ProductDto.class);
    }

    @Transactional(readOnly = true)
    public List<ProductDto> list() {
        return productRepository.findAll().stream()
                .map(product -> mapper.convertValue(product, ProductDto.class))
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductDto findById(Long id) {
        return mapper.convertValue(productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id=" +id))
                , ProductDto.class);
    }

    @Transactional
    public void delete(Long id) {
        productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id=" +id));
        productRepository.deleteById(id);
    }

    @Transactional
    public ProductDto update(ProductDto productDto) {
        Product product = mapper.convertValue(productDto, Product.class);
        Product productBD = productRepository.findById(product.getId())
                .orElseThrow(() -> new NotFoundException("Product with id=" +productDto.getId()+" not exist"));

        productBD.setName(product.getName());
        productBD.setDose(product.getDose());
        productBD.setPriceHectare(product.getPriceHectare());

        return mapper.convertValue(productRepository.save(productBD), ProductDto.class);
    }

}
