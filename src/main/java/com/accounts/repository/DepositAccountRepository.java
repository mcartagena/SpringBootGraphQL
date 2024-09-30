package com.accounts.repository;

import com.accounts.entity.DepositAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositAccountRepository extends JpaRepository<DepositAccount, String> {
}
