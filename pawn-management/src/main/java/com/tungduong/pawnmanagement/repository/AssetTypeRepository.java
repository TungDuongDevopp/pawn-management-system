package com.tungduong.pawnmanagement.repository;

import com.tungduong.pawnmanagement.model.AssetType;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;

public interface AssetTypeRepository extends JpaRepository<AssetType, Long>, JpaSpecificationExecutor<AssetType> {


    boolean existsByNameAndRecordStatusNot(String name, RecordStatus status);

    boolean existsByNameAndIdNotAndRecordStatusNot(String name, Long id, RecordStatus status);

    Optional<AssetType>findByIdAndRecordStatusNot(Long id, RecordStatus status);

    boolean existsByCategoryId(Long categoryId);
}


