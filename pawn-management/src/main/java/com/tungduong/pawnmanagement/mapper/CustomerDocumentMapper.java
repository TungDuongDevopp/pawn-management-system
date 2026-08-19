package com.tungduong.pawnmanagement.mapper;

import com.tungduong.pawnmanagement.dto.request.CustomerDocumentRequest;
import com.tungduong.pawnmanagement.dto.response.CustomerDocumentResponse;
import com.tungduong.pawnmanagement.model.CustomerDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerDocumentMapper {
    CustomerDocument toEntity(CustomerDocumentRequest customerDocumentRequest);

    @Mapping(source = "customer.id",target = "customerId")
    CustomerDocumentResponse toResponse(CustomerDocument customerDocument);
}
