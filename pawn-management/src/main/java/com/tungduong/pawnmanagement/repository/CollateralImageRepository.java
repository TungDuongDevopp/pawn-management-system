    package com.tungduong.pawnmanagement.repository;

    import com.tungduong.pawnmanagement.model.CollateralImage;
    import com.tungduong.pawnmanagement.model.enums.RecordStatus;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
    import java.util.Optional;

    public interface CollateralImageRepository  extends JpaRepository<CollateralImage, Long>, JpaSpecificationExecutor<CollateralImage> {
        Optional<CollateralImage> findByIdAndRecordStatusNot(Long id, RecordStatus status);

        Optional<CollateralImage> findByCollateralIdAndPrimaryImageTrue(Long collateralId);

        boolean existsByCollateralIdAndDisplayOrderAndIdNot(
                Long collateralId,
                Integer displayOrder,
                Long id
        );
    }
