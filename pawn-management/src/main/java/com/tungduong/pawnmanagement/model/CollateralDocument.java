package com.tungduong.pawnmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "collateral_documents")
@Getter
@Setter
@NoArgsConstructor
public class CollateralDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "documentType can not be null")
    private String type;

    private String fileUrl;

    private Instant createAt;

    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collateral_id")
     private Collateral collateral;

}
