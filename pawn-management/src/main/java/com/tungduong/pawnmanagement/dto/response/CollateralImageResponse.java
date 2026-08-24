package com.tungduong.pawnmanagement.dto.response;

import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollateralImageResponse {
    private Long id;

    private Long collateralId;

    private String fileName;

    private Integer displayOrder;

    private Boolean primaryImage;

    private Long fileSize;

    private String extension;

    private String storageKey;

    private String contentType;

    private RecordStatus recordStatus;

}
