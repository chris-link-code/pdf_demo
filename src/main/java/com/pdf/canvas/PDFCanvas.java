package com.pdf.canvas;

import cn.hutool.core.util.StrUtil;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.pdf.bean.Align;
import com.pdf.bean.Point;
import com.pdf.bean.Range;
import com.pdf.bean.Rect;

import java.util.Objects;
import java.util.function.Consumer;

public class PDFCanvas extends PdfCanvas {

    private Rect rect;
    private PdfFormXObject formXObject;

    public PDFCanvas(PdfPage page) {
        super(page);
        Rectangle size = page.getPageSize();
        this.rect = new Rect(Point.build(size.getX(), size.getY()), size.getWidth(), size.getHeight());
        this.formXObject = new PdfFormXObject(size);
    }

    public PDFCanvas(PdfFormXObject xObj, PdfDocument document) {
        super(xObj, document);
        this.formXObject = xObj;
        this.rect = new Rect(0, 0, xObj.getWidth(), xObj.getHeight());
    }

    public PDFCanvas(Range range, PdfDocument document) {
        this(new PdfFormXObject(new Rectangle(0, 0, range.getWidth(), range.getHeight())), document);
    }

    public PDFCanvas(PdfPage page, Rect rect) {
        super(page);
        this.rect = rect;
    }

    public PDFCanvas(PdfDocument document, Rect rect) {
        this(new PdfFormXObject(new Rectangle(rect.getLb().x, rect.getLb().y, rect.getWidth(), rect.getHeight())), document);
        this.rect = rect;
    }

    public PDFCanvas text(Text text) {
        PdfCanvas pdfCanvas = this.beginText();
        if (Objects.nonNull(text.getPoint())) {
            Point point = text.getPoint();
            int size = text.getStyle().getSize();
            int len = text.getText().length() * size;
            Align align = text.getAlign();
            Rect rect = Align.get(align, this.rect, new Rect(point.offsetY(size), len, size));
            point = rect.getLb();
            pdfCanvas.moveText(point.x, point.y);
        }
        if (Objects.nonNull(text.getStyle())) {
            FontStyle style = text.getStyle();
            pdfCanvas.setFontAndSize(this.getDocument().getDefaultFont(), style.getSize());
            if (Objects.nonNull(style.getColor())) {
                pdfCanvas.setFillColor(style.getColor());
            }
        }
        pdfCanvas.showText(text.getText())
                .endText();
        return this;
    }

    public PDFCanvas text(Point point, String text) {
        this.text(new Text(point, text));
        return this;
    }

    public PDFCanvas text(Point point, String text, String color) {
        this.text(new Text(point, text, color));
        return this;
    }

    public PDFCanvas text(Point point, String text, String color, int size) {
        this.text(new Text(point, text, size, color));
        return this;
    }

    public PDFCanvas moveTo(Point point) {
        super.moveTo(point.x, point.y);
        return this;
    }

    public PDFCanvas lineTo(Point point) {
        super.lineTo(point.x, point.y);
        return this;
    }

    public PDFCanvas line(Line line) {
        this.moveTo(line.getStart());
        if (line.getDash() > 0) {
            this.setLineDash(line.getDash(), line.getDash());
        }
        if (line.getWidth() > 0) {
            this.setLineWidth(line.getWidth());
        }
        this.lineTo(line.getEnd());
        if (StrUtil.isNotBlank(line.getColor())) {
            this.setStrokeColor(Color.parse(line.getColor())).stroke();
        }
        return this;
    }

    public PDFCanvas rectangle(Point point, double width, double height) {
        super.rectangle(point.x, point.y, width, height);
        return this;
    }

    public PDFCanvas circle(Point point, double r) {
        super.circle(point.x, point.y, r);
        return this;
    }

    public Rect getRect() {
        return rect;
    }

    public PDFCanvas draw(Consumer<InCanvas> consumer) {
        InCanvas canvas = new InCanvas(Objects.requireNonNull(this.formXObject), this.getDocument());
        canvas.setFont(this.getDocument().getDefaultFont());
        consumer.accept(canvas);
        canvas.close();
        return this;
    }
}
