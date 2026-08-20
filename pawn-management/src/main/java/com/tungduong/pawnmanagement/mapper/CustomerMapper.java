package com.tungduong.pawnmanagement.mapper;

import com.tungduong.pawnmanagement.dto.request.CustomerRequest;
import com.tungduong.pawnmanagement.dto.response.CustomerDocumentResponse;
import com.tungduong.pawnmanagement.dto.response.CustomerResponse;
import com.tungduong.pawnmanagement.model.Customer;
import com.tungduong.pawnmanagement.model.CustomerDocument;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toEntity(CustomerRequest customerRequest);
    CustomerResponse toResponse(Customer customer);

}
