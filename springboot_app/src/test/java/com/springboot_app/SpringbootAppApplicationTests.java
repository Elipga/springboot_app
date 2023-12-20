package com.springboot_app;

import com.springboot_app.Data.Data;
import com.springboot_app.Domain.Account;
import com.springboot_app.Domain.Bank;
import com.springboot_app.Exception.NotEnoughMoneyException;
import com.springboot_app.Repository.AccountRepository;
import com.springboot_app.Repository.BankRepository;
import com.springboot_app.Service.AccountService;
import com.springboot_app.Service.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.springboot_app.Data.Data.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class SpringbootAppApplicationTests {

	AccountRepository accountRepository;
	BankRepository bankRepository;

	AccountService service;

	@BeforeEach
	void setUp() {
		accountRepository = mock(AccountRepository.class);
		bankRepository = mock(BankRepository.class);
		service = new AccountServiceImpl(accountRepository, bankRepository);
		//Data.ACOUNT_001.setBalance(new BigDecimal("1000")); //reset values for each test
		//Data.ACOUNT_002.setBalance(new BigDecimal("2000"));
		//Data.BANK.setTotalTransfer(0);
	}

	@Test
	void contextLoads() {
		when(accountRepository.findById(1L)).thenReturn(Data.createAccount_001());
		when(accountRepository.findById(2L)).thenReturn(Data.createAccount_002());
		when(bankRepository.findById(1L)).thenReturn(Data.createBank());

		BigDecimal originBalance = service.checkBalance(1L);
		BigDecimal destitationBalance = service.checkBalance(2L);
		assertEquals("1000", originBalance.toPlainString());
		assertEquals("2000", destitationBalance.toPlainString());

		service.transfer(1L,2L, new BigDecimal("100"), 1L);

		originBalance = service.checkBalance(1L);
		destitationBalance = service.checkBalance(2L);

		assertEquals("900", originBalance.toPlainString());
		assertEquals("2100", destitationBalance.toPlainString());

		int total = service.checkTotalTransfers(1L);
		assertEquals(1, total);

		verify(accountRepository, times(3)).findById(1L);
		verify(accountRepository, times(3)).findById(2L);
		verify(accountRepository, times(2)).update(any(Account.class));

		verify(bankRepository, times(2)).findById(1L); //times: dafault is 1
		verify(bankRepository).update(any(Bank.class));

		verify(accountRepository, times(6)).findById(anyLong());
		verify(accountRepository, never()).findAll();

	}

	@Test
	void contextLoads2() {
		when(accountRepository.findById(1L)).thenReturn(Data.createAccount_001());
		when(accountRepository.findById(2L)).thenReturn(Data.createAccount_002());
		when(bankRepository.findById(1L)).thenReturn(Data.createBank());

		BigDecimal originBalance = service.checkBalance(1L);
		BigDecimal destitationBalance = service.checkBalance(2L);
		assertEquals("1000", originBalance.toPlainString());
		assertEquals("2000", destitationBalance.toPlainString());

		assertThrows(NotEnoughMoneyException.class, () -> {
			service.transfer(1L,2L, new BigDecimal("1200"), 1L);
		});

		originBalance = service.checkBalance(1L);
		destitationBalance = service.checkBalance(2L);

		assertEquals("1000", originBalance.toPlainString());
		assertEquals("2000", destitationBalance.toPlainString());

		int total = service.checkTotalTransfers(1L);
		assertEquals(0, total);

		verify(accountRepository, times(3)).findById(1L);
		verify(accountRepository, times(2)).findById(2L);
		verify(accountRepository, never()).update(any(Account.class));

		verify(bankRepository, times(1)).findById(1L); //times: dafault is 1
		verify(bankRepository, never()).update(any(Bank.class));

		verify(accountRepository, times(5)).findById(anyLong());
		verify(accountRepository, never()).findAll();

	}

	@Test
	void contextLoad3() {
		when(accountRepository.findById(1L)).thenReturn(Data.createAccount_001());

		Account account1 = service.findById(1L);
		Account account2 = service.findById(1L);

		assertSame(account1, account2);
		assertTrue(account1 == account2);
		assertEquals("Andres", account1.getPerson());
		assertEquals("Andres", account2.getPerson());
		verify(accountRepository, times(2)).findById(anyLong());
	}

	@Test
	void myTest() {
		when(accountRepository.findById(1L)).thenReturn(Data.createAccount_001());
		when(accountRepository.findById(2L)).thenReturn(Data.createAccount_002());

		Account account1 = service.findById(1L);
		Account account2 = service.findById(2L);

		assertEquals("Andres", account1.getPerson());
		assertEquals("John", account2.getPerson());
		verify(accountRepository, times(2)).findById(anyLong());
	}


}
