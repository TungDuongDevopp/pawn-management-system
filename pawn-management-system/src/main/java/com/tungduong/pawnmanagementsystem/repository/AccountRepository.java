package com.tungduong.pawnmanagementsystem.repository;

import com.tungduong.pawnmanagementsystem.model.Account;
import com.tungduong.pawnmanagementsystem.model.enums.AccountStatus;
import com.tungduong.pawnmanagementsystem.model.enums.Role;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AccountRepository {


    private final List<Account> accounts = new ArrayList<>(
            List.of(
                    new Account(1L, "admin", "123456", Role.ADMIN, AccountStatus.ACTIVE),
                    new Account(2L, "staff01", "123456", Role.STAFF,AccountStatus.ACTIVE),
                    new Account(3L, "staff02", "123456", Role.STAFF,AccountStatus.ACTIVE),
                    new Account(4L, "customer01", "123456", Role.CUSTOMER,AccountStatus.ACTIVE),
                    new Account(5L, "customer02", "123456", Role.CUSTOMER,AccountStatus.ACTIVE),
                    new Account(6L, "customer03", "123456", Role.CUSTOMER,AccountStatus.INACTIVE)
            )
    );
    private final Long nextId = (long) (accounts.size()+1);

    public List<Account> findAll() {
        return accounts;
    }

    public Optional<Account> findById(Long id) {
        for(Account account : accounts){

            if(account.getId().equals(id)){
                return Optional.of(account);
            }

        }
        return Optional.empty();
    }

    public Account save(Account account) {
        account.setId(nextId);
        accounts.add(account);
        return account;
    }

    public boolean delete (Long id){
        Optional<Account> current = findById(id);
        if(current.isEmpty()){
            return false;
        }
        accounts.remove(current.get());
        return true;

    }

    public Account update (Account account){
        Optional<Account> current = findById(account.getId());

        if(current.isEmpty()){
            return null;
        }
        current.get().setRole(account.getRole());
        current.get().setStatus(account.getStatus());
        return current.orElse(null);

    }
    public boolean changePassword(Account account){
        Optional<Account> current = findById(account.getId());

        if(current.isEmpty()){
            return false;
        }
        current.get().setPassword(account.getPassword());
        return true;

    }
}
