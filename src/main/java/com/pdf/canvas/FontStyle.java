package com.pdf.canvas;

import lombok.Data;

@Data
public class FontStyle {
    private int size = 12;
    private Color color;
    private boolean bold;

    public FontStyle(int size) {
        this.size = size;
    }

    public FontStyle(Color color) {
        this.color = color;
    }

    public FontStyle(int size, Color color) {
        this.size = size;
        this.color = color;
    }
}
