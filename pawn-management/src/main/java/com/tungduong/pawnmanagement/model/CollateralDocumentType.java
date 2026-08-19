package com.tungduong.pawnmanagement.model;

import com.tungduong.pawnmanagement.model.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity()
@Table(name = "collateral_document_type")
@Getter @Setter
@NoArgsConstructor
public class CollateralDocumentType  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "name can not be null")
    private String name;

    private String description;

    @OneToMany(mappedBy = "documentType")
    private List<CollateralDocument> collateralDocuments;

}
