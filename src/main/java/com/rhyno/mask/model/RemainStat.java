package com.rhyno.mask.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum RemainStat {
    PLENTY("green", "100개 이상"),
    SOME("yellow", "30개 이상 100개 미만"),
    FEW ("red", "2개 이상 30개 미만"),
    EMPTY("gray", "1개 이하");

    private static Map<String, RemainStat> map = new HashMap<>();

    static {
        map.put("plenty", PLENTY);
        map.put("some", SOME);
        map.put("few", FEW);
        map.put("empty", EMPTY);
    }

    private String color;
    private String description;

    RemainStat(String color, String description) {
        this.color = color;
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

    public static RemainStat fromValue(String value) {
        return Optional.ofNullable(value)
                .map(v -> map.get(v))
                .orElse(RemainStat.EMPTY);
    }
}
