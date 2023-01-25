package com.pdf.bean;

import lombok.Data;

@Data
public class Rect {
    /**
     * 为左上坐标点
     */
    private Point point;
    private float width;
    private float height;

    public Rect(float x, float y, float width, float height) {
        this.point = Point.build(x, y);
        this.width = width;
        this.height = height;
    }

    public Rect(Point point, float width, float height) {
        this.point = point;
        this.width = width;
        this.height = height;
    }

    public Point getLt() {
        return this.point;
    }

    public Point getRt() {
        return this.point.offsetX(this.width);
    }

    public Point getLb() {
        return this.point.offsetY(-this.height);
    }

    public Point getRb() {
        return this.point.offset(this.width, -this.height);
    }
}
