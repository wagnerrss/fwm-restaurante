package com.fwm.restaurante.service;

import com.fwm.restaurante.domain.Type;
import com.fwm.restaurante.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
public class TypeService {
    @Autowired
    private TypeRepository typeRepository;

    public Iterable<Type> getTypes() {
        return typeRepository.findAll();
    }

    public Optional<Type> getById(Integer id) {
        return typeRepository.findById(id);
    }

    public Optional<Type> getByTitle(String title) {
        return typeRepository.findByTitle(title);
    }

    public Type insert(Type type) {
        if ((type.getId() == null) || (type.getId() <= 0)) {
            Optional<Type> optional = getByTitle(type.getTitle());
            if (optional.isPresent()) {
                throw new RuntimeException("Registro já existe");
            }
        } else {
            throw new RuntimeException("Não foi possível inserir o registro");
        }

        return typeRepository.save(type);
    }

    public Type update(Integer id, Type type) {
        Assert.notNull(id, "Não foi possível atualizar o registro");

        Optional<Type> optional = getById(id);
        if (optional.isPresent()) {
            Type t = optional.get();
            t.setTitle(type.getTitle());

            typeRepository.save(t);

            return t;
        } else {
            throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }

    public boolean delete(Integer id) {
        if (getById(id).isPresent()) {
            typeRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
