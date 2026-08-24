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
public class CollateralImageRequest {

    private Long id;

    @NotNull(message = "file can not be null")
    private MultipartFile file;

    @NotNull(message = "collateralId can not be null")
    private Long collateralId;


}
