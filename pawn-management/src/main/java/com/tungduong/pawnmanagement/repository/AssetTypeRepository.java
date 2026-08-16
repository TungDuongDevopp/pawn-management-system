package com.tungduong.pawnmanagement.repository;

import com.tungduong.pawnmanagement.model.AssetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AssetTypeRepository extends JpaRepository<AssetType, Long>, JpaSpecificationExecutor<AssetType> {
}
