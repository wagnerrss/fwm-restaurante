package com.fwm.restaurante.service;

import com.fwm.restaurante.domain.Payment;
import com.fwm.restaurante.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public Iterable<Payment> getPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getById(Integer id) {
        return paymentRepository.findById(id);
    }

    public Optional<Payment> getByTitle(String title) {
        return paymentRepository.findByTitle(title);
    }

    public Payment insert(Payment payment) {
        if ((payment.getId() == null) || (payment.getId() <= 0)) {
            Optional<Payment> optional = getByTitle(payment.getTitle());
            if (optional.isPresent()) {
                throw new RuntimeException("Registro já existe");
            }
        } else {
            throw new RuntimeException("Não foi possível inserir o registro");
        }

        return paymentRepository.save(payment);
    }

    public Payment update(Integer id, Payment payment) {
        Assert.notNull(id, "Não foi possível atualizar o registro");

        Optional<Payment> optional = getById(id);
        if (optional.isPresent()) {
            Payment p = optional.get();
            p.setTitle(payment.getTitle());
            p.setType(payment.getType());
            p.setRate(payment.getRate());

            paymentRepository.save(p);

            return p;
        } else {
            throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }

    public boolean delete(Integer id) {
        if (getById(id).isPresent()) {
            paymentRepository.deleteById(id);
            return true;
        }

        return false;
    }

}
