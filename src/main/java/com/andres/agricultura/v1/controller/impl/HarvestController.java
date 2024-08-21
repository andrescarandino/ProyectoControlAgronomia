package com.andres.agricultura.v1.controller.impl;

import com.andres.agricultura.v1.dto.HarvestDto;
import com.andres.agricultura.v1.service.impl.HarvestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("harvests")
public class HarvestController {

    @Autowired
    HarvestService harvestService;

    @PostMapping
    public ResponseEntity<HarvestDto> save(@Valid @RequestBody HarvestDto harvestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(harvestService.save(harvestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        harvestService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping
    public ResponseEntity<HarvestDto> update(@Valid @RequestBody HarvestDto harvestDto){
        return ResponseEntity.status(HttpStatus.OK).body(harvestService.update(harvestDto));
    }
}
