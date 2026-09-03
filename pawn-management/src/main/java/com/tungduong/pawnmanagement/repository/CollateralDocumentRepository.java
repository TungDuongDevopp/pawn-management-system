package com.tungduong.pawnmanagement.repository;

import com.tungduong.pawnmanagement.model.CollateralDocument;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CollateralDocumentRepository  extends JpaRepository<CollateralDocument,Long>, JpaSpecificationExecutor<CollateralDocument> {
    Optional<CollateralDocument>findByIdAndRecordStatusNot(Long id, RecordStatus status);
}
