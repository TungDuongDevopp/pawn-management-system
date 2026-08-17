package com.tungduong.pawnmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssetTypeRequest {
    private Long id;

    @NotBlank( message = "Asset Type can not be null")
    private String name;

    private String description;

    @NotNull(message = "Asset Category can not be null")
    private Long categoryId;


}
