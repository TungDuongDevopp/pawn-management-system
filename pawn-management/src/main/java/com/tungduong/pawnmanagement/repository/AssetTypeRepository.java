package com.tungduong.pawnmanagement.repository;

import com.tungduong.pawnmanagement.model.AssetType;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetTypeRepository extends JpaRepository<AssetType, Long>, JpaSpecificationExecutor<AssetType> {

    boolean existsByName(String name);

    boolean existsByNameAndStatusNot(String name, RecordStatus status);

    boolean existsByNameAndIdNotAndStatusNot(String name, Long id, RecordStatus status);
}


