package com.fwm.restaurante.repository;

import com.fwm.restaurante.domain.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    Optional<Employee> findByAlias(String alias);

    Optional<Employee> findByName(String name);
}
