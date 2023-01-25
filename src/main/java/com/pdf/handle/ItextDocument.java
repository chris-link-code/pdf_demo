package com.pdf.handle;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.properties.AreaBreakType;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.pdf.bean.Margin;
import com.pdf.bean.Rect;
import lombok.Getter;

import java.util.Objects;

public class ItextDocument extends Document {

    @Getter
    private Margin margin;

    public ItextDocument(PdfDocument pdfDoc) {
        super(pdfDoc);
        this.margin(this.margin = new Margin());
    }

    public ItextPdfDocument getItextPdfDocument() {
        return (ItextPdfDocument) this.getPdfDocument();
    }

    public int getPageNumber() {
        return this.getPdfDocument().getNumberOfPages();
    }

    /**
     * 下一个区域
     */
    public void nextArea() {
        this.add(new AreaBreak(AreaBreakType.NEXT_AREA));
    }

    /**
     * 下一页
     */
    public void nextPage() {
        this.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
    }

    /**
     * 最后一页
     */
    public void lastPage() {
        this.add(new AreaBreak(AreaBreakType.LAST_PAGE));
    }

    /**
     * @param margin 边距 语法与css margin 一致
     */
    public void margin(String margin) {
        this.margin(Objects.requireNonNull(Margin.parse(margin)));
    }

    public void margin(Margin margin) {
        this.margin = margin;
        this.setMargins(margin.getTop(), margin.getRight(), margin.getBottom(), margin.getLeft());
    }

    public PageSize getPageSize() {
        return this.getPdfDocument().getDefaultPageSize();
    }

    public float getWidth() {
        return this.getPageWidth() - margin.getLeft() - margin.getRight();
    }

    public float getPageWidth() {
        return this.getPageSize().getWidth();
    }


    public float getHeight() {
        return this.getPageHeight() - margin.getBottom() - margin.getTop();
    }

    public float getPageHeight() {
        return this.getPageSize().getHeight();
    }

    /**
     * 获取内容区域
     */
    public Rect getBodyRect() {
        PageSize pageSize = this.getPageSize();
        return new Rect(margin.getLeft(),
                pageSize.getHeight() - margin.getTop(),
                pageSize.getWidth() - margin.getRight() - margin.getLeft(),
                pageSize.getHeight() - margin.getTop() - margin.getBottom()
        );
    }

    /**
     * 页眉区域
     */
    public Rect getHeaderRect() {
        PageSize pageSize = this.getPageSize();
        return new Rect(0,
                pageSize.getHeight(),
                pageSize.getWidth(),
                margin.getBottom()
        );
    }

    /**
     * 页脚区域
     */
    public Rect getFooterRect() {
        PageSize pageSize = this.getPageSize();
        return new Rect(0, margin.getBottom(), pageSize.getWidth(), margin.getBottom()
        );
    }

    public ItextDocument showTextAligned(String text, float x, float y, TextAlignment textAlign, int angle) {
        super.showTextAligned(text, x, y, textAlign, (float) ((angle % 360) / 180f * Math.PI));
        return this;
    }

    public ItextDocument showTextAligned(String text, float x, float y, TextAlignment textAlign, VerticalAlignment vertAlign, int angle) {
        super.showTextAligned(text, x, y, textAlign, vertAlign, (float) ((angle % 360) / 180f * Math.PI));
        return this;
    }

    public ItextDocument showTextAlignedKerned(String text, float x, float y, TextAlignment textAlign, VerticalAlignment vertAlign, int radAngle) {
        super.showTextAlignedKerned(text, x, y, textAlign, vertAlign, (float) ((radAngle % 360) / 180f * Math.PI));
        return this;
    }
}
