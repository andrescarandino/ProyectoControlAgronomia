package com.andres.agricultura.v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andres.agricultura.v1.entities.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
