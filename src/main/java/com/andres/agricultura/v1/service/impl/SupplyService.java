package com.andres.agricultura.v1.service.impl;

import com.andres.agricultura.v1.Exceptions.NotFoundException;
import com.andres.agricultura.v1.dto.SupplyDto;

import com.andres.agricultura.v1.entities.Supply;
import com.andres.agricultura.v1.repository.SupplyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SupplyService {

    @Autowired
    SupplyRepository supplyRepository;

    @Autowired
    ObjectMapper mapper;

    @Transactional
    public SupplyDto save(SupplyDto supplyDto) {
        Supply supply = mapper.convertValue(supplyDto, Supply.class);
        return mapper.convertValue(supplyRepository.save(supply), SupplyDto.class);
    }

    @Transactional(readOnly = true)
    public List<SupplyDto> list() {
        return supplyRepository.findAll().stream()
                .map(supply -> mapper.convertValue(supply, SupplyDto.class))
                .toList();
    }

    @Transactional(readOnly = true)
    public SupplyDto findById(Long id) {
        return mapper.convertValue(supplyRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Supply not found with id=" +id))
                , SupplyDto.class);
    }

    @Transactional
    public void delete(Long id) {
        supplyRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Supply not found with id=" +id));
        supplyRepository.deleteById(id);
    }

    @Transactional
    public SupplyDto update(SupplyDto supplyDto) {
        Supply supply = mapper.convertValue(supplyDto, Supply.class);
        Supply supplyBD = supplyRepository.findById(supply.getId())
                .orElseThrow(() -> new NotFoundException("Supply not found with id=" +supplyDto.getId()));

        supplyBD.setName(supply.getName());
        supplyBD.setPriceHectare(supply.getPriceHectare());

        return mapper.convertValue(supplyRepository.save(supplyBD), SupplyDto.class);
    }


}
