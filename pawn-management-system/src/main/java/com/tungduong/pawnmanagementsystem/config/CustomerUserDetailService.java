package com.tungduong.pawnmanagementsystem.config;

import com.tungduong.pawnmanagementsystem.model.Account;
import com.tungduong.pawnmanagementsystem.service.AccountService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerUserDetailService  implements UserDetailsService {

    private final AccountService service;

    public CustomerUserDetailService(AccountService service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> opt = service.getAccountByUsername(username);
        Account account = opt.orElseThrow( () -> new UsernameNotFoundException("User not found"));

        return User.builder()
                .username(account.getUsername())
                .password(account.getPassword())
                .authorities("ROLE_"+account.getRole())
                .build();
    }
}
