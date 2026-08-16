package com.tungduong.pawnmanagement.model;

import com.tungduong.pawnmanagement.model.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "documentType can not be null")
    private String type;

    private String fileUrl;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collateral_id")
     private Collateral collateral;

}
