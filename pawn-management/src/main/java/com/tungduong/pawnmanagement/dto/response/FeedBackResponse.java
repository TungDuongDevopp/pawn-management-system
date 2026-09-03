package com.tungduong.pawnmanagement.dto.response;
import com.tungduong.pawnmanagement.model.enums.FeedBackStatus;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedBackResponse {
    private Long id;

    private String title;

    private String content;

    private Long accountId;

    private RecordStatus recordStatus;

    private FeedBackStatus status;

    private List<Long> attachmentIds;

}
