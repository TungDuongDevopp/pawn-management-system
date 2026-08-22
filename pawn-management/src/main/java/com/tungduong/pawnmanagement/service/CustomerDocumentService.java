package com.tungduong.pawnmanagement.service;

import com.tungduong.pawnmanagement.dto.request.CustomerDocumentRequest;
import com.tungduong.pawnmanagement.dto.request.filter.CustomerDocumentFilterRequest;
import com.tungduong.pawnmanagement.dto.request.update.CustomerDocumentUpdateRequest;
import com.tungduong.pawnmanagement.dto.request.update.RecordStatusUpdateRequest;
import com.tungduong.pawnmanagement.dto.response.CustomerDocumentResponse;
import com.tungduong.pawnmanagement.helper.exception.CanNotManipulateDataException;
import com.tungduong.pawnmanagement.helper.exception.FileStorageException;
import com.tungduong.pawnmanagement.helper.exception.ResourceNotFoundException;
import com.tungduong.pawnmanagement.mapper.CustomerDocumentMapper;
import com.tungduong.pawnmanagement.model.Customer;
import com.tungduong.pawnmanagement.model.CustomerDocument;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import com.tungduong.pawnmanagement.repository.CustomerDocumentRepository;
import com.tungduong.pawnmanagement.repository.CustomerRepository;
import com.tungduong.pawnmanagement.service.interfaces.IFileStorageService;
import com.tungduong.pawnmanagement.service.specification.CustomerDocumentSpecification;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor
public class CustomerDocumentService {
    private final CustomerDocumentRepository customerDocumentRepository;
    private final CustomerDocumentMapper customerDocumentMapper;
    private final IFileStorageService  fileStorageService;
    private final CustomerRepository customerRepository;

    public void ensureManipulable(CustomerDocument customerDocument, Customer customer) {
        if(customerDocument !=null &&(
                customerDocument.getRecordStatus() == RecordStatus.INACTIVE ||
                        customerDocument.getRecordStatus()== RecordStatus.DELETED
                )){
            throw new CanNotManipulateDataException("Customer Document can not be manipulated in its current status");

        }
        if(customer != null &&(customer.getRecordStatus() == RecordStatus.INACTIVE ||
                customer.getRecordStatus() == RecordStatus.DELETED)){
            throw new CanNotManipulateDataException("Customer can not be manipulated in its current status");
        }
    }

    public Page<CustomerDocumentResponse> findAll(CustomerDocumentFilterRequest request, Pageable pageable) {
      Specification<CustomerDocument> spec = Specification.allOf(
              CustomerDocumentSpecification.recordStatusNot(RecordStatus.DELETED),
              CustomerDocumentSpecification.hasContentType(request),
              CustomerDocumentSpecification.hasCustomerId(request),
              CustomerDocumentSpecification.hasFileSize(request),
              CustomerDocumentSpecification.hasDocumentType(request),
              CustomerDocumentSpecification.hasExtension(request)
      );
      return customerDocumentRepository.findAll(spec,pageable).map(customerDocumentMapper::toResponse);
    }

    public CustomerDocumentResponse findById(Long id) {
        return customerDocumentMapper.toResponse(customerDocumentRepository.findByIdAndRecordStatusNot(id, RecordStatus.DELETED)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id " + id)));
    }


    @Transactional
    public CustomerDocumentResponse upload(CustomerDocumentRequest customerDocumentRequest) throws IOException {
        Long customerId = customerDocumentRequest.getCustomerId();
        Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new ResourceNotFoundException("Customer not found with id "+customerId));
        ensureManipulable(null, customer);
        MultipartFile file = customerDocumentRequest.getFile();

        String directory = "customers/" + customerId + "/documents";
        String storageKey = fileStorageService.save(file,directory);

        try{
            CustomerDocument document = new CustomerDocument();
            document.setCustomer(customer);
            document.setFileName(FilenameUtils.getName(file.getOriginalFilename()));
            document.setFileSize(file.getSize());
            document.setCustomerDocumentType(customerDocumentRequest.getCustomerDocumentType());
            document.setExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
            document.setContentType(file.getContentType());
            document.setStorageKey(storageKey);
            return customerDocumentMapper.toResponse(customerDocumentRepository.save(document));
        }
        catch (Exception e){
            fileStorageService.delete(storageKey);
            throw new FileStorageException("Can not save file");
        }

    }

    public Resource download(Long id){
        CustomerDocument document = customerDocumentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Document not found with id "+id));
        ensureManipulable(document, null);
        return fileStorageService.get(document.getStorageKey());
    }

    @Transactional
    public void delete(Long id){
        CustomerDocument document = customerDocumentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Document not found with id "+id));
        ensureManipulable(document, null);
        document.setRecordStatus(RecordStatus.DELETED);
        try{
            fileStorageService.delete(document.getStorageKey());
        }
        catch (Exception e){
            throw new FileStorageException("Can not delete file");
        }
    }

    @Transactional
    public CustomerDocumentResponse replaceFile(Long id,CustomerDocumentUpdateRequest request) throws IOException {
        CustomerDocument document = customerDocumentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id " + id));

        Customer customer = document.getCustomer();

        if(request.getCustomerDocumentType() != null){
            document.setCustomerDocumentType(request.getCustomerDocumentType());
        }

        ensureManipulable(document, customer);

        String oldStorageKey = document.getStorageKey();
        String directory = "customers/" + customer.getId() + "/documents";
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
        return customerDocumentMapper.toResponse(document);
    }

    @Transactional
    public CustomerDocumentResponse updateRecordStatus(Long id, RecordStatusUpdateRequest request) {
        CustomerDocument document = customerDocumentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id " + id));

        if (document.getRecordStatus() == RecordStatus.DELETED) {
            throw new CanNotManipulateDataException("Document cannot be manipulated in its current status");
        }

        document.setRecordStatus(request.getRecordStatus());
        return customerDocumentMapper.toResponse(document);
    }


}
