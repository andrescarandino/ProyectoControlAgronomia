package com.andres.agricultura.v1.controller.impl;

import com.andres.agricultura.v1.dto.SupplyDto;
import com.andres.agricultura.v1.service.impl.SupplyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplies")
public class SupplyController {
    @Autowired
    SupplyService supplyService;

    @PostMapping
    public ResponseEntity<SupplyDto> save(@Valid @RequestBody SupplyDto supplyDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(supplyService.save(supplyDto));
    }


    @GetMapping
    public ResponseEntity<List<SupplyDto>> list() {
        return ResponseEntity.status(HttpStatus.OK).body(supplyService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplyDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(supplyService.findById(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        supplyService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PutMapping
    public ResponseEntity<SupplyDto> update(@Valid @RequestBody SupplyDto supplyDto) {
        return ResponseEntity.status(HttpStatus.OK).body(supplyService.update(supplyDto));
    }
    
}
