package com.example.account.repository;

import com.example.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findAccountByUsername(String name);

    Account findAccountByEmail(String email);

    Account findAccountByPhoneNumber(String number);

    Account findAccountById(Long id);


    @Modifying(clearAutomatically = true)
    @Query("update Account account set account.enabled = true where account.id = :id")
    @Transactional
    int activateAccountById(@Param("id") Long id);

}
