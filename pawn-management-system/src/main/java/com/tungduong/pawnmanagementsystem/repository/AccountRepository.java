package com.tungduong.pawnmanagementsystem.repository;

import com.tungduong.pawnmanagementsystem.model.Account;
import com.tungduong.pawnmanagementsystem.model.enums.Role;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AccountRepository {

    private final List<Account> accounts = new ArrayList<>(
            List.of(
                    new Account(1L, "admin", "123456", Role.ADMIN),
                    new Account(2L, "staff01", "123456", Role.STAFF),
                    new Account(3L, "staff02", "123456", Role.STAFF),
                    new Account(4L, "customer01", "123456", Role.CUSTOMER),
                    new Account(5L, "customer02", "123456", Role.CUSTOMER),
                    new Account(6L, "customer03", "123456", Role.CUSTOMER)
            )
    );

    Map<Long, Account> accountCache = new HashMap<>();

    public List<Account> findAll() {
        return accounts;
    }

    public Account findById(Long id) {
       return accountCache.get(id);
    }

    public void save(Account account) {
        accounts.add(account);
        accountCache.put(account.getId(), account);
    }
}
