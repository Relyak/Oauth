package com.myonlineshopping.dto;

import com.myonlineshopping.model.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {

    private Long id;

    private String type;
    private int balance;
    private String opening_date;
    private Long ownerId;
    public static AccountDTO createAccountDto(Account account){
        return new AccountDTO(account.getId(),account.getType(), account.getBalance(), account.getOpening_date(),account.getOwner().getId());
    }
}
