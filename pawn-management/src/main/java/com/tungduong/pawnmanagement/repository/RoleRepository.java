package com.tungduong.pawnmanagement.repository;

import com.tungduong.pawnmanagement.model.Role;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {

    boolean existsByName(String name);

    Optional<Role> findByIdAndRecordStatusNot(Long id, RecordStatus recordStatus);
    boolean existsByNameAndIdNot(String name, Long id);

    boolean existsByNameAndRecordStatusNot(String name, RecordStatus recordStatus);
}

