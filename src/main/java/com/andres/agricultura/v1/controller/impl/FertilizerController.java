package com.andres.agricultura.v1.controller.impl;

import java.util.List;

import com.andres.agricultura.v1.dto.FertilizerDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.andres.agricultura.v1.service.impl.FertilizerService;

@RestController
@RequestMapping("/fertilizers")
public class FertilizerController {

    @Autowired
    FertilizerService fertilizerService;


    @PostMapping()
    public ResponseEntity<FertilizerDto> save(@Valid @RequestBody FertilizerDto fertilizerDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fertilizerService.save(fertilizerDto));
    }

    @GetMapping
    public ResponseEntity<List<FertilizerDto>> list() {
        return ResponseEntity.status(HttpStatus.OK).body(fertilizerService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FertilizerDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(fertilizerService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        fertilizerService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping
    public ResponseEntity<FertilizerDto> update(@Valid @RequestBody FertilizerDto fertilizerDto) {
        return ResponseEntity.status(HttpStatus.OK).body(fertilizerService.update(fertilizerDto));
    }

}
