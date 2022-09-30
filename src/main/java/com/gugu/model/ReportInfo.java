package com.gugu.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The type Report info.
 *
 * @author minmin
 * @date 2022 /09/30
 */
@Data
@AllArgsConstructor
public class ReportInfo {
    private String path;
    private String previousSize;
    private String sizeAfter;

    @Override
    public String toString() {
        return String.format("%s %s->%s", path, previousSize, sizeAfter);
    }
}
