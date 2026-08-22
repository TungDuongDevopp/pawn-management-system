package com.tungduong.pawnmanagement.service;

import com.tungduong.pawnmanagement.dto.request.CollateralDocumentRequest;
import com.tungduong.pawnmanagement.dto.request.filter.CollateralDocumentFilterRequest;
import com.tungduong.pawnmanagement.dto.request.update.CollateralDocumentUpdateRequest;
import com.tungduong.pawnmanagement.dto.request.update.RecordStatusUpdateRequest;
import com.tungduong.pawnmanagement.dto.response.CollateralDocumentResponse;
import com.tungduong.pawnmanagement.helper.exception.CanNotManipulateDataException;
import com.tungduong.pawnmanagement.helper.exception.FileStorageException;
import com.tungduong.pawnmanagement.helper.exception.ResourceNotFoundException;
import com.tungduong.pawnmanagement.mapper.CollateralDocumentMapper;
import com.tungduong.pawnmanagement.model.Collateral;
import com.tungduong.pawnmanagement.model.CollateralDocument;
import com.tungduong.pawnmanagement.model.CollateralDocumentType;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import com.tungduong.pawnmanagement.repository.CollateralDocumentRepository;
import com.tungduong.pawnmanagement.repository.CollateralDocumentTypeRepository;
import com.tungduong.pawnmanagement.repository.CollateralRepository;
import com.tungduong.pawnmanagement.service.specification.CollateralDocumentSpecification;
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
public class CollateralDocumentService {
    private final CollateralDocumentRepository collateralDocumentRepository;
    private final CollateralDocumentMapper collateralDocumentMapper;
    private final CollateralRepository collateralRepository;
    private final CollateralDocumentTypeRepository collateralDocumentTypeRepository;
    private final LocalFileStorageService fileStorageService;

    public void ensureManipulable(CollateralDocument collateralDocument, CollateralDocumentType collateralDocumentType,Collateral collateral) {
        if(collateralDocument!=null &&(collateralDocument.getRecordStatus() == RecordStatus.INACTIVE
                || collateralDocument.getRecordStatus() == RecordStatus.DELETED)){
            throw new CanNotManipulateDataException("Collateral Document  can not be manipulated in its current status");
        }

        if(collateralDocumentType!=null&& (collateralDocumentType.getRecordStatus() == RecordStatus.INACTIVE
               || collateralDocumentType.getRecordStatus() == RecordStatus.DELETED)){
            throw new CanNotManipulateDataException("Collateral Document Type can not be manipulated in its current status");
        }

        if(collateral!=null&& (collateral.getRecordStatus() == RecordStatus.INACTIVE
                || collateral.getRecordStatus() == RecordStatus.DELETED)){
            throw new CanNotManipulateDataException("Collateral can not be manipulated in its current status");
        }
    }

    public Page<CollateralDocumentResponse> findAll(Pageable pageable, CollateralDocumentFilterRequest request) {
        Specification<CollateralDocument> spec = Specification.allOf(
                CollateralDocumentSpecification.hasCollateralId(request),
                CollateralDocumentSpecification.hasCollateralTypeId(request),
                CollateralDocumentSpecification.recordStatusNot(RecordStatus.DELETED),
                CollateralDocumentSpecification.hasFileSize(request),
                CollateralDocumentSpecification.hasContentType(request),
                CollateralDocumentSpecification.hasExtension(request)
        );
        return collateralDocumentRepository.findAll(spec, pageable).map(collateralDocumentMapper::toResponse);
    }

    public CollateralDocumentResponse findById(Long id) {
        return collateralDocumentMapper.toResponse(collateralDocumentRepository.findByIdAndRecordStatusNot(id,RecordStatus.DELETED)
                .orElseThrow(()->new ResourceNotFoundException("Collateral Document not found with id:"+id)));
    }

    @Transactional
    public CollateralDocumentResponse upload(CollateralDocumentRequest request) throws IOException {
        Collateral collateral = collateralRepository.findById(request.getCollateralId())
                                                    .orElseThrow(()->new ResourceNotFoundException("Collateral Document not found with id:"+request.getCollateralId()));
        CollateralDocumentType type = collateralDocumentTypeRepository.findById(request.getCollateralTypeId())
                                                                      .orElseThrow(()-> new ResourceNotFoundException("Collateral Document Type not found with id:"+request.getCollateralTypeId()));
        ensureManipulable(null, type, collateral);
        MultipartFile file = request.getFile();
        String directory = "collaterals/" + request.getCollateralId() + "/documents";
        String storageKey = fileStorageService.save(file,directory);
        try{
            CollateralDocument document = new CollateralDocument();
            document.setCollateral(collateral);
            document.setDocumentType(type);
            document.setFileName(FilenameUtils.getName(file.getOriginalFilename()));
            document.setFileSize(file.getSize());
            document.setContentType(file.getContentType());
            document.setExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
            document.setStorageKey(storageKey);
            return collateralDocumentMapper.toResponse(collateralDocumentRepository.save(document));
        }
        catch (Exception e){
            fileStorageService.delete(storageKey);
            throw new FileStorageException("Can not save file");
        }
    }

    public Resource download(Long id){
        CollateralDocument document = collateralDocumentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Document not found with id "+id));
       ensureManipulable(document,null,null);
        return fileStorageService.get(document.getStorageKey());
    }


    @Transactional
    public CollateralDocumentResponse replaceFile(Long id ,CollateralDocumentUpdateRequest request) throws IOException {
        CollateralDocument document = collateralDocumentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Document not found with id "+id));
        Collateral collateral = document.getCollateral();
        CollateralDocumentType type = document.getDocumentType();

        if(request.getCollateralTypeId()!=null){
            type = collateralDocumentTypeRepository.findById(request.getCollateralTypeId()).orElseThrow(()->new ResourceNotFoundException("Collateral Document Type not found with id:"+request.getCollateralTypeId()));
            document.setDocumentType(type);
        }
        ensureManipulable(document,type,collateral);
        String oldStorageKey =  document.getStorageKey();
        String directory = "collaterals/" + collateral.getId() + "/documents";

        if(request.getFile() != null){
            MultipartFile file = request.getFile();
            String newStorageKey = fileStorageService.save(file,directory);
            try{
                document.setStorageKey(newStorageKey);
                document.setFileName(FilenameUtils.getName(file.getOriginalFilename()));
                document.setFileSize(file.getSize());
                document.setExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
                document.setContentType(file.getContentType());
                fileStorageService.delete(oldStorageKey);
            }
            catch (Exception e){
                fileStorageService.delete(newStorageKey);
                throw new FileStorageException("Can not replace file");
            }
        }
        return collateralDocumentMapper.toResponse(document);

    }

    @Transactional
    public CollateralDocumentResponse updateRecordStatus(Long id, RecordStatusUpdateRequest request) {
        CollateralDocument document = collateralDocumentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id " + id));

        if (document.getRecordStatus() == RecordStatus.DELETED) {
            throw new CanNotManipulateDataException("Document cannot be manipulated in its current status");
        }

        document.setRecordStatus(request.getRecordStatus());
        return collateralDocumentMapper.toResponse(document);
    }

    @Transactional
    public void delete(Long id){
        CollateralDocument document = collateralDocumentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Document not found with id "+id));
        ensureManipulable(document,null,null);
        document.setRecordStatus(RecordStatus.DELETED);
        try{
            fileStorageService.delete(document.getStorageKey());
        }
        catch (Exception e){
            throw new FileStorageException("Can not delete file");
        }
    }

}
