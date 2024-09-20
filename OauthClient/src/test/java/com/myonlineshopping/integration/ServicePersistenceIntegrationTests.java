package com.myonlineshopping.integration;

import com.myonlineshopping.model.Account;
import com.myonlineshopping.model.Customer;
import com.myonlineshopping.repository.IAccountRepository;
import com.myonlineshopping.repository.ICustomerRepository;
import com.myonlineshopping.services.IAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ServicePersistenceIntegrationTests {

   /* @Autowired
    IAccountRepository repo;*/

    @Autowired
    IAccountService service;

    @Autowired
    ICustomerRepository customerRepo;
    @Autowired
    IAccountRepository accountRepo;

    Customer customer;

    Account acc;



    @BeforeEach
    public void setUp() {

        List<Account> accountList = new ArrayList<>(List.of());

        customer = new Customer(100L, "Javier", "javier@a.com", accountList);

        acc = new Account(200L, "Personal", 500, "2024-09-20", customer);
        accountList.add(acc);

        customerRepo.save(customer);

        accountRepo.save(acc);

    }

    @Test
    public void givenAddMoney_whenMatchingOwnerAndAccountId_ThenAddMoney() {

        // Al llamar al método addMoney, si la cuenta recibida se encuentra entre las cuentas
        // del cliente, se puede sumar el dinero.

        // cogemos la cuenta que nos devuelve el servicio para poder comparar
        Account account = service.addMoney(1L, 1L, 200);

        assertThat(account.getBalance(), is(equalTo(1200)));
    }

    @Test
    public void givenAddMoney_whenNotMatchingOwnerAndAccountId_ThenThrowException() {

        // La excepción es genérica porque el servicio no tiene implementada una custom
        assertThrows(Exception.class,
                () -> service.addMoney(300L, 1L, 300));
    }

    @Test
    public void givenWithdrawMoney_whenMatchingOwnerAndAccountId_ThenWithdrawMoney() throws Exception{

        // Al llamar al método addMoney, si la cuenta recibida se encuentra entre las cuentas
        // del cliente, se puede sumar el dinero.

        Account account = service.withdrawMoney(1L, 1L, 200);
        assertThat(account.getBalance(), is(equalTo(800)));
    }

    @Test
    public void givenWithdrawMoney_whenNotMatchingOwnerAndAccountId_ThenThrowException() {
        assertThrows(Exception.class,
                () -> service.withdrawMoney(300L, 1L, 300));
    }
}








/*    • Para validar la integración entre la capa de servicio y persistencia (2 escenarios significativos).
            -> givenAddMoneyWhen
  		-> givenAddMoney_whenValid_ThenAddMoney
      -> givenAddMoney_whenNoValid_ThenAddMoney
    -> getByCustomer_id
    	-> givenGetByCustomer_id_whenValid_ThenListAccounts(todo correcto)
      -> givenGetByCustomer_id_whenNotCustomer_ThenException(customer = null) */