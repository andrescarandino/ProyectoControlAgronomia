package com.andres.agricultura.v1.controller.impl;

import java.util.List;

import com.andres.agricultura.v1.dto.ClientDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.andres.agricultura.v1.service.impl.ClientService;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientDto> save(@Valid @RequestBody ClientDto clientDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.save(clientDto));
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> list() {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(clientService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        clientService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping
    public ResponseEntity<ClientDto> update(@Valid @RequestBody ClientDto clientDto){
        return ResponseEntity.status(HttpStatus.OK).body(clientService.update(clientDto));
    }

}
