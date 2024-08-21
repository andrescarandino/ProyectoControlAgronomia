package com.andres.agricultura.v1.service.impl;

import com.andres.agricultura.v1.Exceptions.BadRequestException;
import com.andres.agricultura.v1.Exceptions.NotFoundException;
import com.andres.agricultura.v1.dto.ProductDto;
import com.andres.agricultura.v1.entities.Product;
import com.andres.agricultura.v1.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;
    @Mock
    ObjectMapper mapper;

    @InjectMocks
    private ProductService productService;
    private Product product;
    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);

        productDto = new ProductDto();
        productDto.setId(1L);
    }

    @Test
    void testProductSave_Success() {
        when(mapper.convertValue(productDto, Product.class)).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(mapper.convertValue(product, ProductDto.class)).thenReturn(productDto);

        ProductDto result = productService.save(productDto);

        assertEquals(productDto, result);
        assertEquals(ProductDto.class, result.getClass());
    }

    @Test
    void testProductList_Success() {
        when(productRepository.findAll()).thenReturn(List.of(product));
        when(mapper.convertValue(product, ProductDto.class)).thenReturn(productDto);

        List<ProductDto> result = productService.list();

        assertEquals(productDto, result.get(0));
        assertEquals(1, result.size());
    }

    @Test
    void testProductFindById_Success() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(mapper.convertValue(product, ProductDto.class)).thenReturn(productDto);

        ProductDto result = productService.findById(1L);

        assertEquals(productDto, result);
        assertEquals(1L, result.getId());
        assertEquals(ProductDto.class, result.getClass());
    }
    @Test
    void testProductFindById_NotFoundException() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
           productService.findById(1L);
        });

        verify(productRepository, times(1)).findById(1L);
        verify(mapper, times(0)).convertValue(product, ProductDto.class);
    }

    @Test
    void testProductDelete_Success() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.delete(1L);

        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }
    @Test
    void testProductDelete_NotFoundException() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            productService.delete(1L);
        });


        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(0)).deleteById(1L);
    }

    @Test
    void testProductUpdate_Success() {
        when(mapper.convertValue(productDto, Product.class)).thenReturn(product);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(mapper.convertValue(product, ProductDto.class)).thenReturn(productDto);

        ProductDto result = productService.update(productDto);

        assertEquals(productDto, result);
        assertEquals(ProductDto.class, result.getClass());

        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testProductUpdate_NotFoundException() {
        when(mapper.convertValue(productDto, Product.class)).thenReturn(product);
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            productService.update(productDto);
        });


        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(0)).save(product);
    }
}