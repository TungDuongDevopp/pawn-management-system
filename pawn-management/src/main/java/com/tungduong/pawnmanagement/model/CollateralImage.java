package com.tungduong.pawnmanagement.model;

import com.tungduong.pawnmanagement.model.base.BaseEntityFile;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "collateral_images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollateralImage extends BaseEntityFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "primaryImage can not be null")
    private Boolean primaryImage;

    private Integer displayOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collateral_id")
    private Collateral collateral;
}
