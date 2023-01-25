package com.pdf.heading;

import com.itextpdf.layout.element.Text;

public class H3 extends H {
    public H3(String text) {
        this(new Text(text));
    }

    public H3(Text text) {
        super(text, 3);
        this.setFontSize(16).setBold();
    }
}
