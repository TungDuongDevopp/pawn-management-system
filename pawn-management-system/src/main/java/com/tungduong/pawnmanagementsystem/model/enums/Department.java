package com.tungduong.pawnmanagementsystem.model.enums;

public enum Department {
    REVIEW("primary"),
    CONTRACT("success");

    private final String color;

    Department(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
