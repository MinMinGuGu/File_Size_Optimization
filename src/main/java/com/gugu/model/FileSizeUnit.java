package com.gugu.model;

import lombok.ToString;

/**
 * The type File size unit.
 *
 * @author minmin
 * @date 2022 /09/30
 */
@ToString
public class FileSizeUnit {
    private final Long byteNum;
    private final SizeUnit sizeUnit;

    /**
     * Instantiates a new File size unit.
     *
     * @param byteNum  the byte num
     * @param sizeUnit the size unit
     */
    public FileSizeUnit(Long byteNum, SizeUnit sizeUnit) {
        this.byteNum = byteNum;
        this.sizeUnit = sizeUnit;
    }

    /**
     * Get file size byte count long.
     *
     * @return the long
     */
    public long getFileSizeByteCount(){
        return sizeUnit.countBytes(byteNum);
    }
}
