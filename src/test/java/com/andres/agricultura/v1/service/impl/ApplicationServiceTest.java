package com.andres.agricultura.v1.service.impl;

import com.andres.agricultura.v1.Exceptions.BadRequestException;
import com.andres.agricultura.v1.Exceptions.NotFoundException;
import com.andres.agricultura.v1.dto.ApplicationDto;
import com.andres.agricultura.v1.dto.ProductDto;
import com.andres.agricultura.v1.entities.Application;
import com.andres.agricultura.v1.entities.Product;
import com.andres.agricultura.v1.repository.ApplicationRepository;
import com.andres.agricultura.v1.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceTest {

    @Mock
    ApplicationRepository applicationRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    ObjectMapper mapper;

    @InjectMocks
    private ApplicationService applicationService;
    private ApplicationDto applicationDto;
    private Application application;
    private Product product;
    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        // Inicializa tus objetos de prueba aquí
        product = new Product();
        product.setId(1L);
        productDto = new ProductDto();
        productDto.setId(1L);

        application = new Application();
        application.addProduct(product);
        application.setId(1L);

        applicationDto = new ApplicationDto();
        applicationDto.setProducts(Set.of(productDto));
        applicationDto.setId(1L);
    }

    @Test
    void testSaveApplication_Success() {
        // Configura el comportamiento de los mocks
        when(mapper.convertValue(applicationDto, Application.class)).thenReturn(application);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(applicationRepository.save(any(Application.class))).thenReturn(application);
        when(mapper.convertValue(application, ApplicationDto.class)).thenReturn(applicationDto);

        // Ejecuta el método y verifica los resultados
        ApplicationDto result = applicationService.save(applicationDto);

        assertEquals(applicationDto, result);
    }

    @Test
    void testSaveApplication_ProductNotFound() {
        // Configura el comportamiento de los mocks para lanzar una excepción
        when(mapper.convertValue(applicationDto, Application.class)).thenReturn(application);
        when(productRepository.findById(1L)).thenReturn(Optional.empty());


        assertThrows(BadRequestException.class, () -> {
            applicationService.save(applicationDto);
        });
    }

    @Test
    void testListApplication_Success() {

        when(applicationRepository.findAll()).thenReturn(List.of(application));
        when(mapper.convertValue(application, ApplicationDto.class)).thenReturn(applicationDto);

        List<ApplicationDto> result = applicationService.list();
        assertEquals(1, result.size());
        assertEquals(applicationDto, result.get(0));
    }

    @Test
    void testFindById_Success() {
        when(applicationRepository.findById(1L)).thenReturn(Optional.of(application));
        when(mapper.convertValue(application, ApplicationDto.class)).thenReturn(applicationDto);

        ApplicationDto result = applicationService.findById(1L);

        assertEquals(1L, result.getId());
        assertEquals(ApplicationDto.class, result.getClass());
    }

    @Test
    void testFindById_NotFoundException() {
        when(applicationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,() -> {
            applicationService.findById(1L);
        });
    }

    @Test
    void testDelete_Success() {
        when(applicationRepository.findById(1L)).thenReturn(Optional.of(application));

        applicationService.delete(1L);

        verify(applicationRepository, times(2)).findById(1L);
        verify(applicationRepository, times(1)).deleteById(1L);


        assertEquals(0, application.getProducts().size());
    }
    @Test
    void testDelete_NotFoundException() {
        when(applicationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            applicationService.delete(1L);
        });

        verify(applicationRepository, times(1)).findById(1L);
        verify(applicationRepository, times(0)).deleteById(1L);

    }
    @Test
    void testUpdate_Success() {
        when(mapper.convertValue(applicationDto, Application.class)).thenReturn(application);
        when(applicationRepository.findById(1L)).thenReturn(Optional.of(application));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(applicationRepository.save(any(Application.class))).thenReturn(application);
        when(mapper.convertValue(application, ApplicationDto.class)).thenReturn(applicationDto);

        ApplicationDto result = applicationService.update(applicationDto);

        assertEquals(applicationDto, result);

        verify(applicationRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).findById(anyLong());
        verify(applicationRepository, times(1)).save(application);

    }

    @Test
    void testUpdate_NotFoundException() {
        when(mapper.convertValue(applicationDto, Application.class)).thenReturn(application);
        when(applicationRepository.findById(1L)).thenReturn(Optional.empty());


        assertThrows(NotFoundException.class, () -> {
            applicationService.update(applicationDto);
        });

        verify(productRepository, times(0)).findById(anyLong());
        verify(applicationRepository, times(0)).save(application);

    }

    @Test
    void testUpdate_BadRequestException() {
        when(mapper.convertValue(applicationDto, Application.class)).thenReturn(application);
        when(applicationRepository.findById(1L)).thenReturn(Optional.of(application));
        when(productRepository.findById(1L)).thenReturn(Optional.empty());


        assertThrows(BadRequestException.class, () -> {
            applicationService.update(applicationDto);
        });

        verify(applicationRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).findById(anyLong());
        verify(applicationRepository, times(0)).save(application);

    }
}