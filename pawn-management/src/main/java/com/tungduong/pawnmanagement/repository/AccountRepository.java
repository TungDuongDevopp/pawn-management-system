package com.tungduong.pawnmanagement.repository;

import com.tungduong.pawnmanagement.model.Account;
import com.tungduong.pawnmanagement.model.enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> , JpaSpecificationExecutor<Account> {

    boolean existsByUsernameAndStatusNot(
            String username,
            AccountStatus status
    );

    Optional<Account> findByIdAndStatusNot(Long id, AccountStatus status);
}
