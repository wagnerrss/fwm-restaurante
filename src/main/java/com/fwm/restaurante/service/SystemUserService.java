package com.fwm.restaurante.service;

import com.fwm.restaurante.domain.SystemUser;
import com.fwm.restaurante.domain.Type;
import com.fwm.restaurante.repository.SystemUserRepository;
import com.fwm.restaurante.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
public class SystemUserService {
    @Autowired
    private SystemUserRepository systemUserRepository;

    public Iterable<SystemUser> getSystemUsers() {
        return systemUserRepository.findAll();
    }

    public Optional<SystemUser> getById(Integer id) {
        return systemUserRepository.findById(id);
    }

    public Optional<SystemUser> getByUserName(String username) {
        return systemUserRepository.findByUsername(username);
    }

    public SystemUser insert(SystemUser systemUser) {
        if ((systemUser.getId() == null) || (systemUser.getId() <= 0)) {
            Optional<SystemUser> optional = getByUserName(systemUser.getUsername());
            if (optional.isPresent()) {
                throw new RuntimeException("Registro já existe");
            }
        } else {
            throw new RuntimeException("Não foi possível inserir o registro");
        }

        return systemUserRepository.save(systemUser);
    }

    public SystemUser update(Integer id, SystemUser systemUser) {
        Assert.notNull(id, "Não foi possível atualizar o registro");

        Optional<SystemUser> optional = getById(id);
        if (optional.isPresent()) {
            SystemUser s = optional.get();
            s.setPassword(systemUser.getPassword());
            s.setEmail(systemUser.getEmail());
            s.setType(systemUser.getType());

            systemUserRepository.save(s);

            return s;
        } else {
            throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }

    public boolean delete(Integer id) {
        if (getById(id).isPresent()) {
            systemUserRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
