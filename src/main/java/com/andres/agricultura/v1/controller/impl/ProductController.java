package com.andres.agricultura.v1.controller.impl;

import com.andres.agricultura.v1.dto.ProductDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RestController;

import com.andres.agricultura.v1.service.impl.ProductService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/products")
public class ProductController{

    @Autowired
    ProductService productService;
    

    @PostMapping
    public ResponseEntity<ProductDto> save (@Valid @RequestBody ProductDto productdto){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productdto));
    }


    @GetMapping()
    public ResponseEntity<List<ProductDto>> list(){
        return ResponseEntity.status(HttpStatus.OK).body(productService.list());
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findById(id));
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PutMapping
    public ResponseEntity<ProductDto> update(@Valid @RequestBody ProductDto productDto) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.update(productDto));
    }


}
