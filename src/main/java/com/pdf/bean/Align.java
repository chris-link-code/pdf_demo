package com.pdf.bean;

import com.pdf.canvas.HorizontalAlign;
import com.pdf.canvas.VerticalAlign;
import lombok.Data;

@Data
public class Align {
    private final HorizontalAlign horizontal;
    private final VerticalAlign vertical;


    private Align(HorizontalAlign horizontal, VerticalAlign vertical) {
        this.horizontal = horizontal;
        this.vertical = vertical;
    }

    public static Align align() {
        return new Align(HorizontalAlign.LEFT, VerticalAlign.BOTTOM);
    }

    public static Align horizontal(HorizontalAlign horizontal) {
        return new Align(horizontal, VerticalAlign.BOTTOM);
    }

    public static Align vertical(VerticalAlign vertical) {
        return new Align(HorizontalAlign.LEFT, vertical);
    }

    public static Align align(HorizontalAlign horizontal, VerticalAlign vertical) {
        return new Align(horizontal, vertical);
    }

    /**
     * @param align 位置
     * @param area 整个区域
     * @param block 滑块
     */
    public static Rect get(Align align, Rect area, Rect block) {

        Point point = block.getLt();
        HorizontalAlign horizontal = align.getHorizontal();
        if (horizontal == HorizontalAlign.CENTER) {
            point = point.offsetX((area.getWidth() - block.getWidth()) / 2);
        } else if (horizontal == HorizontalAlign.RIGHT) {
            point = point.offsetX(area.getWidth() - block.getWidth());
        }

        VerticalAlign vertical = align.getVertical();
        if(vertical == VerticalAlign.MIDDLE) {
            point = point.offsetY((area.getHeight() - block.getHeight()) / 2);
        } else if (vertical == VerticalAlign.TOP) {
            point = point.offsetY(area.getHeight() - block.getHeight());
        }
        return new Rect(point, block.getWidth(), block.getHeight());
    }
}
