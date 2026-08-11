package com.tungduong.pawnmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "asset_types")
@Getter
@Setter
@NoArgsConstructor
public class AssetType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "name can not be null")
    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_category_id")
    AssetCategory category;

    @OneToMany(mappedBy = "type")
    List<Collateral> collaterals;

}
