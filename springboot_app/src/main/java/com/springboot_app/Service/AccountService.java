package com.springboot_app.Service;

import com.springboot_app.Domain.Account;

import java.math.BigDecimal;

public interface AccountService {
    Account findById(Long id);

    int checkTotalTransfers(Long bankId);

    BigDecimal checkBalance (Long accountId);

    void transfer(Long numAccountOrigin, Long numAccountDestination, BigDecimal amount,
                  Long bankId);


}
