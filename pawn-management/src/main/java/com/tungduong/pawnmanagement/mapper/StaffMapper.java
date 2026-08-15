package com.tungduong.pawnmanagement.mapper;

import com.tungduong.pawnmanagement.dto.request.StaffRequest;
import com.tungduong.pawnmanagement.dto.response.StaffResponse;
import com.tungduong.pawnmanagement.model.Staff;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StaffMapper {
    Staff toEntity(StaffRequest staffRequest);
    StaffResponse toDto(Staff staff);
    List<StaffResponse> toList(List<Staff> staffList);
}
