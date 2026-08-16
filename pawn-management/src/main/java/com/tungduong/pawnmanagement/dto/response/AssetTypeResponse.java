package com.tungduong.pawnmanagement.dto.response;


import com.tungduong.pawnmanagement.model.enums.CategoryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssetTypeResponse {

    private Long id;

    private String name;

    private String description;

    private CategoryStatus status;
}
