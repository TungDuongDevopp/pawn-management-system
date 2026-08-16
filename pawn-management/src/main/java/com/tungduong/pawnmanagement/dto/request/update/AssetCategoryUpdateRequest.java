package com.tungduong.pawnmanagement.dto.request.update;


import com.tungduong.pawnmanagement.model.enums.CategoryStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssetCategoryUpdateRequest {
    private Long id;

    private String name;

    private String description;

    private CategoryStatus status;
}
