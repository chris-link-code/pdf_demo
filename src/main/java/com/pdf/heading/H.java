package com.pdf.heading;

import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import lombok.Getter;

public class H extends Paragraph {
    @Getter
    private final int level;
    @Getter
    private final String text;

    public H(String text, int level) {
        super(text);
        this.text = text;
        this.level = level;
    }

    public H(Text text, int level) {
        super(text);
        this.text = text.getText();
        this.level = level;
    }
}
