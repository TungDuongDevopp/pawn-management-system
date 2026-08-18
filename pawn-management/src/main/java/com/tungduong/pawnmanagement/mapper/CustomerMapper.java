package com.tungduong.pawnmanagement.mapper;

import com.tungduong.pawnmanagement.dto.request.CustomerRequest;
import com.tungduong.pawnmanagement.dto.response.CustomerResponse;
import com.tungduong.pawnmanagement.model.Customer;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toEntity(CustomerRequest customerRequest);
    CustomerResponse toDto(Customer customer);


}
