package com.tungduong.pawnmanagement.dto.request.filter;

import com.tungduong.pawnmanagement.dto.request.baseFileFilter.FileFilterRequest;
import com.tungduong.pawnmanagement.model.enums.CustomerDocumentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDocumentFilterRequest  extends FileFilterRequest {

    private Long customerId;
    private CustomerDocumentType customerDocumentType;

}
