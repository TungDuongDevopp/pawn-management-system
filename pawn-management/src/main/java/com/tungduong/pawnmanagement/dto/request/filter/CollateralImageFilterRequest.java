package com.tungduong.pawnmanagement.dto.request.filter;

import com.tungduong.pawnmanagement.dto.request.baseFileFilter.FileFilterRequest;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollateralImageFilterRequest extends FileFilterRequest {
    private Long collateralId;

}
