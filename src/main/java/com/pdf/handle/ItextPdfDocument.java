package com.pdf.handle;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.navigation.PdfDestination;
import com.itextpdf.layout.element.Paragraph;
import com.pdf.heading.H;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ItextPdfDocument extends PdfDocument {

    @Getter
    private final List<Catalog> catalogs = new ArrayList<>();

    public ItextPdfDocument(PdfReader reader, DocumentProperties properties) {
        super(reader, properties);
    }


    public ItextPdfDocument(PdfWriter writer) {
        super(writer);
    }

    public ItextPdfDocument(PdfReader reader, PdfWriter writer) {
        super(reader, writer);
    }

    public ItextPdfDocument(PdfReader reader, PdfWriter writer, StampingProperties properties) {
        super(reader, writer, properties);
    }

    /**
     *
     * @param destination 标题所在的元素
     * @param title 标题内容
     * @return
     */
    public Catalog addCatalog(Paragraph destination, String title) {

        return this.addCatalog(destination, title, 1);
    }

    public Catalog addCatalog(H h) {
        return this.addCatalog(h, h.getText(), h.getLevel());
    }

    /**
     *
     * @param destination 标题所在的元素
     * @param title 标题内容
     * @param level 标题级别 1 2 3 4 5
     */
    public Catalog addCatalog(Paragraph destination, String title, int level) {
        Catalog catalog = new Catalog(title, level);
        destination.setDestination(catalog.getCode()).setNextRenderer(new UpdateCatalogRender(destination, catalog));
        catalog.setPageNumber(this.getNumberOfPages());

        if (level == 1 || this.catalogs.isEmpty()) {
            this.catalogs.add(catalog);
        } else {
            catalog.setLevel(level);
            this.catalogs.get(this.catalogs.size() - 1).add(catalog);
        }

        return catalog;
    }

    /**
     * 增加书签
     * @param bookmark 书签内容
     * @param dest 书签关联的元素id
     */
    public void addBookmark(String bookmark, String dest) {
        this.addBookmark(bookmark, dest, 1);
    }

    public void addBookmark(H h, String dest) {
        this.addBookmark(h.getText(), dest, h.getLevel());
    }

    /**
     * 增加书签
     * @param bookmark 书签内容
     * @param dest 书签关联的元素id
     * @param outlineLevel 书签的层级 1 2 3 4 5
     */
    public void addBookmark(String bookmark, String dest, int outlineLevel) {
        PdfOutline outlines = this.getOutlines(true);
        if (outlineLevel == 1) {
            outlines.addOutline(bookmark).addDestination(PdfDestination.makeDestination(new PdfString(dest)));
        } else {
            PdfOutline outline = outlines;
            List<PdfOutline> pdfOutlines;
            int count = outlineLevel;
            while (count-- > 0) {
                pdfOutlines = outline.getAllChildren();
                if (pdfOutlines.isEmpty()) {
                    outline.addOutline(bookmark).addDestination(PdfDestination.makeDestination(new PdfString(dest)));
                    break;
                }
                outline = pdfOutlines.get(pdfOutlines.size() - 1);
            }
        }
    }

    public void eachCatalog(Consumer<Catalog> consumer) {
        this.catalogs.forEach(catalog -> {
            catalog.forEach(consumer);
        });
    }
}
