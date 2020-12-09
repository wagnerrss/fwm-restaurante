package com.fwm.restaurante.service;

import com.fwm.restaurante.domain.Employee;
import com.fwm.restaurante.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Iterable<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getById(Integer id) {
        return employeeRepository.findById(id);
    }

    public Optional<Employee> getByAlias(String alias) {

        return employeeRepository.findByAlias(alias);
    }

    public Optional<Employee> getByName(String name) {
        return employeeRepository.findByName(name);
    }

    public Employee insert(Employee employee) {
        if ((employee.getId() == null) || (employee.getId() <= 0)) {
            Optional<Employee> optional = getByName(employee.getName());
            if (optional.isPresent()) {
                throw new RuntimeException("Registro já existe");
            }
        } else {
            throw new RuntimeException("Não foi possível inserir o registro");
        }

        return employeeRepository.save(employee);
    }

    public Employee update(Integer id, Employee employee) {
        Assert.notNull(id, "Não foi possível atualizar o registro");

        Optional<Employee> optional = getById(id);
        if (optional.isPresent()) {
            Employee e = optional.get();
            e.setAlias(employee.getAlias());
            e.setName(employee.getName());
            e.setDocument(employee.getDocument());
            e.setBirth(employee.getBirth());
            e.setOccupation(employee.getOccupation());

            employeeRepository.save(e);

            return e;
        } else {
            throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }

    public boolean delete(Integer id) {
        if (getById(id).isPresent()) {
            employeeRepository.deleteById(id);
            return true;
        }

        return false;
    }

}
