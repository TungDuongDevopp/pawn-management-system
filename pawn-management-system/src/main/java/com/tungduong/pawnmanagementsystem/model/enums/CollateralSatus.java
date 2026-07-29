package com.tungduong.pawnmanagementsystem.model.enums;

public enum CollateralSatus {
    NEW("success"),
    LIKENEW("secondary"),
    OLD("danger");

    private final String color;

    CollateralSatus (String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

}
