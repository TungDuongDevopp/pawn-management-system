package com.tungduong.pawnmanagement.repository;

import com.tungduong.pawnmanagement.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> , JpaSpecificationExecutor<Customer> {

    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);



}
