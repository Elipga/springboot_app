package com.springboot_app.Repository;

import com.springboot_app.Domain.Bank;

import java.util.List;

public interface BankRepository {
    List<Bank> findAll();
    Bank findById(Long id);
    void update(Bank bank);
}
