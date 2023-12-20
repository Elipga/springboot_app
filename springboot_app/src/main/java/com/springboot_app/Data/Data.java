package com.springboot_app.Data;

import com.springboot_app.Domain.Account;
import com.springboot_app.Domain.Bank;

import java.math.BigDecimal;

public class Data {

    //public  static final Account ACOUNT_001 = new Account(1L, "Andres", new BigDecimal("1000"));
    //public  static final Account ACOUNT_002 = new Account(2L, "John", new BigDecimal("2000"));
    //public static  final Bank BANK = new Bank(1L, "Santander", 0);

    public static Account createAccount_001(){
        return new Account(1L, "Andres", new BigDecimal("1000"));
    }

    public static Account createAccount_002(){
        return new Account(2L, "John", new BigDecimal("2000"));
    }

    public static Bank createBank(){
        return new Bank(1L, "Santander", 0);
    }
}
