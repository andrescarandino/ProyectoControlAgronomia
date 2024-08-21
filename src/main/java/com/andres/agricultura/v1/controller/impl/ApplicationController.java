package com.andres.agricultura.v1.controller.impl;

import java.util.List;

import com.andres.agricultura.v1.dto.ApplicationDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.andres.agricultura.v1.service.impl.ApplicationService;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<ApplicationDto> save(@Valid @RequestBody ApplicationDto applicationDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationService.save(applicationDto));
    }

    @GetMapping
    public ResponseEntity<List<ApplicationDto>> list() {
        return ResponseEntity.status(HttpStatus.OK).body(applicationService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(applicationService.findById(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        applicationService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping
    public ResponseEntity<ApplicationDto> update(@Valid @RequestBody ApplicationDto applicationDto) {
        return ResponseEntity.status(HttpStatus.OK).body(applicationService.update(applicationDto));
    }

}
