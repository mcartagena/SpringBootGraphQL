package com.accounts.service;

import com.accounts.entity.DepositAccount;
import com.accounts.repository.DepositAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DepositService {
    @Autowired
    DepositAccountRepository repository;

    public Boolean save(DepositAccount account) {
        if (repository.findById(account.getAccountId()).isEmpty()){
            repository.save(account);
            return true;
        }
        return false;
    }

    public DepositAccount modify(DepositAccount account) {
        repository.save(account);
        return account;
    }

    public List<DepositAccount> getAccounts() {
        return repository.findAll();
    }

    public DepositAccount accountById(String accountId) {
        return repository.findById(accountId).orElse(null);
    }

    public Boolean delete(String accountId) {
        if (repository.findById(accountId).isPresent()){
            repository.delete(repository.findById(accountId).get());
            return true;
        }
        return false;
    }
}
