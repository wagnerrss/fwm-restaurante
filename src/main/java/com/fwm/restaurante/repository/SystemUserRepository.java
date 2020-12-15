package com.fwm.restaurante.repository;

import com.fwm.restaurante.domain.SystemUser;
import com.fwm.restaurante.domain.Type;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SystemUserRepository extends CrudRepository<SystemUser, Integer> {
    Optional<SystemUser> findByUserName(String userName);
}
