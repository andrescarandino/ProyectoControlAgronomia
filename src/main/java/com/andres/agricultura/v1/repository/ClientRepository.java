package com.andres.agricultura.v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.andres.agricultura.v1.entities.Client;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{

    @Query("select c from Client c left join fetch c.parcels where c.id=?1")
    Optional<Client> findClientById(Long id);


}
