package com.tungduong.pawnmanagement.repository;

import com.tungduong.pawnmanagement.model.Account;
import com.tungduong.pawnmanagement.model.enums.AccountStatus;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> , JpaSpecificationExecutor<Account> {

    boolean existsByUsernameAndStatusNot(
            String username,
            AccountStatus status
    );

    Optional<Account> findByIdAndStatusNot(Long id, AccountStatus status);

    Optional<Account> findByIdAndRecordStatusNotAndStatusNot(Long id, RecordStatus recordStatus, AccountStatus status);
}
