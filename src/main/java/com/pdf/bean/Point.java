package com.pdf.bean;

public class Point {
    public float x;
    public float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static Point build(float x, float y) {
        return new Point(x, y);
    }

    public Point offset(float offsetX, float offsetY) {
        return Point.build(this.x + offsetX, this.y + offsetY);
    }

    public Point offsetX(float offsetX) {
        return Point.build(this.x + offsetX, this.y);
    }

    public Point offsetY(float offsetY) {
        return Point.build(this.x, this.y + offsetY);
    }
}
