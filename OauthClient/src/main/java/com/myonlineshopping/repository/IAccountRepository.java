package com.myonlineshopping.repository;

import com.myonlineshopping.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IAccountRepository extends JpaRepository<Account,Long> {
    public List<Account> findByOwnerId(Long id); //esto por nomenclatura
    public void deleteByOwnerId(Long customer); //esto por nomenclatura
    @Modifying
    @Query("UPDATE Account a SET a.balance = a.balance + :cantidad WHERE a.id = :cuentaId AND a.owner.id = :propietario")
    public void addMoney(@Param("cuentaId") Long cuentaId, @Param("propietario") Long customer, @Param("cantidad") Integer cantidad);
    @Modifying
    @Query("UPDATE Account a SET a.balance = a.balance - :cantidad WHERE a.id = :cuentaId AND a.owner.id = :propietario")
    public void withdrawMoney(@Param("cuentaId") Long cuentaId,@Param("propietario") Long customer,@Param("cantidad") Integer cantidad) ;
    @Query("Select SUM(a.balance) FROM Account a WHERE a.owner.id= :id")
    public Integer totalBalance(@Param("id") Long id);

}
