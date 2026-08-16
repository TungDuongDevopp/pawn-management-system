package com.tungduong.pawnmanagement.repository;

import com.tungduong.pawnmanagement.model.AssetCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AssetCategoryRepository extends JpaRepository<AssetCategory, Long> , JpaSpecificationExecutor<AssetCategory> {
    boolean existsByName(String name);
}
