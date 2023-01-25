package com.pdf.bean;

import lombok.Data;

@Data
public class Range {
    private float width;
    private float height;

    public Range(float width, float height) {
        this.width = width;
        this.height = height;
    }
}
