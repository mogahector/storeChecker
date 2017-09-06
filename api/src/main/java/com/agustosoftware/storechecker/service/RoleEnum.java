package com.agustosoftware.storechecker.service;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public enum RoleEnum {
    ADMIN(0, "ADMIN"),
    OWNER(25,"OWNER"),
    DISTRICT_MANAGER(50, "DISTRICT_MANAGER"),
    MANAGER(100, "MANAGER");

    private int weight;
    private String name;

    RoleEnum(int weight, String name) {
        this.weight = weight;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public static RoleEnum fromName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }

        for (RoleEnum roleEnum : values()) {
            if (roleEnum.name.equalsIgnoreCase(name)) {
                return roleEnum;
            }
        }

        return null;
    }

    public static List<String> getValues() {
        List<String> values = new ArrayList<>();
        for (RoleEnum roleEnum : RoleEnum.values()) {
            values.add(roleEnum.getName());
        }
        return values;
    }
}
