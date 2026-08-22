package com.tungduong.pawnmanagement.dto.response;

import com.tungduong.pawnmanagement.model.enums.CustomerDocumentType;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollateralDocumentResponse {
    private Long id;

    private Long collateralId;

    private Long collateralTypeId;

    private String fileName;

    private Long fileSize;

    private String extension;

    private String storageKey;

    private String contentType;

    private RecordStatus recordStatus;
}
