package com.fwm.restaurante.repository;

import com.fwm.restaurante.domain.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    Iterable<Product> findByCategory(Integer id);

    Iterable<Product> findByDestination(Integer id);

    Iterable<Product> findByType(Integer id);

    Optional<Product> findByTitle(String title);
}
