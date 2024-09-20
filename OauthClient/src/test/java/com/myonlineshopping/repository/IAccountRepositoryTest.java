package com.myonlineshopping.repository;

import com.myonlineshopping.model.Account;
import com.myonlineshopping.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


//@ExtendWith(SpringExtension.class)
@DataJpaTest()
@Sql(value = "classpath:data.sql")
@ComponentScan(basePackages = "com.myonlineshopping.demo.repository")
class IAccountRepositoryTest {

    @Autowired
    private IAccountRepository iAccountRepository;

    @Test
    void givenFindByOwnerIdWhenEsValidaThenFind() {
        List<Account> ac = iAccountRepository.findByOwnerId(1L);
        assertThat(ac, is(notNullValue()));
        assertThat(ac.get(0).getOwner().getId(), is(1L));
    }

    @Test
    void givenFindByOwnerIdWhenNoEsValidaThenEmpty() {
        assertThat(iAccountRepository.findByOwnerId(999L),is(empty()));
    }

    @Test
    void givenSaveWhenAccountIsValidThenSave() {
        Account ac = new Account(null, "Corriente", 1230, "2000-12-10", new Customer(1L));
        assertThat(iAccountRepository.save(ac),is(notNullValue()));
    }

    @Test
    void givenSaveWhenAccountNotValidThenException() {
        assertThrows(Exception.class, () -> {
            iAccountRepository.save(new Account(null, "Corriente", 1230, "3131a131a", new Customer(1L)));
        });
    }

    @Test
    void givenAddMoneyWhenAccountEsValidaThenSave() {
        iAccountRepository.addMoney(1L, 1L, 200);
        assertThat(1200, is(equalTo(iAccountRepository.findById(1L).get().getBalance())));
    }

    @Test
    void givenAccountWhenIdIsNotNullThenFind() {
        assertThat((iAccountRepository.findById(1L).get()),is(notNullValue()));
    }

    @Test
    void givenAccountWhenIdIsNullThenException() {
        Long id = null;
        assertThrows(Exception.class, () -> {
            iAccountRepository.findById(id);
        });
    }

    @Test
    void givenWithdrawMoneyWhenIsValidThenSave() {
        iAccountRepository.withdrawMoney(1L, 1L, 200);
        assertThat(800,is(equalTo(iAccountRepository.findById(1L).get().getBalance())));
    }

    @Test
    void givenWithdrawMoneyWhenIsNotValidThenException() {
        assertThrows(Exception.class, () -> {
            Optional<Account> accParaAdd = iAccountRepository.findById(null);
            int dinero = 30;
            int total = accParaAdd.get().getBalance() - dinero;
            accParaAdd.get().setBalance(total);
            iAccountRepository.save(accParaAdd.get());
        });
    }

    @Test
    void givenTotalBalanceWhenEsValidoIdCuentaThenNotNull() {
        int totalB = iAccountRepository.totalBalance(1L);
        assertThat(totalB,is(notNullValue()));
        assertThat(totalB,is(greaterThan(0)));
    }

    @Test
    void givenTotalBalanceWhenNoEsValidoIdCuentaThenNull() {
        assertThat((iAccountRepository.totalBalance(320L)),is(nullValue()));
    }
}