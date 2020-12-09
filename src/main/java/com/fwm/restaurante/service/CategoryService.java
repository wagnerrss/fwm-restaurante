package com.fwm.restaurante.service;


import com.fwm.restaurante.domain.Category;
import com.fwm.restaurante.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Iterable<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getById(Integer id) {
        return categoryRepository.findById(id);
    }

    public Optional<Category> getByTitle(String title) {
        return categoryRepository.findByTitle(title);
    }


    public Category insert(Category category) {
        if ((category.getId() == null) || (category.getId() <= 0)) {
            Optional<Category> optional = getByTitle(category.getTitle());
            if (optional.isPresent()) {
                throw new RuntimeException("Registro já existe");
            }
        } else {
            throw new RuntimeException("Não foi possível inserir o registro");
        }

        return categoryRepository.save(category);
    }

    public Category update(Integer id, Category category) {
        Assert.notNull(id, "Não foi possível atualizar o registro");

        Optional<Category> optional = getById(id);
        if (optional.isPresent()) {
            Category c = optional.get();
            c.setTitle(category.getTitle());

            categoryRepository.save(c);

            return c;
        } else {
            throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }

    public boolean delete(Integer id) {
        if (getById(id).isPresent()) {
            categoryRepository.deleteById(id);
            return true;
        }

        return false;
    }

}
