package com.fwm.restaurante.repository;

import com.fwm.restaurante.domain.Destination;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DestinationRepository extends CrudRepository<Destination, Integer> {
    Optional<Destination> findByTitle(String title);
}
