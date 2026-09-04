package com.tungduong.pawnmanagement.service;

import com.tungduong.pawnmanagement.dto.request.FeedbackRequest;
import com.tungduong.pawnmanagement.dto.request.filter.FeedbackFilterRequest;
import com.tungduong.pawnmanagement.dto.request.update.RecordStatusUpdateRequest;
import com.tungduong.pawnmanagement.dto.response.FeedBackResponse;
import com.tungduong.pawnmanagement.helper.EntityGuard;
import com.tungduong.pawnmanagement.helper.exception.CanNotManipulateDataException;
import com.tungduong.pawnmanagement.helper.exception.FileStorageException;
import com.tungduong.pawnmanagement.helper.exception.ResourceNotFoundException;
import com.tungduong.pawnmanagement.mapper.FeedbackMapper;
import com.tungduong.pawnmanagement.model.Account;
import com.tungduong.pawnmanagement.model.Feedback;
import com.tungduong.pawnmanagement.model.FeedbackAttachment;
import com.tungduong.pawnmanagement.model.enums.AccountStatus;
import com.tungduong.pawnmanagement.model.enums.FeedBackStatus;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import com.tungduong.pawnmanagement.repository.AccountRepository;
import com.tungduong.pawnmanagement.repository.FeedbackAttachmentRepository;
import com.tungduong.pawnmanagement.repository.FeedbackRepository;
import com.tungduong.pawnmanagement.service.specification.FeedbackSpecification;
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
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;
    private final AccountRepository accountRepository;
    private final LocalFileStorageService localFileStorageService;
    private final FeedbackAttachmentRepository feedbackAttachmentRepository;

    private void ensureManipulable(Account account, FeedbackAttachment attachment) {
        if (account != null) {
            EntityGuard.requireManipulable(account, "Account");
            if (account.getStatus() != AccountStatus.ACTIVE) {
                throw new CanNotManipulateDataException("Account can not be manipulated in its current status");
            }
        }

        if (attachment != null) {
            EntityGuard.requireManipulable(attachment, "Feedback attachment");
        }
    }


    public Page<FeedBackResponse> findAll(Pageable pageable, FeedbackFilterRequest request) {
        Specification<Feedback> spec = Specification.allOf(
                FeedbackSpecification.feedbackStatusNot(FeedBackStatus.CANCELLED),
                FeedbackSpecification.feedbackStatus(request),
                FeedbackSpecification.hasAccountId(request),
                FeedbackSpecification.recordStatusNot(RecordStatus.DELETED)
        );
        return feedbackRepository.findAll(spec,pageable).map(feedbackMapper::toResponse);
    }

    public FeedBackResponse findById(Long id) {
        return feedbackMapper.toResponse(feedbackRepository.findByIdAndRecordStatusNotAndStatusNot(id,RecordStatus.DELETED,FeedBackStatus.CANCELLED)
                .orElseThrow(()-> new ResourceNotFoundException("Feedback not found with id:" + id)));
    }

    @Transactional
    public FeedBackResponse create(FeedbackRequest request) throws IOException {
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Account not found with id: " + request.getAccountId()
                        )
                );
        ensureManipulable(account,null);
        Feedback feedback = new Feedback();
        feedback.setAccount(account);
        feedback.setContent(request.getContent());
        feedback.setTitle(request.getTitle());
        feedback.setStatus(FeedBackStatus.NEW);

        feedback = feedbackRepository.save(feedback);

        if (request.getFile() != null && !request.getFile().isEmpty()) {
            MultipartFile file = request.getFile();

            String directory =
                    "accounts/" + account.getId() + "/feedbacks/" + feedback.getId();

            String storageKey = localFileStorageService.save(file, directory);

            try {
                FeedbackAttachment attachment = new FeedbackAttachment();

                attachment.setFeedback(feedback);
                attachment.setFileName(
                        FilenameUtils.getName(file.getOriginalFilename())
                );
                attachment.setFileSize(file.getSize());
                attachment.setExtension(
                        FilenameUtils.getExtension(file.getOriginalFilename())
                );
                attachment.setContentType(file.getContentType());
                attachment.setStorageKey(storageKey);

                feedbackAttachmentRepository.save(attachment);

                if(feedback.getAttachments() == null){
                    feedback.setAttachments(new ArrayList<>());
                }
                
                feedback.getAttachments().add(attachment);
            } catch (Exception e) {
                localFileStorageService.delete(storageKey);
                throw new FileStorageException("Cannot save feedback attachment");
            }
        }


        return feedbackMapper.toResponse(feedback);
    }

    public Resource download(Long id){
        FeedbackAttachment attachment = feedbackAttachmentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Attachment not found with id "+id));
        EntityGuard.requireNotDeleted(attachment, "Attachment");
        return localFileStorageService.get(attachment.getStorageKey());
    }

    @Transactional
    public FeedBackResponse updateRecordStatus(Long id, RecordStatusUpdateRequest request) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found with id " + id));

        EntityGuard.requireNotDeleted(feedback, "Feedback");

        feedback.setRecordStatus(request.getRecordStatus());
        return feedbackMapper.toResponse(feedback);
    }

}
