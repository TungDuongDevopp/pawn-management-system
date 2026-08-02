package com.tungduong.pawnmanagementsystem.service;
import com.tungduong.pawnmanagementsystem.dto.request.RegisterRequest;
import com.tungduong.pawnmanagementsystem.helper.ResourceNotFoundException;
import com.tungduong.pawnmanagementsystem.model.Account;
import com.tungduong.pawnmanagementsystem.model.enums.AccountStatus;
import com.tungduong.pawnmanagementsystem.model.enums.Role;
import com.tungduong.pawnmanagementsystem.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
    public class AccountService {

        private final AccountRepository repository;
        private final PasswordEncoder passwordEncoder;

    public List<Account> getAllAccounts() {
            return repository.findAll();
        }
        public Account getAccountById(Long id){
            return repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Account not found"));
        }
        public Account getAccountByUsername(String username){
        return repository.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException("Account not found"));
    }
        public Account saveAccount(Account account){

            String hashPassword = passwordEncoder.encode(account.getPassword());
            account.setPassword(hashPassword);
            return repository.save(account);
        }

        public boolean deleteAccountById(Long id){
            if(!repository.existsById(id)){
                return false;
            }

            repository.deleteById(id);
            return true;
        }
        public Account updateAccount(Account account){

            Account currentAccount = getAccountById(account.getId());
                currentAccount.setStatus(account.getStatus());
                currentAccount.setRole(account.getRole());
                repository.save(currentAccount);
                return currentAccount;
        }
        public boolean isExistAccount(String username){
            return repository.existsByUsername(username);
        }

        public Account register(RegisterRequest request){
            Account account = new Account();
            String hashPassword = passwordEncoder.encode(request.getPassword());
            account.setUsername(request.getUsername());
            account.setPassword(hashPassword);
            account.setRole(Role.CUSTOMER);
            account.setStatus(AccountStatus.ACTIVE);
            repository.save(account);
            return account;
        }
   }

