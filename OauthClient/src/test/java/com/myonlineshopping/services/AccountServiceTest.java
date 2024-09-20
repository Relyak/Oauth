package com.myonlineshopping.services;

import com.myonlineshopping.model.Account;
import com.myonlineshopping.model.Customer;
import com.myonlineshopping.repository.IAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@ComponentScan(basePackages = {"com.myonlineshopping.demo.services"})
class AccountServiceTest {

    // TODO: Las excepciones que se catchean son genéricas porque no temos custom implementadas
    // Uno de los métodos interesantes para checkear sería el de añadir el de crear cuenta
    // aún así no devuelve nada, quizás por un error de diseño

    @Mock
    IAccountRepository repo;
    @InjectMocks
    AccountService service;

    Customer testCustomer;

    Account testAccount;

    @BeforeEach
    public void createCustomer(){
        testCustomer = new Customer(
                1L,
                "Pepote",
                "pepote@mail.es",
                List.of()
        );

        testAccount = new Account(
                1L,
                "Personal",
                100,
                LocalDate.now().toString(),
                testCustomer);

        testAccount.setOwner(testCustomer);
    }



    // Update account
    @Test
    public void givenUpdateAccount_whenValid_ThenUpdatedAccount(){

        Mockito.when(repo.findById(testAccount.getId())).
                thenReturn(Optional.of(testAccount));

        Mockito.when(repo.save(testAccount))
                .thenReturn(testAccount);

        final int newBalance = 500;
        testAccount.setBalance(newBalance);
        Account account = service.updateAccount(testAccount, testCustomer.getId());
        assertThat(account, is(notNullValue()));
        assertThat(account.getBalance(), is(testAccount.getBalance()));
    }

    @Test
    public void givenUpdateAccount_whenAccountNull_ThenException(){
        assertThatThrownBy(
                () -> service.updateAccount(null, testCustomer.getId())
        ).isInstanceOf(Exception.class);
    }

    @Test
    public void givenUpdateAccount_whenCustomerNull_ThenException(){
        assertThatThrownBy(
                () -> service.updateAccount(testAccount, null)
        ).isInstanceOf(Exception.class);
    }

    // Get customer by id
    @Test
    public void givenGetByCustomerId_whenValid_ThenListAccounts(){
        Mockito.when(repo.findByOwnerId(testCustomer.getId()))
                .thenReturn(List.of(testAccount));

        List<Account> accounts = service.getByCustomer_id(testCustomer.getId());

        assertThat(accounts.size(), is(greaterThan(0)));
    }
    
    @Test
    public void givenGetByCustomerId_whenNotCustomer_ThenException(){
        assertThatThrownBy(
                () -> service.getByCustomer_id(null))
                .isInstanceOf(Exception.class);
    }

    // Check prestamo
    @Test
    public void givenCheckPrestamo_whenValid_thenTrue(){
        final int amount = (int)(testAccount.getBalance() * 0.7f);
        final int balance = testAccount.getBalance();

        assertThat(service.checkPrestamo(amount, balance), is(true));

    }

     @Test
    public void givenCheckPrestamo_whenNotValid_thenFalse(){
        final int amount = (int)(testAccount.getBalance() * 0.9f);
        final int balance = testAccount.getBalance();

        assertThat(service.checkPrestamo(amount, balance), is(false));
    }

    @Test
    public void givenCheckPrestamo_whenAmountNull_thenException(){
        final int balance = testAccount.getBalance();

        assertThatThrownBy(
                () -> service.checkPrestamo(null, balance))
                .isInstanceOf(Exception.class);
    }

    @Test
    public void givenCheckPrestamo_whenBalanceNull_thenException(){
        final int amount = 124;

        assertThatThrownBy(
                () -> service.checkPrestamo(amount, null))
                .isInstanceOf(Exception.class);
    }

    // add money
    @Test
    public void givenAddMoney_whenValid_thenUpdatedAccount(){
        final int toAdd = 500;

        testAccount.setBalance(testAccount.getBalance() + toAdd);
        Mockito.when(
                repo.findById(testCustomer.getId()))
                .thenReturn(Optional.of(testAccount));

        Account account = service.addMoney(testAccount.getId(),
                testAccount.getOwner().getId(),
                toAdd);

        assertThat(account, is(notNullValue()));
    }

    @Test
    public void givenAddMoney_whenCuentaIdNull_thenException(){

        assertThatThrownBy(() -> service.addMoney(
                null,
                testCustomer.getId(),
                200)).isInstanceOf(Exception.class);
    }

}