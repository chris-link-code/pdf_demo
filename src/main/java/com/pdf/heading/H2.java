package com.pdf.heading;

import com.itextpdf.layout.element.Text;

public class H2 extends H {
    public H2(String text) {
        this(new Text(text));
    }

    public H2(Text text) {
        super(text, 2);
        this.setFontSize(18).setBold();
    }
}
