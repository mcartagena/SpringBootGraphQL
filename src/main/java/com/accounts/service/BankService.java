package com.accounts.service;

import com.accounts.domain.Client;
import com.accounts.domain.Currency;
import com.accounts.entity.BankAccount;
import com.accounts.exceptions.AccountNotFoundException;
import com.accounts.exceptions.ClientNotFoundException;
import com.accounts.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BankService {

    @Autowired
    BankAccountRepository bankAccountRepository;

    private static final List<Client> clients = Arrays.asList(
            new Client(100L,  "John", "T.", "Doe", "US"),
            new Client(101L, "Emma", "B.", "Smith", "CA"),
            new Client(102L, "James", "R.", "Brown", "IN"),
            new Client(103L, "Olivia", "S.", "Johnson", "UK"),
            new Client(104L, "William", "K.", "Jones", "SG")
    );

    public void save(BankAccount bankAccount){
        if(validClient(bankAccount)){
            bankAccountRepository.save(bankAccount);
        } else {
            throw new ClientNotFoundException("Client Not Found");
        }
    }

    public BankAccount modify(BankAccount bankAccount){
        if(validClient(bankAccount)){
            return bankAccountRepository.save(bankAccount);
        } else {
            throw new ClientNotFoundException("Client Not Found");
        }
    }

    public List<BankAccount> getAccounts() {
        return bankAccountRepository.findAll();
    }

    public BankAccount accountById(Long accountId) {
        if (bankAccountRepository.findById(accountId).isPresent()) {
            return bankAccountRepository.findById(accountId).get();
        }
        throw new AccountNotFoundException("Account Not Found");
    }

    public Boolean delete(Long accountId){
        if(bankAccountRepository.findById(accountId).isPresent()){
            bankAccountRepository.delete(
                    bankAccountRepository.findById(accountId).get()
            );
            return  true;
        }
        return false;
    }

    private List<Client> getClients () {
        return clients;
    }

    public Map<BankAccount, Client> getClients (List<BankAccount> bankAccounts) {
        // Collect all client IDs from the list of bank accounts
        Set<Long> clientIds = bankAccounts.stream()
                .map(BankAccount::getClientId)
                .collect(Collectors.toSet());

        // Fetch client for all collected IDs
        List<Client> clients = getClients().stream()
                .filter(client -> clientIds.contains(client.getId()))
                .collect(Collectors.toList());

        // Map each bank account to its corresponding client
        return clients.stream()
                .collect(Collectors.toMap(
                        client -> bankAccounts.stream()
                                .filter(bankAccount -> bankAccount.getClientId().equals(client.getId()))
                                .findFirst()
                                .orElse(null),
                        client -> client
                ));
    }

    private boolean validClient(BankAccount account) {
        return getClients ().stream()
                .filter(client -> client.getId().equals(account.getClientId())).findAny().isPresent();
    }
}
