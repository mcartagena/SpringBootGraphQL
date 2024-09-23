package com.accounts.controller;

import com.accounts.domain.Client;
import com.accounts.entity.BankAccount;
import com.accounts.service.BankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class AccountsController {
    @Autowired
    BankService bankService;

    @QueryMapping
    List<BankAccount> accounts() {
        log.info("Getting Accounts");
        return bankService.getAccounts();
    }

    @QueryMapping
    BankAccount accountById(@Argument ("accountId") Long accountId){
        return bankService.accountById(accountId);
    }

    /*
    @SchemaMapping(typeName="BankAccount", field = "client")
    Client getClient(BankAccount account){
        log.info("Getting client for {}", account.getId());
        return bankService.getClientByAccountId(account.getId());
    }
     */

    /** Get clients without N + 1 problem **/
    @BatchMapping( field = "client", typeName = "BankAccountType")
    Map<BankAccount, Client> getClient(List<BankAccount> accounts) {
        log.info("Getting client for Accounts : {}", accounts.size());

        return bankService.getClients(accounts);
    }

    @MutationMapping
    Boolean addAccount (@Argument("account") BankAccount account)  {
        log.info("Saving Account : {}", account);
        bankService.save(account);
        return true;
    }

    @MutationMapping
    BankAccount editAccount (@Argument("account") BankAccount account) {
        log.info("Editing Account : {}", account);
        return bankService.modify(account);
    }

    @MutationMapping
    Boolean deleteAccount (@Argument("id") Long accountId) {
        log.info("Deleting Account : {}", accountId);
        return bankService.delete(accountId);
    }
}
