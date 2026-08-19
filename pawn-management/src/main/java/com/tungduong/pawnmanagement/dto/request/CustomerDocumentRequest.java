package com.tungduong.pawnmanagement.dto.request;

import com.tungduong.pawnmanagement.model.enums.CustomerDocumentType;
import jakarta.persistence.*;
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
public class CustomerDocumentRequest {

    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull( message = "CustomerDocumentType can not be null")
    private CustomerDocumentType customerDocumentType;

    @NotBlank(message = "fileName can not be null")
    private String fileName;

    @NotNull(message = "CustomerId can not be null")
    private Long customerId;
}
