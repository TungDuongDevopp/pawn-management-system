package com.tungduong.pawnmanagement.dto.response;

import com.tungduong.pawnmanagement.model.enums.CustomerDocumentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDocumentResponse {
    private Long id;

    private CustomerDocumentType customerDocumentType;

    private String fileName;

    private Long fileSize;

    private String extension;

    private String storageKey;

    private String contentType;

    private Long customerId;
}
