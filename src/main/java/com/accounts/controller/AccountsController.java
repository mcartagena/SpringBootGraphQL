package com.accounts.controller;

import com.accounts.domain.Client;
import com.accounts.entity.BankAccount;
import com.accounts.exceptions.ClientNotFoundException;
import com.accounts.service.BankService;
import org.springframework.graphql.execution.ErrorType;
import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import lombok.NonNull;
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
    List<BankAccount> accounts(@ContextValue String accountStatus) {
        log.info("Getting Accounts for status: {}", accountStatus);
        return bankService.getAccounts(accountStatus);
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

    @GraphQlExceptionHandler
    public GraphQLError handle(@NonNull ClientNotFoundException ex, @NonNull DataFetchingEnvironment environment){
        return GraphQLError.newError().errorType(ErrorType.BAD_REQUEST)
                .message("Unable to locate the specified client. " +
                        "Please verify the client details and attempt your request again. : " +
                        ex.getMessage())
                .path(environment.getExecutionStepInfo().getPath())
                .location(environment.getField().getSourceLocation())
                .build();
    }
}
