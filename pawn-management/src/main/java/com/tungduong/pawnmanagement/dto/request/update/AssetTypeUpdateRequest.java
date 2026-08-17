package com.tungduong.pawnmanagement.dto.request.update;

import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssetTypeUpdateRequest {
    private Long id;

    private String name;

    private String description;

    private Long categoryId;

    private RecordStatus status;
}
