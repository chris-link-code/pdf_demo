package com.pdf.heading;

import com.itextpdf.layout.element.Text;

public class H1 extends H {

    public H1(String text) {
        this(new Text(text));
    }

    public H1(Text text) {
        super(text, 1);
        this.setFontSize(20).setBold();
    }
}
