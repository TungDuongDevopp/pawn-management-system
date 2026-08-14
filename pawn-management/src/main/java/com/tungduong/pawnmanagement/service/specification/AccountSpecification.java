package com.tungduong.pawnmanagement.service.specification;

import com.tungduong.pawnmanagement.dto.request.AccountFilterRequest;
import com.tungduong.pawnmanagement.model.Account;
import com.tungduong.pawnmanagement.model.enums.AccountStatus;
import org.springframework.data.jpa.domain.Specification;

public class AccountSpecification {

    public static Specification<Account> statusNot(AccountStatus status) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (status == null) return criteriaBuilder.conjunction();
            return criteriaBuilder.notEqual(root.get("status"), status);
        };
    }

    public static Specification<Account> hasUsername(AccountFilterRequest request) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (request == null || request.getUsername() == null || request.getUsername().isBlank()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("username"), request.getUsername());
        };
    }

    public static Specification<Account> hasStatus(AccountFilterRequest request) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (request == null || request.getStatus() == null || request.getStatus().isBlank()) {
                return criteriaBuilder.conjunction();
            }
            try {
                AccountStatus statusEnum = AccountStatus.valueOf(request.getStatus().trim().toUpperCase());
                return criteriaBuilder.equal(root.get("status"), statusEnum);
            } catch (IllegalArgumentException e) {
                return criteriaBuilder.conjunction();
            }
        };
    }

    public static Specification<Account> hasRole(AccountFilterRequest request) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (request == null || request.getRoleName() == null || request.getRoleName().isBlank()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(criteriaBuilder.lower(root.get("role").get("name")), request.getRoleName().trim().toLowerCase());
        };
    }
}
