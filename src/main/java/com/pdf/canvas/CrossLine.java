package com.pdf.canvas;

import lombok.Data;

@Data
public class CrossLine {
    /**
     * 虚线间隔
     */
    private float dash;
    /**
     * 线条宽度
     */
    private float width;
    /**
     * 线条颜色
     */
    private String color;

    public static CrossLine build(String color) {
        return build(color, 0, 0);
    }

    public static CrossLine build(float width) {
        return build(null, width, 0);
    }

    public static CrossLine build(float width, float dash) {
        return build(null, width, dash);
    }

    public static CrossLine build(String color, float width) {
        return build(color, width, 0);
    }

    public static CrossLine build(String color, float width, float dash) {
        CrossLine line = new CrossLine();
        line.color = color;
        line.width = width;
        line.dash = dash;
        return line;
    }
}
