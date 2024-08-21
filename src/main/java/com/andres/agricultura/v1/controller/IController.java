package com.andres.agricultura.v1.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


public interface IController<T> {

    @PostMapping()
    public ResponseEntity<T> save (@RequestBody T t);

    @GetMapping()
    public ResponseEntity<List<T>> list();

    @GetMapping("/{id}")
    public ResponseEntity<T> findById(@PathVariable Long id);
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id);

    @PutMapping("/{id}")
    public ResponseEntity<T> update(@PathVariable Long id, @RequestBody T t);

}
