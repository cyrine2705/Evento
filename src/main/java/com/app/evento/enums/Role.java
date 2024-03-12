package com.app.evento.enums;

public enum Role {
    ADMIN("ADMIN"),AGENT("AGENT");
    private final String value;



    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
