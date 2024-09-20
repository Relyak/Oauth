package com.myonlineshopping.services;

import com.myonlineshopping.model.Account;

import java.util.List;
import java.util.Optional;

public interface IAccountService {
    public List<Account> getAllAccounts();

    public List<Account> getByCustomer_id(Long customer);

    public Optional<Account> getAccount(Long id);

    public void saveAccount(Account account,Long ownerId);

    public Account updateAccount(Account account,Long ownerId);

    public void deleteAccount(Long accountId);

    public void deleteAccountById(Long ownerId);

    public void deleteByCustomer(Long customer);

    public Account addMoney(Long cuentaId,  Long customerId,Integer cantidad);

    public Account withdrawMoney(Long cuentaId,  Long customerId,Integer cantidad) throws Exception;
    public Integer totalBalance(Long id);
    public boolean checkPrestamo(Integer amount,Integer balance);
}
