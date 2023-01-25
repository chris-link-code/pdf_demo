package com.pdf.heading;

import com.itextpdf.layout.element.Text;

public class H5 extends H {
    public H5(String text) {
        this(new Text(text));
    }

    public H5(Text text) {
        super(text, 5);
        this.setFontSize(12).setBold();
    }
}
