package com.deliverytech.delivery_api.enums;

public enum Role {
    ADMIN("ADMIN"),
    CLIENTE("CLIENTE"),
    RESTAURANTE("RESTAURANTE"),
    ENTREGADOR("ENTREGADOR");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
