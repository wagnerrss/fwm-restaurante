package com.fwm.restaurante.service;

import com.fwm.restaurante.domain.Product;
import com.fwm.restaurante.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Iterable<Product> getProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getById(Integer id) {
        return productRepository.findById(id);
    }

    public Optional<Product> getByTitle(String title) {
        return productRepository.findByTitle(title);
    }

    public Iterable<Product> getByCategory(Integer id) {
        return productRepository.findByCategory(id);
    }

    public Iterable<Product> getByDestination(Integer id) {
        return productRepository.findByDestination(id);
    }

    public Iterable<Product> getByType(Integer id) {
        return productRepository.findByType(id);
    }

    public Product insert(Product product) {
        if ((product.getId() == null) || (product.getId() <= 0)) {
            Optional<Product> optional = getByTitle(product.getTitle());
            if (optional.isPresent()) {
                throw new RuntimeException("Registro já existe");
            }
        } else {
            throw new RuntimeException("Não foi possível inserir o registro");
        }

        return productRepository.save(product);
    }

    public Product update(Integer id, Product product) {
        Assert.notNull(id, "Não foi possível atualizar o registro");

        Optional<Product> optional = getById(id);
        if (optional.isPresent()) {
            Product p = optional.get();
            p.setTitle(product.getTitle());
            p.setPrice(product.getPrice());
            p.setCost(product.getCost());
            p.setCategory(product.getCategory());
            p.setDestination(product.getDestination());
            p.setType(product.getType());
            p.setBarCode(product.getBarCode());

            productRepository.save(p);

            return p;
        } else {
            throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }

    public boolean delete(Integer id) {
        if (getById(id).isPresent()) {
            productRepository.deleteById(id);
            return true;
        }

        return false;
    }

}
