package com.pdf.heading;

import com.itextpdf.layout.element.Text;

public class H4 extends H {
    public H4(String text) {
        this(new Text(text));
    }

    public H4(Text text) {
        super(text, 4);
        this.setFontSize(14).setBold();
    }
}
