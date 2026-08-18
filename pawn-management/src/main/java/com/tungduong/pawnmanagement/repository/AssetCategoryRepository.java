package com.tungduong.pawnmanagement.repository;

import com.tungduong.pawnmanagement.model.AssetCategory;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetCategoryRepository extends JpaRepository<AssetCategory, Long>, JpaSpecificationExecutor<AssetCategory> {

    boolean existsByName(String name);

    boolean existsByNameAndStatusNot(String name, RecordStatus status);

    boolean existsByNameAndIdNotAndStatusNot(String name, Long id, RecordStatus status);
}


