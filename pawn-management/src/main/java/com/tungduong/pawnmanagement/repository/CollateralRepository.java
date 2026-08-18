package com.tungduong.pawnmanagement.repository;

import com.tungduong.pawnmanagement.model.Collateral;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CollateralRepository  extends JpaRepository<Collateral,Long>, JpaSpecificationExecutor<Collateral> {


    Optional<Collateral> findByIdAndRecordStatusNot(Long id, RecordStatus status);

}
