package com.accounts.service;

import com.accounts.domain.BankAccount;
import com.accounts.domain.Client;
import com.accounts.domain.Currency;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BankService {
    private static final List<BankAccount> bankAccounts = Arrays.asList(
            new BankAccount("A100", "C100", Currency.USD, 106.00f, "A"),
            new BankAccount("A101", "C200", Currency.CAD, 250.00f, "A"),
            new BankAccount("A102", "C300", Currency.CAD, 333.00f, "I"),
            new BankAccount("A103", "C400", Currency.EUR, 4000.00f, "A"),
            new BankAccount("A104", "C500", Currency.EUR, 4000.00f, "A")
    );
    private static final List<Client> clients = Arrays.asList(
            new Client("C100", "A100", "Elena", "Maria", "Gonzalez"),
            new Client("C200", "A101", "James", "Robert", "Smith"),
            new Client("C300", "A102", "Aarav", "Kumar", "Patel"),
            new Client("C400", "A103", "Linh", "Thi", "Nguyen"),
            new Client("C500", "A104", "Olivia", "Grace", "Johnson")
    );

    public List<BankAccount> getAccounts() {
        return bankAccounts;
    }

    public Client getClientByAccountId (String accountId) {
        return clients.stream().filter(c->c.getAccountId().equals(accountId)).findFirst().orElse(null);
    }

    public Map<BankAccount, Client> getClients (List<BankAccount> accounts) {
        return accounts.stream()
                .collect(Collectors.toMap(
                        account -> account, // Key Mapper
                        account -> clients.stream()
                                .filter(c -> c.getId().equals(account.getClientId()))
                                .findFirst()
                                .orElse(null) // Value Mapper
                ));
    }
}
