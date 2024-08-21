package com.andres.agricultura.v1.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.andres.agricultura.v1.Exceptions.NotFoundException;
import com.andres.agricultura.v1.dto.ApplicationDto;
import com.andres.agricultura.v1.entities.Product;
import com.andres.agricultura.v1.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andres.agricultura.v1.Exceptions.BadRequestException;
import com.andres.agricultura.v1.entities.Application;
import com.andres.agricultura.v1.repository.ApplicationRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApplicationService {

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ObjectMapper mapper;

    @Transactional
    public ApplicationDto save(ApplicationDto applicationDto) {
        Application application = mapper.convertValue(applicationDto, Application.class);

        List<Product> products = application.getProducts().stream()
                .map(prod -> productRepository.findById(prod.getId())
                        .orElseThrow(() -> new BadRequestException("Product not found with id=" +prod.getId())))
                        .collect(Collectors.toList());

        application.getProducts().clear();
        products.forEach(application::addProduct);

        applicationRepository.save(application);

        return mapper.convertValue(application, ApplicationDto.class);
    }

    @Transactional (readOnly = true)
    public List<ApplicationDto> list() {
        return applicationRepository.findAll().stream()
                .map(application -> mapper.convertValue(application, ApplicationDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ApplicationDto findById(Long id) {
       return mapper.convertValue(applicationRepository.findById(id).
                       orElseThrow(() -> new NotFoundException("Application with id:" + id + " not found"))
               , ApplicationDto.class);
    }

    @Transactional
    public void delete(Long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() ->new NotFoundException("Application with id:" +id+ " not found"));
        applicationRepository.findById(id).get().getProducts().forEach(product -> {
                application.removeProduct(product);
            });
        applicationRepository.deleteById(id);
    }

    @Transactional
    public ApplicationDto update(ApplicationDto applicationDTO) {
        Application application = mapper.convertValue(applicationDTO, Application.class);
        Application applicationDB = applicationRepository.findById(application.getId())
                .orElseThrow(() -> new NotFoundException("Application not found with id " + application.getId()));

        applicationDB.setActivity(application.getActivity());
        applicationDB.setDate(application.getDate());
        applicationDB.setObservation(application.getObservation());

        Set<Product> newProducts = application.getProducts().stream()
                .map(prod -> productRepository.findById(prod.getId())
                        .orElseThrow(() -> new BadRequestException("Product not found with id=" + prod.getId())))
                .collect(Collectors.toSet());

        applicationDB.getProducts().clear();
        newProducts.forEach(applicationDB::addProduct);

        return mapper.convertValue(applicationRepository.save(applicationDB), ApplicationDto.class);
    }


}