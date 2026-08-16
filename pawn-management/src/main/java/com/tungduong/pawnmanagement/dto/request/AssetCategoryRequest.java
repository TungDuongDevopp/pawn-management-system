package com.tungduong.pawnmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssetCategoryRequest {

    private Long id;
    @NotBlank( message = "Asset Category can not be null")
    private String name;

    private String description;
}
