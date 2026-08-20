package com.tungduong.pawnmanagement.dto.request;

import com.tungduong.pawnmanagement.model.enums.CustomerDocumentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDocumentRequest {

    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull( message = "CustomerDocumentType can not be null")
    private CustomerDocumentType customerDocumentType;

    @NotNull(message = "File can not be null")
    private MultipartFile file;

    @NotNull(message = "CustomerId can not be null")
    private Long customerId;
}
