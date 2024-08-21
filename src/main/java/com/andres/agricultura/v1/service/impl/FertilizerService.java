package com.andres.agricultura.v1.service.impl;

import java.util.List;

import com.andres.agricultura.v1.Exceptions.NotFoundException;
import com.andres.agricultura.v1.dto.FertilizerDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andres.agricultura.v1.entities.Fertilizer;
import com.andres.agricultura.v1.repository.FertilizerRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FertilizerService{

    @Autowired
    FertilizerRepository fertilizerRepository;

    @Autowired
    ObjectMapper mapper;

    @Transactional
    public FertilizerDto save(FertilizerDto fertilizerDto) {
        Fertilizer fertilizer = mapper.convertValue(fertilizerDto, Fertilizer.class);
        fertilizer = fertilizerRepository.save(fertilizer);
        return mapper.convertValue(fertilizer, FertilizerDto.class);
    }

    @Transactional(readOnly = true)
    public List<FertilizerDto> list() {
        return fertilizerRepository.findAll().stream()
                .map(fertilizer -> mapper.convertValue(fertilizer, FertilizerDto.class))
                .toList();
    }

    @Transactional(readOnly = true)
    public FertilizerDto findById(Long id) {
        return mapper.convertValue(fertilizerRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Fertilizer with id=" +id+ " not found" ))
                , FertilizerDto.class);
    }

    @Transactional
    public void delete(Long id) {
        fertilizerRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Fertilizer with id=" +id+ " not found" ));
        fertilizerRepository.deleteById(id);
    }

    @Transactional
    public FertilizerDto update(FertilizerDto fertilizerDto) {
        Fertilizer fertilizer = mapper.convertValue(fertilizerDto, Fertilizer.class);
        Fertilizer fertilizerBD = fertilizerRepository.findById(fertilizer.getId())
                .orElseThrow(()-> new NotFoundException("Fertilizer with id=" +fertilizerDto.getId()+ " not found" ));

        fertilizerBD.setName(fertilizer.getName());
        fertilizerBD.setDate(fertilizer.getDate());
        fertilizerBD.setObservation(fertilizer.getObservation());
        fertilizerBD.setNitrogen(fertilizer.getNitrogen());
        fertilizerBD.setPhosphorus(fertilizer.getPhosphorus());
        fertilizerBD.setDose(fertilizer.getDose());
        fertilizerBD.setPriceHectare(fertilizer.getPriceHectare());
        fertilizerBD.setZinc(fertilizer.getZinc());
        fertilizerBD.setSulphur(fertilizer.getSulphur());

        fertilizerRepository.save(fertilizerBD);
        return  mapper.convertValue(fertilizerBD, FertilizerDto.class);
    }

}
