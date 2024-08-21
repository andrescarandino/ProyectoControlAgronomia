package com.andres.agricultura.v1.repository;

import com.andres.agricultura.v1.entities.Harvest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HarvestRepository extends JpaRepository<Harvest, Long> {
}
