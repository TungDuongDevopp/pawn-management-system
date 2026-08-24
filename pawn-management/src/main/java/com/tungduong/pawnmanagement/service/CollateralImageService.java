package com.tungduong.pawnmanagement.service;

import com.tungduong.pawnmanagement.dto.request.CollateralImageRequest;
import com.tungduong.pawnmanagement.dto.request.filter.CollateralImageFilterRequest;
import com.tungduong.pawnmanagement.dto.request.update.RecordStatusUpdateRequest;
import com.tungduong.pawnmanagement.dto.response.CollateralImageResponse;
import com.tungduong.pawnmanagement.helper.exception.CanNotManipulateDataException;
import com.tungduong.pawnmanagement.helper.exception.DuplicateResourceException;
import com.tungduong.pawnmanagement.helper.exception.FileStorageException;
import com.tungduong.pawnmanagement.helper.exception.ResourceNotFoundException;
import com.tungduong.pawnmanagement.mapper.CollateralImageMapper;
import com.tungduong.pawnmanagement.model.Collateral;
import com.tungduong.pawnmanagement.model.CollateralImage;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import com.tungduong.pawnmanagement.repository.CollateralImageRepository;
import com.tungduong.pawnmanagement.repository.CollateralRepository;
import com.tungduong.pawnmanagement.service.specification.CollateralImageSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CollateralImageService {
    private final CollateralImageRepository collateralImageRepository;
    private final CollateralImageMapper collateralImageMapper;
    private final CollateralRepository collateralRepository;
    private final LocalFileStorageService localFileStorageService;

    private void ensureManipulable(CollateralImage collateralImage,Collateral collateral) {
        if(collateral != null && (collateral.getRecordStatus() == RecordStatus.DELETED||
                collateral.getRecordStatus()== RecordStatus.INACTIVE)){
            throw new CanNotManipulateDataException("Collateral can not be manipulated in its current status");
        }
        if(collateralImage != null && (collateralImage.getRecordStatus() == RecordStatus.DELETED||
                collateralImage.getRecordStatus()== RecordStatus.INACTIVE)){
            throw new CanNotManipulateDataException("Collateral Image can not be manipulated in its current status");
        }
    }

    public Page<CollateralImageResponse> findAll(Pageable pageable, CollateralImageFilterRequest request) {
        Specification<CollateralImage> spec = Specification.allOf(CollateralImageSpecification.hasCollateralId(request),
                CollateralImageSpecification.hasContentType(request),
                CollateralImageSpecification.hasExtension(request),
                CollateralImageSpecification.hasFileSize(request),
                CollateralImageSpecification.recordStatusNot(RecordStatus.DELETED)
        );
        return collateralImageRepository.findAll(spec,pageable).map(collateralImageMapper::toResponse);
    }

    public CollateralImageResponse findById(Long id) {
        return collateralImageMapper.toResponse(collateralImageRepository.findByIdAndRecordStatusNot(id,RecordStatus.DELETED)
                .orElseThrow(()-> new ResourceNotFoundException("Collateral Image not found with id:" + id)));
    }

    @Transactional
    public CollateralImageResponse upload(CollateralImageRequest request) throws IOException {
        Collateral collateral = collateralRepository.findById(request.getCollateralId()).orElseThrow(()-> new ResourceNotFoundException("Collateral not found with id:"+ request.getCollateralId()));
        ensureManipulable(null,collateral);
        MultipartFile file = request.getFile();
        String directory = "collaterals/" + request.getCollateralId() + "/images";
        String storageKey = localFileStorageService.save(file,directory);
        try{
            CollateralImage collateralImage = new CollateralImage();
            collateralImage.setPrimaryImage(false);
            collateralImage.setDisplayOrder(null);
            collateralImage.setFileName(FilenameUtils.getName(file.getOriginalFilename()));
            collateralImage.setFileSize(file.getSize());
            collateralImage.setContentType(file.getContentType());
            collateralImage.setExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
            collateralImage.setCollateral(collateral);
            collateralImage.setStorageKey(storageKey);
           return collateralImageMapper.toResponse(collateralImageRepository.save(collateralImage));

        }
        catch (Exception e){
            localFileStorageService.delete(storageKey);
            throw new FileStorageException("Can not save file");
        }

    }

    @Transactional
    public CollateralImageResponse replaceFile(Long id, MultipartFile file) throws IOException {
        CollateralImage image = collateralImageRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Collateral Image not found with id:" + id));
        Collateral collateral = image.getCollateral();
        ensureManipulable(image,collateral);
        String oldStorageKey = image.getStorageKey();
        String directory = "collaterals/" + collateral.getId() + "/images";
        if(file != null){
            String newStorageKey = localFileStorageService.save(file,directory);
            try{
                image.setStorageKey(newStorageKey);
                image.setFileName(FilenameUtils.getName(file.getOriginalFilename()));
                image.setFileSize(file.getSize());
                image.setContentType(file.getContentType());
                image.setExtension(file.getOriginalFilename());
                localFileStorageService.delete(oldStorageKey);
            }
            catch(Exception e){
                localFileStorageService.delete(newStorageKey);
                throw new FileStorageException("Can not replace file");
            }
        }
        return collateralImageMapper.toResponse(image);
    }

    @Transactional
    public CollateralImageResponse setImagePrimary(Long id) {

        CollateralImage image = collateralImageRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Collateral Image not found with id: " + id
                        )
                );

        ensureManipulable(image, null);

        Long collateralId = image.getCollateral().getId();
            collateralImageRepository
                    .findByCollateralIdAndPrimaryImageTrue(collateralId)
                    .ifPresent(currentPrimary -> {
                        if (!currentPrimary.getId().equals(image.getId())) {
                            currentPrimary.setPrimaryImage(false);
                        }
                    });
            image.setPrimaryImage(true);

        return collateralImageMapper.toResponse(image);
    }

    @Transactional
    public CollateralImageResponse setImageDisplayOrder(Long id, Integer displayOrder) {
        CollateralImage image = collateralImageRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Collateral Image not found with id: " + id
                        )
                );
        ensureManipulable(image, null);
        Long collateralId = image.getCollateral().getId();
        boolean exists = collateralImageRepository
                .existsByCollateralIdAndDisplayOrderAndIdNot(
                        collateralId,
                        displayOrder,
                        image.getId()
                );
        if (exists) {
            throw new DuplicateResourceException(
                    "Collateral Image Order already exists"
            );
        }

        image.setDisplayOrder(displayOrder);
        return collateralImageMapper.toResponse(image);

    }

    public Resource download(Long id) {
        CollateralImage image = collateralImageRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Collateral Image not found with id:" + id));
        ensureManipulable(image,null);
       return localFileStorageService.get(image.getStorageKey());
    }

    @Transactional
    public void deleteById(Long id) {
        CollateralImage image = collateralImageRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Collateral Image not found with id:" + id));
        ensureManipulable(image,null);
        image.setRecordStatus(RecordStatus.DELETED);
        try{
            localFileStorageService.delete(image.getStorageKey());
        }
        catch (Exception e){
            throw new FileStorageException("Can not delete file");
        }

    }

    @Transactional
    public CollateralImageResponse updateRecordStatus(Long id, RecordStatusUpdateRequest request) {
        CollateralImage image = collateralImageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id " + id));

        if (image.getRecordStatus() == RecordStatus.DELETED) {
            throw new CanNotManipulateDataException("Image cannot be manipulated in its current status");
        }

        image.setRecordStatus(request.getRecordStatus());
        return collateralImageMapper.toResponse(image);
    }

}
