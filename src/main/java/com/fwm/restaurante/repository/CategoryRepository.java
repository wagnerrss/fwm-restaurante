package com.fwm.restaurante.repository;

import com.fwm.restaurante.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
    Optional<Category> findByTitle(String title);
}
