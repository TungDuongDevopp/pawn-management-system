package com.tungduong.pawnmanagement.model.base;

import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * Abstract base class that provides automatic auditing fields (createdAt, updatedAt)
 * and a soft-delete mechanism via {@code recordStatus} for JPA entities.
 * <ul>
 *   <li>ACTIVE   – record is fully operational</li>
 *   <li>INACTIVE – record is view-only; all write operations are blocked</li>
 *   <li>DELETED  – record is logically deleted; hidden from all queries</li>
 * </ul>
 */
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    @Column(updatable = false)
    private Instant createdAt;

    private Instant updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecordStatus recordStatus;

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
        if (this.recordStatus == null) {
            this.recordStatus = RecordStatus.ACTIVE;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }
}
