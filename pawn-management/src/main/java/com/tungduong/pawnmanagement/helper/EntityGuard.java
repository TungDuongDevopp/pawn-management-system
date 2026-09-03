package com.tungduong.pawnmanagement.helper;

import com.tungduong.pawnmanagement.helper.exception.CanNotManipulateDataException;
import com.tungduong.pawnmanagement.model.base.BaseEntity;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;

/**
 * Utility class that centralises RecordStatus guard checks shared across multiple services.
 *
 * <p>Only RecordStatus-level guards belong here. Business-specific status checks
 * (e.g. AssetStatus, AccountStatus) remain in their respective service classes.</p>
 */
public final class EntityGuard {

    private EntityGuard() {
        // utility class – no instantiation
    }

    /**
     * Throws {@link CanNotManipulateDataException} when the entity's
     * {@code recordStatus} is either {@code DELETED} or {@code INACTIVE}.
     *
     * @param entity     the entity to check (must not be null)
     * @param entityName human-readable entity name used in the exception message
     */
    public static void requireManipulable(BaseEntity entity, String entityName) {
        if (entity.getRecordStatus() == RecordStatus.DELETED
                || entity.getRecordStatus() == RecordStatus.INACTIVE) {
            throw new CanNotManipulateDataException(
                    entityName + " cannot be manipulated in its current status"
            );
        }
    }

    /**
     * Throws {@link CanNotManipulateDataException} when the entity's
     * {@code recordStatus} is {@code DELETED}.
     *
     * <p>Typically used in {@code updateRecordStatus()} methods where
     * INACTIVE records are still allowed to be re-activated.</p>
     *
     * @param entity     the entity to check (must not be null)
     * @param entityName human-readable entity name used in the exception message
     */
    public static void requireNotDeleted(BaseEntity entity, String entityName) {
        if (entity.getRecordStatus() == RecordStatus.DELETED) {
            throw new CanNotManipulateDataException(
                    entityName + " cannot be manipulated in its current status"
            );
        }
    }
}
