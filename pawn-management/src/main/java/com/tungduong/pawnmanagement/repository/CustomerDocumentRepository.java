package com.tungduong.pawnmanagement.repository;

import com.tungduong.pawnmanagement.model.CustomerDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDocumentRepository  extends JpaRepository<CustomerDocument, Long>, JpaSpecificationExecutor<CustomerDocument> {
}
