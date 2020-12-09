package com.fwm.restaurante.service;

import com.fwm.restaurante.domain.Destination;
import com.fwm.restaurante.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
public class DestinationService {
    @Autowired
    private DestinationRepository destinationRepository;

    public Iterable<Destination> getDestinations() {
        return destinationRepository.findAll();
    }

    public Optional<Destination> getById(Integer id) {
        return destinationRepository.findById(id);
    }

    public Optional<Destination> getByTitle(String title) {
        return destinationRepository.findByTitle(title);
    }

    public Destination insert(Destination destination) {
        if ((destination.getId() == null) || (destination.getId() <= 0)) {
            Optional<Destination> optional = getByTitle(destination.getTitle());
            if (optional.isPresent()) {
                throw new RuntimeException("Registro já existe");
            }
        } else {
            throw new RuntimeException("Não foi possível inserir o registro");
        }

        return destinationRepository.save(destination);
    }

    public Destination update(Integer id, Destination destination) {
        Assert.notNull(id, "Não foi possível atualizar o registro");

        Optional<Destination> optional = getById(id);
        if (optional.isPresent()) {
            Destination d = optional.get();
            d.setTitle(destination.getTitle());

            destinationRepository.save(d);

            return d;
        } else {
            throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }

    public boolean delete(Integer id) {
        if (getById(id).isPresent()) {
            destinationRepository.deleteById(id);
            return true;
        }

        return false;
    }

}
