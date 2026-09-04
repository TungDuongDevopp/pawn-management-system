package com.tungduong.pawnmanagement.repository;

import com.tungduong.pawnmanagement.model.Collateral;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;

public interface CollateralRepository  extends JpaRepository<Collateral,Long>, JpaSpecificationExecutor<Collateral> {


    Optional<Collateral> findByIdAndRecordStatusNot(Long id, RecordStatus status);

    boolean existsByTypeId(Long typeId);

}
