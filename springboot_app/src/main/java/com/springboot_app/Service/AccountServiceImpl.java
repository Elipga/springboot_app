package com.springboot_app.Service;

import com.springboot_app.Domain.Account;
import com.springboot_app.Domain.Bank;
import com.springboot_app.Repository.AccountRepository;
import com.springboot_app.Repository.BankRepository;

import java.math.BigDecimal;

public class AccountServiceImpl implements AccountService{
    private AccountRepository accountRepository;
    private BankRepository bankRepository;

    public AccountServiceImpl(AccountRepository accountRepository, BankRepository bankRepository) {
        this.accountRepository = accountRepository;
        this.bankRepository = bankRepository;
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public int checkTotalTransfers(Long bankId) {
        Bank bank = bankRepository.findById(bankId);
        return bank.getTotalTransfer();
    }

    @Override
    public BigDecimal checkBalance(Long accountId) {
        Account account = accountRepository.findById(accountId);
        return account.getBalance();
    }

    @Override
    public void transfer(Long numAccountOrigin, Long numAccountDestination, BigDecimal amount,
                         Long bankId) {

        Account originAccount = accountRepository.findById(numAccountOrigin);
        originAccount.debit(amount);
        accountRepository.update(originAccount);

        Account destinationAccount = accountRepository.findById(numAccountDestination);
        destinationAccount.credit(amount);
        accountRepository.update(destinationAccount);

        Bank bank = bankRepository.findById(bankId);
        int totalTransfer = bank.getTotalTransfer();
        bank.setTotalTransfer(++totalTransfer);
        bankRepository.update(bank);

    }
}
