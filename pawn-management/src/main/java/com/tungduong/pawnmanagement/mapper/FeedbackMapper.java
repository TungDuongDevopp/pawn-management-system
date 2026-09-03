package com.tungduong.pawnmanagement.mapper;

import com.tungduong.pawnmanagement.dto.request.FeedbackRequest;
import com.tungduong.pawnmanagement.dto.response.FeedBackResponse;
import com.tungduong.pawnmanagement.model.Feedback;
import com.tungduong.pawnmanagement.model.FeedbackAttachment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {

    Feedback toEntity(FeedbackRequest feedbackRequest);

    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "attachments", target = "attachmentIds")
    FeedBackResponse toResponse(Feedback feedback);

    default Long map(FeedbackAttachment attachment) {
        return attachment.getId();
    }
}
