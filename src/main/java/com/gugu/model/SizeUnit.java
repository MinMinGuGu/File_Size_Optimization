package com.gugu.model;

import lombok.Getter;

import java.text.DecimalFormat;

/**
 * The enum Size unit.
 *
 * @author minmin
 * @date 2022 /09/30
 */
@Getter
public enum SizeUnit {
    /**
     * 1024 * 1024 byte
     */
    MB("MB"),
    /**
     * 1024 * 1024 * 1024 byte
     */
    GB("GB");

    private static final String GB_STR = "GB";

    private static final String MB_STR = "MB";

    private final long defaultValue;

    private final String unit;

    SizeUnit(String unit) {
        this.unit = unit;
        if (MB_STR.equals(unit)) {
            this.defaultValue = 1024 * 1024;
        } else {
            this.defaultValue = 1024 * 1024 * 1024;
        }
    }

    /**
     * Count bytes long.
     *
     * @param value the value
     * @return the long
     */
    public long countBytes(long value) {
        return value * defaultValue;
    }

    /**
     * Format string.
     *
     * @param value the value
     * @return the string
     */
    public String format(long value) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return String.format("%s%s", decimalFormat.format((double) value / defaultValue), unit);
    }
}
