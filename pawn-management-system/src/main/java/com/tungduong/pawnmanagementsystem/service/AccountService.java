package com.tungduong.pawnmanagementsystem.service;
import com.tungduong.pawnmanagementsystem.model.Account;
import com.tungduong.pawnmanagementsystem.repository.AccountRepository;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
    public class AccountService {

        private final AccountRepository repository;

        public AccountService(AccountRepository repository) {
            this.repository = repository;
        }

        public List<Account> getAllAccounts() {
            return repository.findAll();
        }
        public Account getAccountById(Long id){
            return repository.findById(id).orElse(null);
        }
        public Account createAccount(Account account){
            return repository.save(account);
        }
        public boolean deleteAccount(Long id){
            return repository.delete(id);
        }
        public Account updateAccount(Account account){
            return repository.update(account);
        }
   }

