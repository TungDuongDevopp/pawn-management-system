package com.tungduong.pawnmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "asset_categories")
@Getter
@Setter
@NoArgsConstructor
public class AssetCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "name can not be null")
    private String name;

    private String description;

    @OneToMany(mappedBy = "category")
    private List<AssetType> assetTypes;
}
