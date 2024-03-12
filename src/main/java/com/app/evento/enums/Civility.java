package com.app.evento.enums;

public enum Civility {
    MR("Mr"),MME("Mme");

    private final String value;

    Civility(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
