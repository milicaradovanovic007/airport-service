package com.milicaradovanovic.daon.repository;

import com.milicaradovanovic.daon.entity.AirportUserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AirportUserRepository extends CrudRepository<AirportUserEntity, Integer> {
    Optional<AirportUserEntity> findByEmail(String email);
}
