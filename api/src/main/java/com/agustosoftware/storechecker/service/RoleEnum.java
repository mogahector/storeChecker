package com.agustosoftware.storechecker.service;

import java.util.ArrayList;
import java.util.List;

public enum RoleEnum {
    ADMIN("ADMIN"), OWNER("OWNER");

    RoleEnum(String value) {
    }

    public static List<String> getValues() {
        List<String> values = new ArrayList<>();
        for (RoleEnum roleEnum : RoleEnum.values()) {
            values.add(roleEnum.name());
        }
        return values;
    }
}
