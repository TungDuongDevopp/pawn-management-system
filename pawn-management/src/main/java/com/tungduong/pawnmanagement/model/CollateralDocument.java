package com.tungduong.pawnmanagement.model;

import com.tungduong.pawnmanagement.model.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "collateral_documents")
@Getter
@Setter
@NoArgsConstructor
public class CollateralDocument extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "fileName can not be null")
    private String fileName;

    @NotBlank(message = "contentType can not be null")
    private String contentType;

    @NotBlank(message = "extension can not be null")
    private String extension;

    @NotBlank(message = "storageKey can not be null")
    private String storageKey;

    @NotNull (message = "filesize can not be null")
    private Long fileSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collateral_type_id")
    private CollateralDocumentType documentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collateral_id")
     private Collateral collateral;



}
