package com.pdf.canvas;

public class Header extends HeaderFooter {

    public Header(int pageNumber, PDFCanvas canvas) {
        super(pageNumber, canvas);
        CrossLine crossLine = new CrossLine();
        crossLine.setColor("#EEEEEE");
        crossLine.setWidth(1F);
        this.setCrossLine(crossLine);
    }
}
