package com.tungduong.pawnmanagement.dto.request.filter;

import com.tungduong.pawnmanagement.model.enums.CustomerDocumentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollateralDocumentFilterRequest {

    private Long collateralId;

    private String contentType;
    private String extension;

    private Long minFileSize;
    private Long maxFileSize;

    private Long collateralTypeId;
}
