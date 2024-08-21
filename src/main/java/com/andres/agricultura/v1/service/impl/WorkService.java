package com.andres.agricultura.v1.service.impl;

import java.util.List;

import com.andres.agricultura.v1.Exceptions.NotFoundException;
import com.andres.agricultura.v1.dto.WorkDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andres.agricultura.v1.entities.Work;
import com.andres.agricultura.v1.repository.WorkRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkService {

    @Autowired
    WorkRepository workRepository;


    @Autowired
    ObjectMapper mapper;

    @Transactional
    public WorkDto save(WorkDto workDto) {
        Work work = mapper.convertValue(workDto, Work.class);
        return mapper.convertValue(workRepository.save(work), WorkDto.class);
    }

    @Transactional(readOnly = true)
    public List<WorkDto> list() {
        return workRepository.findAll().stream()
                .map(work -> mapper.convertValue(work, WorkDto.class))
                .toList();
    }

    @Transactional(readOnly = true)
    public WorkDto findById(Long id) {
        return mapper.convertValue(workRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Work not found with id=" +id))
                , WorkDto.class);
    }

    @Transactional
    public void delete(Long id) {
        workRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Work not found with id=" +id));
        workRepository.deleteById(id);
    }

    @Transactional
    public WorkDto update(WorkDto workDto) {
        Work work = mapper.convertValue(workDto, Work.class);
        Work workBD = workRepository.findById(work.getId())
                .orElseThrow(() -> new NotFoundException("Work not found with id=" +workDto.getId()));

        workBD.setDate(work.getDate());
        workBD.setObservation(work.getObservation());
        workBD.setPriceHectare(work.getPriceHectare());
        workBD.setName(work.getName());

        workRepository.save(workBD);
        return mapper.convertValue(workBD, WorkDto.class);
    }

}
