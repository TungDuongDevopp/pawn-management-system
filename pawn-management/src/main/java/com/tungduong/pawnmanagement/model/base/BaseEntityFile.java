package com.tungduong.pawnmanagement.model.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntityFile extends BaseEntity{
    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false)
    private String extension;

    @Column(nullable = false,unique = true)
    private String storageKey;

    @Column(nullable = false)
    private Long fileSize;
}
