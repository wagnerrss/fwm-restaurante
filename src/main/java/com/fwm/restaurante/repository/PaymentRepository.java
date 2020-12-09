package com.fwm.restaurante.repository;

import com.fwm.restaurante.domain.Payment;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PaymentRepository extends CrudRepository<Payment, Integer> {
    Optional<Payment> findByTitle(String title);
}
