package com.pdf.bean;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.pdf.handle.ItextDocument;
import com.pdf.handle.ItextPdfDocument;
import com.pdf.handle.PdfRender;
import lombok.Data;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static com.itextpdf.kernel.events.PdfDocumentEvent.END_PAGE;
import static com.pdf.handle.Const.*;

@Data
public class Itext7Pdf {

    private PdfSetup setup;
    private PdfRender render;
    private PdfFont font;
    private Style style;
    private PageSize pageSize;

    /**
     * @param render 渲染器
     */
    private Itext7Pdf(PdfRender render, PdfSetup setup) throws IOException {
        this.render = Objects.requireNonNull(render);
        this.setup = Objects.requireNonNull(setup);
        this.style = new Style();
        this.pageSize = PageSize.A4;
        if (StrUtil.isNotBlank(this.setup.getFont())) {
            font = PdfFontFactory.createFont(FileUtil.readBytes(this.setup.getFont()), PdfEncodings.IDENTITY_H, true);
        } else {
            font = PdfFontFactory.createFont();
        }
    }

    public static Itext7Pdf getPdf(PdfRender render, PdfSetup setup) throws IOException {
        return new Itext7Pdf(render, setup);
    }

    public Itext7Pdf style(Style style) {
        this.style = style;
        return this;
    }

    public void write() throws IOException {
        PdfWriter writer = new PdfWriter(setup.getOutput());
        ItextPdfDocument pdfDocument = new ItextPdfDocument(writer);
        pdfDocument.setDefaultPageSize(this.pageSize);
        // 修改PdfDocument 的默认字体为自定义字体 不设置该字体 svg转换 canvas中的中文都会无法显示
        ReflectUtil.setFieldValue(pdfDocument, DEFAULT_FONT, this.font);
        this.font.makeIndirect(pdfDocument);
        // 需要页眉页脚才会需要注册该事件
        if (setup.hasFooterOrHeader()) {
            pdfDocument.addEventHandler(END_PAGE, new PDFEventHandler(this));
        }
        pdfDocument.addNewPage();
        ItextDocument document = new ItextDocument(pdfDocument);
        document.setFont(font);
        document.margin(this.style.getMargin());
        this.render.body(document);

        if (setup.hasDirectory()) {
            PdfPage pdfPage = pdfDocument.addNewPage(1);
            pdfPage.setXmpMetadata(DIRECTORY.getBytes(StandardCharsets.UTF_8));
            ItextDocument directoryDocument = new ItextDocument(pdfPage.getDocument());
            directoryDocument.setFont(font);
            directoryDocument.setMargins(30f, 20f, 30f, 20f);
            this.render.directory(directoryDocument);
        }
        if (setup.hasCover()) {
            PdfPage pdfPage = pdfDocument.addNewPage(1);
            pdfPage.setXmpMetadata(COVER.getBytes(StandardCharsets.UTF_8));
            ItextDocument coverDocument = new ItextDocument(pdfPage.getDocument());
            coverDocument.setMargins(0f, 0f, 0f, 0f);
            coverDocument.setFont(font);
            this.render.cover(coverDocument);
        }
        document.close();
    }

    /**
     * 获取内容区域
     */
    public Rect getBodyRect() {
        Margin margin = this.getStyle().getMargin();
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
        Margin margin = this.getStyle().getMargin();
        PageSize pageSize = this.getPageSize();
        return new Rect(0,
                pageSize.getHeight(),
                pageSize.getWidth(),
                margin.getTop()
        );
    }

    /**
     * 页脚区域
     */
    public Rect getFooterRect() {
        Margin margin = this.getStyle().getMargin();
        PageSize pageSize = this.getPageSize();
        return new Rect(0, margin.getBottom(), pageSize.getWidth(), margin.getBottom());
    }

    public float getWidth() {
        return this.getPageWidth() - this.getStyle().getMargin().getLeft() - this.getStyle().getMargin().getRight();
    }

    public float getPageWidth() {
        return this.getPageSize().getWidth();
    }


    public float getHeight() {
        return this.getPageHeight() - this.getStyle().getMargin().getBottom() - this.getStyle().getMargin().getTop();
    }

    public float getPageHeight() {
        return this.getPageSize().getHeight();
    }
}
