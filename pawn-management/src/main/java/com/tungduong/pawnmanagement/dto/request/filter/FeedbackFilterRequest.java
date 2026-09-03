package com.tungduong.pawnmanagement.dto.request.filter;

import com.tungduong.pawnmanagement.model.enums.FeedBackStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackFilterRequest {
    private Long AccountId;
    private FeedBackStatus status;
}
