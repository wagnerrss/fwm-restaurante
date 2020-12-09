package com.fwm.restaurante.repository;

import com.fwm.restaurante.domain.Type;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TypeRepository extends CrudRepository<Type, Integer> {
    Optional<Type> findByTitle(String title);
}
