package com.andres.agricultura.v1.controller.impl;

import java.util.List;

import com.andres.agricultura.v1.dto.WorkDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.andres.agricultura.v1.service.impl.WorkService;

@RestController
@RequestMapping("/works")
public class WorkController {

    @Autowired
    WorkService workService;


    @PostMapping
    public ResponseEntity<WorkDto> save(@Valid @RequestBody WorkDto workDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(workService.save(workDto));
    }


    @GetMapping
    public ResponseEntity<List<WorkDto>> list() {
        return ResponseEntity.status(HttpStatus.OK).body(workService.list());
    }


    @GetMapping("/{id}")
    public ResponseEntity<WorkDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(workService.findById(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        workService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PutMapping
    public ResponseEntity<WorkDto> update(@Valid @RequestBody WorkDto workDto) {
        if (workDto.getId() == null){
            throw new IllegalArgumentException("The given id must not be null");
        }
        return ResponseEntity.status(HttpStatus.OK).body(workService.update(workDto));
    }

}
