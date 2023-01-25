package com.pdf.canvas;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;

public class InCanvas extends Canvas {
    public InCanvas(PdfPage page, Rectangle rootArea) {
        super(page, rootArea);
    }

    public InCanvas(PdfCanvas pdfCanvas, Rectangle rootArea) {
        super(pdfCanvas, rootArea);
    }

    public InCanvas(PdfCanvas pdfCanvas, Rectangle rootArea, boolean immediateFlush) {
        super(pdfCanvas, rootArea, immediateFlush);
    }

    public InCanvas(PdfFormXObject formXObject, PdfDocument pdfDocument) {
        super(formXObject, pdfDocument);
    }


    public InCanvas showTextAligned(String text, float x, float y, TextAlignment textAlign, int angle) {
        super.showTextAligned(text, x, y, textAlign, (float) ((angle % 360) / 180f * Math.PI));
        return this;
    }

    public InCanvas showTextAligned(String text, float x, float y, TextAlignment textAlign, VerticalAlignment vertAlign, int angle) {
        super.showTextAligned(text, x, y, textAlign, vertAlign, (float) ((angle % 360) / 180f * Math.PI));
        return this;
    }

    public InCanvas showTextAlignedKerned(String text, float x, float y, TextAlignment textAlign, VerticalAlignment vertAlign, int radAngle) {
        super.showTextAlignedKerned(text, x, y, textAlign, vertAlign, (float) ((radAngle % 360) / 180f * Math.PI));
        return this;
    }
}
