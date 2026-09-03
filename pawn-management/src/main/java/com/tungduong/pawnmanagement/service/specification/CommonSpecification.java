package com.tungduong.pawnmanagement.service.specification;

import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import org.springframework.data.jpa.domain.Specification;

/**
 * Reusable JPA specifications providing generic helpers for common query patterns:
 * <ul>
 *     <li>Excluding records by {@link RecordStatus}</li>
 *     <li>Range queries on {@link Comparable} fields (e.g. fileSize, salary, values, dates)</li>
 *     <li>Case-insensitive LIKE search on string fields</li>
 *     <li>Case-insensitive equality comparison on string fields</li>
 * </ul>
 */
public final class CommonSpecification {

    private CommonSpecification() {
        // utility class – no instantiation
    }

    /**
     * Excludes records whose {@code recordStatus} matches the given status.
     * Returns conjunction (no-op) if {@code status} is null.
     */
    public static <T> Specification<T> recordStatusNot(RecordStatus status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.notEqual(root.get("recordStatus"), status);
        };
    }

    /**
     * Range query for any {@link Comparable} field.
     * Handles both bounds present, only min present, only max present, or both null (no-op).
     */
    public static <T, C extends Comparable<? super C>> Specification<T> inRange(
            String fieldName, C min, C max) {
        return (root, query, criteriaBuilder) -> {
            if (min != null && max != null) {
                return criteriaBuilder.between(root.get(fieldName), min, max);
            }
            if (min != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get(fieldName), min);
            }
            if (max != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get(fieldName), max);
            }
            return criteriaBuilder.conjunction();
        };
    }

    /**
     * Case-insensitive LIKE query: {@code LOWER(field) LIKE %value.trim().toLowerCase()Boolean%}
     * Returns conjunction (no-op) if {@code value} is null or blank.
     */
    public static <T> Specification<T> likeIgnoreCase(String fieldName, String value) {
        return (root, query, criteriaBuilder) -> {
            if (value == null || value.isBlank()) {
                return criteriaBuilder.conjunction();
            }
            String pattern = "%" + value.trim().toLowerCase() + "%";
            return criteriaBuilder.like(criteriaBuilder.lower(root.get(fieldName)), pattern);
        };
    }

    /**
     * Case-insensitive EQUAL query: {@code LOWER(field) = value.trim().toLowerCase()}
     * Returns conjunction (no-op) if {@code value} is null or blank.
     */
    public static <T> Specification<T> equalIgnoreCase(String fieldName, String value) {
        return (root, query, criteriaBuilder) -> {
            if (value == null || value.isBlank()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(
                    criteriaBuilder.lower(root.get(fieldName)),
                    value.trim().toLowerCase()
            );
        };
    }
}
