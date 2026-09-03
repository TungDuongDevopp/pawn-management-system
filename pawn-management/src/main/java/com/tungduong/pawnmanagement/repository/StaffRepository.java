package com.tungduong.pawnmanagement.repository;

import com.tungduong.pawnmanagement.model.Staff;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long>, JpaSpecificationExecutor<Staff> {

    boolean existsByEmailAndRecordStatusNot(String email, RecordStatus recordStatus);

    boolean existsByPhoneAndRecordStatusNot(String phone, RecordStatus recordStatus);

    boolean existsByPhoneAndIdNotAndRecordStatusNot(String phone, Long id, RecordStatus recordStatus);

    boolean existsByEmailAndIdNotAndRecordStatusNot(String email, Long id, RecordStatus recordStatus);

    Optional<Staff> findByIdAndRecordStatusNot(Long id, RecordStatus recordStatus);
}


