package com.tungduong.pawnmanagement.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollateralDocumentRequest {

    private Long id;

    @NotNull(message = "file can not be null")
    private MultipartFile file;

    @NotNull(message = "Collateral can not be null")
    private Long collateralId;

    @NotNull(message = "CollateralType can not be null")
    private Long collateralTypeId;
}
