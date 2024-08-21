package com.andres.agricultura.v1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.andres.agricultura.v1.entities.Application;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

//    @Query("select a from Application a left join fetch a.campaings where a.id=?1")
//    Optional<Application> findApplicationAll(Long id);

}
