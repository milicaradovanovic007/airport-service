package com.milicaradovanovic.daon.repository;

import com.milicaradovanovic.daon.entity.GateEntity;
import org.springframework.data.repository.CrudRepository;

import java.sql.Time;
import java.util.Collection;
import java.util.Optional;

public interface GateRepository extends CrudRepository<GateEntity, Integer> {
    Optional<GateEntity> findById(int id);

    Collection<GateEntity> findAllByAvailableTimeStartBeforeAndAvailableTimeEndAfter(Time from, Time to);

    Collection<GateEntity> findAllByStatusIsNot(byte statusId);

    Collection<GateEntity> findAllByStatusIsNotAndAvailableTimeStartBeforeAndAvailableTimeEndAfter(byte statusId,
                                                                                                   Time from, Time to);
}
