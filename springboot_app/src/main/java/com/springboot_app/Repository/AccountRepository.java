package com.springboot_app.Repository;

import com.springboot_app.Domain.Account;

import java.util.List;

public interface AccountRepository {
    List<Account> findAll();
    Account findById(Long id);

    void update(Account account);
}
