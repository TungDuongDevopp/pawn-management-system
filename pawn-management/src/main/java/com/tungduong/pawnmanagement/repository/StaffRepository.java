package com.tungduong.pawnmanagement.repository;

import com.tungduong.pawnmanagement.model.Staff;
import com.tungduong.pawnmanagement.model.enums.Department;
import com.tungduong.pawnmanagement.model.enums.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StaffRepository  extends JpaRepository<Staff,Long>, JpaSpecificationExecutor<Staff> {

    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

}
