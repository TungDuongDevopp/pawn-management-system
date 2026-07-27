package com.tungduong.pawnmanagementsystem.model.enums;

public enum StaffStatus  {
    ACTIVE("success"),
    INACTIVE("primary"),
    TERMINATED("danger");

    private final String color;

    StaffStatus(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
