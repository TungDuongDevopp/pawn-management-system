package com.tungduong.pawnmanagement.dto.request.filter;

import com.tungduong.pawnmanagement.dto.request.baseFileFilter.FileFilterRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollateralDocumentFilterRequest extends FileFilterRequest {

    private Long collateralId;
    private Long collateralTypeId;
}
