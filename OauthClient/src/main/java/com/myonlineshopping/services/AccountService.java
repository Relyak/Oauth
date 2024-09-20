package com.myonlineshopping.services;

import com.myonlineshopping.controller.AccountController;
import com.myonlineshopping.exceptions.AccountNotfoundException;
import com.myonlineshopping.model.Account;
import com.myonlineshopping.model.Customer;
import com.myonlineshopping.repository.IAccountRepository;
import com.myonlineshopping.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
@Service
public class AccountService implements IAccountService{
    @Autowired
    IAccountRepository accountRepository;
    @Autowired
    ICustomerRepository iCustomerRepository;

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }



    public List<Account> getByCustomer_id(Long customer) {

        List<Account> accounts = accountRepository.findByOwnerId(customer);

        if(!accounts.isEmpty()) {
            return accounts;
        } else {
            throw new AccountNotfoundException();
        }
    }
    public Optional<Account> getAccount(Long id){


        return accountRepository.findById(id);
    }
    public void saveAccount(Account account,Long ownerId){
        //TODO  PONER EXCEPCIÓN SI NO EXISTE
        Customer cus = iCustomerRepository.findById(ownerId).get();
        account.setOwner(cus);
        accountRepository.save(account);
    }
    public Account updateAccount(Account account,Long accountId){
         //TODO  PONER EXCEPCIÓN SI NO EXISTE
        //TODO A VER SI HACE FALTA CAMBIAR MÁS PARAMETROS DE LA CUENTA

        Account acc= accountRepository.findById(accountId).get();
        acc.setBalance(account.getBalance());
        return accountRepository.save(acc);
    }

    @Override
    public void deleteAccount(Long accountId) {

    }

    public void deleteAccount(Account account){
        accountRepository.delete(account);
    }
    public void deleteAccountById(Long id){
        accountRepository.deleteById(id);
    }
    public void deleteByCustomer(Long customer){
        accountRepository.deleteByOwnerId(customer);
    }
    @Transactional
    public Account addMoney(Long cuentaId, Long idCustomer,Integer dinero){
        accountRepository.addMoney(cuentaId,idCustomer,dinero);
        return accountRepository.findById(cuentaId).get();
    }

    @Autowired
    private EntityManager em;

    @Transactional
    public Account withdrawMoney(Long cuentaId,Long customer, Integer cantidad) throws Exception{
        Optional<Account> account = accountRepository.findById(cuentaId);
        if((account.get().getBalance())>=cantidad){
            accountRepository.withdrawMoney(cuentaId, customer, cantidad);
            em.refresh(account.get());
            return account.get();
        }else
            throw new Exception();


    }
    public Integer totalBalance(Long id){

        return accountRepository.totalBalance(id);
    }


    public boolean checkPrestamo(Integer amount, Integer balance) {

        if(balance*(AccountController.PORCENTAJE_MAXIMO)>=amount){
            return true;
        }else return false;
    }
}
