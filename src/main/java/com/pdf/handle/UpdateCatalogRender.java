package com.pdf.handle;

import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.renderer.ParagraphRenderer;

public class UpdateCatalogRender extends ParagraphRenderer {

    private final Catalog catalog;
    /**
     * Creates a ParagraphRenderer from its corresponding layout object.
     *
     * @param modelElement the {@link Paragraph} which this object should manage
     */
    public UpdateCatalogRender(Paragraph modelElement, Catalog catalog) {
        super(modelElement);
        this.catalog = catalog;
    }

    @Override
    public LayoutResult layout(LayoutContext layoutContext) {
        LayoutResult result = super.layout(layoutContext);
        catalog.setPageNumber(layoutContext.getArea().getPageNumber());
        return result;
    }
}
