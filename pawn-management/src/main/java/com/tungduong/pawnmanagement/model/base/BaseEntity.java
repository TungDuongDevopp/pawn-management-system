package com.tungduong.pawnmanagement.model.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * Abstract base class that provides automatic auditing fields (createdAt, updatedAt)
 * for JPA entities. Uses @PrePersist and @PreUpdate lifecycle hooks to set timestamps.
 */
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    @Column(updatable = false)
    private Instant createdAt;

    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }
}
