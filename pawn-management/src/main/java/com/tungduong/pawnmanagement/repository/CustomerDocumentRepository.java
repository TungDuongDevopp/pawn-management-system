package com.tungduong.pawnmanagement.repository;

import com.tungduong.pawnmanagement.model.CustomerDocument;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerDocumentRepository  extends JpaRepository<CustomerDocument, Long>, JpaSpecificationExecutor<CustomerDocument> {
    Optional<CustomerDocument> findByIdAndRecordStatusNot(Long id, RecordStatus status);
}
