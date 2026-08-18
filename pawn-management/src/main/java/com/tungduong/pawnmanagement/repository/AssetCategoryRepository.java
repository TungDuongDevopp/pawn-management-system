package com.tungduong.pawnmanagement.repository;

import com.tungduong.pawnmanagement.model.AssetCategory;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssetCategoryRepository extends JpaRepository<AssetCategory, Long>, JpaSpecificationExecutor<AssetCategory> {

    Optional<AssetCategory> findByIdAndRecordStatusNot(Long id, RecordStatus status);

    boolean existsByNameAndRecordStatusNot(String name, RecordStatus status);

    boolean existsByNameAndIdNotAndRecordStatusNot(String name, Long id, RecordStatus status);
}


