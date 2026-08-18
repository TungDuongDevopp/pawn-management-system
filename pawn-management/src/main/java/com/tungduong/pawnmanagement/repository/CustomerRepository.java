package com.tungduong.pawnmanagement.repository;

import com.tungduong.pawnmanagement.model.Customer;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    boolean existsByEmailAndRecordStatusNot(String email, RecordStatus recordStatus);

    boolean existsByPhoneAndRecordStatusNot(String phone, RecordStatus recordStatus);

    boolean existsByPhoneAndIdNotAndRecordStatusNot(String phone, Long id, RecordStatus recordStatus);

    boolean existsByEmailAndIdNotAndRecordStatusNot(String email, Long id, RecordStatus recordStatus);

    Optional<Customer> findByIdAndRecordStatusNot(Long id, RecordStatus recordStatus);
}

