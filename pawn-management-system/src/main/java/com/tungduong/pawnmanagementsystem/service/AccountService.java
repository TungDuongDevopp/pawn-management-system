package com.tungduong.pawnmanagementsystem.service;
import com.tungduong.pawnmanagementsystem.model.Account;
import com.tungduong.pawnmanagementsystem.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
    public class AccountService {

        private final AccountRepository repository;

        public AccountService(AccountRepository repository) {
            this.repository = repository;
        }

        public List<Account> getAllAccounts() {
            return repository.findAll();
        }
        public Optional<Account> getAccountById(Long id){
            return repository.findById(id);
        }
        public Account saveAccount(Account account){
            return repository.save(account);
        }

        public boolean deleteAccount(Long id){
            if(!repository.existsById(id)){
                return false;
            }

            repository.deleteById(id);
            return true;
        }
        public Account updateAccount(Account account){
            Optional<Account> optional = getAccountById(account.getId());

            if(optional.isEmpty()){
                return null;
            }

            Account currentAccount = optional.get();
                currentAccount.setStatus(account.getStatus());
                currentAccount.setRole(account.getRole());
                repository.save(currentAccount);
                return currentAccount;
        }
   }

