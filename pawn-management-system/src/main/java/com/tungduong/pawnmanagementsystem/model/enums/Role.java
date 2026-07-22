package com.tungduong.pawnmanagementsystem.model.enums;

public enum Role {
    ADMIN("danger"),
    STAFF("warning"),
    CUSTOMER("primary");

    private final String color;

    Role(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
