package com.pdf.canvas;

import com.pdf.bean.Point;
import lombok.Data;

@Data
public class Line {
    private Point start;
    private Point end;
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

    private Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public static Line build(Point start, Point end) {
        return new Line(start, end);
    }

    public static Line build(Point start, Point end, String color) {
        Line line = new Line(start, end);
        line.color = color;
        return line;
    }

    public static Line build(Point start, Point end, float width) {
        Line line = new Line(start, end);
        line.width = width;
        return line;
    }

    public static Line build(Point start, Point end, float width, float dash) {
        Line line = new Line(start, end);
        line.width = width;
        line.dash = dash;
        return line;
    }

    public static Line build(Point start, Point end, float width, String color) {
        Line line = new Line(start, end);
        line.width = width;
        line.color = color;
        return line;
    }

    public static Line build(Point start, Point end, float width, float dash, String color) {
        Line line = new Line(start, end);
        line.width = width;
        line.dash = dash;
        line.color = color;
        return line;
    }
}
