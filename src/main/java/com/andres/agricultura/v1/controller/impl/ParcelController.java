package com.andres.agricultura.v1.controller.impl;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.andres.agricultura.v1.dto.ParcelDto;
import com.andres.agricultura.v1.service.impl.ParcelService;

@RestController
@RequestMapping("/parcels")
public class ParcelController {

    @Autowired
    ParcelService parcelService;

   @PostMapping
    public ResponseEntity<ParcelDto> save(@Valid @RequestBody ParcelDto parcelDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(parcelService.save(parcelDto));
    }


    @GetMapping
    public ResponseEntity<List<ParcelDto>> list() {
        return ResponseEntity.status(HttpStatus.OK).body(parcelService.list());
    }


    @GetMapping("/{id}")
    public ResponseEntity<ParcelDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(parcelService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        parcelService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PutMapping
    public ResponseEntity<ParcelDto> update(@Valid @RequestBody ParcelDto parcelDto) {
        return ResponseEntity.status(HttpStatus.OK).body(parcelService.update(parcelDto));
    }



}
