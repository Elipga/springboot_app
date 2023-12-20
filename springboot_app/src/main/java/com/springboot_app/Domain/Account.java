package com.springboot_app.Domain;

import com.springboot_app.Exception.NotEnoughMoneyException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.BindParam;

import java.math.BigDecimal;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
public class Account {
    private Long id;
    private String person;
    private BigDecimal balance;

    public Account(Long id, String person, BigDecimal balance) {
        this.id = id;
        this.person = person;
        this.balance = balance;
    }

    public void debit(BigDecimal amount){
       BigDecimal newBalance = this.balance.subtract(amount);
       if(newBalance.compareTo(BigDecimal.ZERO) < 0){
           throw new NotEnoughMoneyException("Not enough money in account");
       }
       this.balance = newBalance;
    }

    public void credit(BigDecimal amount){
        this.balance = this.balance.add(amount);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(person, account.person) && Objects.equals(balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, person, balance);
    }
}
