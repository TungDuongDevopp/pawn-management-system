package com.tungduong.pawnmanagement.repository;

import com.tungduong.pawnmanagement.model.CollateralDocumentType;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CollateralDocumentTypeRepository extends JpaRepository<CollateralDocumentType, Long>, JpaSpecificationExecutor<CollateralDocumentType> {

    Optional<CollateralDocumentType> findByIdAndRecordStatusNot(Long id, RecordStatus status);

    boolean existsByNameAndRecordStatusNot(String name, RecordStatus status);

    boolean existsByNameAndIdNotAndRecordStatusNot(String name, Long id, RecordStatus status);
}
