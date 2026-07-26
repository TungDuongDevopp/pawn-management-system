package com.tungduong.pawnmanagementsystem.model.enums;

public enum AccountStatus {
    ACTIVE("success"),
    INACTIVE("secondary"),
    LOCKED("danger");

    private final String color;

    AccountStatus(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
