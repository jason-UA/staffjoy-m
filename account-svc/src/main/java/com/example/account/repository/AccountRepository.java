package com.example.account.repository;

import com.example.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findAccountByUsername(String name);

    Account findAccountByEmail(String email);

    Account findAccountByPhoneNumber(String number);

    Account findAccountById(Long id);

}
