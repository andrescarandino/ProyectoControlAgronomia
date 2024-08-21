package com.andres.agricultura.v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andres.agricultura.v1.entities.Fertilizer;
import org.springframework.stereotype.Repository;

@Repository
public interface FertilizerRepository extends JpaRepository<Fertilizer, Long> {

}
