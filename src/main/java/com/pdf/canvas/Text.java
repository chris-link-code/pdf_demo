package com.pdf.canvas;

import com.pdf.bean.Align;
import com.pdf.bean.Point;
import lombok.Data;

@Data
public class Text {
    private Point point;
    private String text;
    private FontStyle style;
    private Align align = Align.align();

    public Text(String text) {
        this.text = text;
        this.style = new FontStyle(14, Color.parse("#000000"));
    }

    public Text(Point point, String text) {
        this(text);
        this.point = point;
    }

    public Text(Point point, String text, String color) {
        this(text);
        this.point = point;
        this.style.setColor(Color.parse(color));
    }

    public Text(Point point, String text, int size, String color) {
        this(text);
        this.point = point;
        this.style.setSize(size);
        this.style.setColor(Color.parse(color));
    }

    public Text(Point point, String text, FontStyle style) {
        this(point, text);
        this.style = style;
    }

    public Text style(FontStyle style) {
        this.style = style;
        return this;
    }

    public Text fontSize(int size) {
        this.style.setSize(size);
        return this;
    }

    public Text color(String color) {
        this.style.setColor(Color.parse(color));
        return this;
    }

    public Text bold() {
        this.style.setBold(true);
        return this;
    }

    public Text left() {
        this.align = Align.horizontal(HorizontalAlign.LEFT);
        return this;
    }

    public Text right() {
        this.align = Align.horizontal(HorizontalAlign.RIGHT);
        return this;
    }

    public Text center() {
        this.align = Align.horizontal(HorizontalAlign.CENTER);
        return this;
    }

    public Text top() {
        this.align = Align.vertical(VerticalAlign.TOP);
        return this;
    }

    public Text middle() {
        this.align = Align.vertical(VerticalAlign.MIDDLE);
        return this;
    }

    public Text bottom() {
        this.align = Align.vertical(VerticalAlign.BOTTOM);
        return this;
    }

    public Text align(HorizontalAlign horizontal) {
        this.align = Align.horizontal(horizontal);
        return this;
    }

    /**
     * 绝对居中
     */
    public Text centered() {
        this.align = Align.align(HorizontalAlign.CENTER, VerticalAlign.MIDDLE);
        return this;
    }

    public Text align(VerticalAlign vertical) {
        this.align = Align.vertical(vertical);
        return this;
    }

    public Text align(HorizontalAlign horizontal, VerticalAlign vertical) {
        this.align = Align.align(horizontal, vertical);
        return this;
    }

    public Text move(Point point) {
        Text text = new Text(point, this.text);
        text.align = this.align;
        text.style = this.style;
        return text;
    }
}
