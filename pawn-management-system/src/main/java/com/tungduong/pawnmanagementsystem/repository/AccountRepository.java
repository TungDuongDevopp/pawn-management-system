package com.tungduong.pawnmanagementsystem.repository;

import com.tungduong.pawnmanagementsystem.model.Account;
import com.tungduong.pawnmanagementsystem.model.enums.Role;
import org.springframework.stereotype.Repository;

import java.util.*;

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
        if(findById(id)==null) return false;
        accounts.remove(findById(id));
        return true;

    }

    public Account update (Account account){
        Optional<Account> current = findById(account.getId());

        if(current.isEmpty()){
            return null;
        }

        current.get().setUsername(account.getUsername());
        current.get().setPassword(account.getPassword());
        current.get().setRole(account.getRole());
        return current.orElse(null);

    }
}
