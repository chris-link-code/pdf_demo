package com.pdf.handle;

import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.pdf.PdfPage;
import com.pdf.bean.Itext7Pdf;
import com.pdf.bean.PdfSetup;
import com.pdf.bean.Rect;
import com.pdf.canvas.*;
import lombok.Data;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class PDFEventHandler implements IEventHandler {

    private Itext7Pdf pdf;
    private AtomicInteger pageCounter = new AtomicInteger(0);

    public PDFEventHandler(Itext7Pdf render) {
        this.pdf = render;
    }

    @Override
    public void handleEvent(Event event) {
        PdfDocumentEvent evt = (PdfDocumentEvent) event;
        ItextPdfDocument pdfDocument = (ItextPdfDocument)evt.getDocument();

        PdfPage page = evt.getPage();
        // 根据元数据判断是否是主题内容
        if (Objects.nonNull(page.getXmpMetadata())){
            String type = new String(page.getXmpMetadata().getBytes());
            pdfDocument.dispatchEvent(new PdfDocumentEvent(type, pdfDocument));
            return;
        }
        if (Objects.nonNull(evt.getPage().getTabOrder())) return;
        int pageNumber = pageCounter.incrementAndGet();
        PdfRender render = this.pdf.getRender();
        PdfSetup setup = this.pdf.getSetup();
        ItextDocument itextDocument = new ItextDocument(pdfDocument);
        itextDocument.setFont(this.pdf.getFont());
        if (setup.hasHeader()) {
            // 页眉
            Rect headerRect = this.pdf.getHeaderRect();
            PDFCanvas headerCanvas = new PDFCanvas(page, headerRect);
            Header header = new Header(pageNumber, headerCanvas);
            render.header(header);

            // 分割线
            CrossLine crossLine = header.getCrossLine();
            if (Objects.nonNull(crossLine)) {
                headerCanvas.line(Line.build(headerRect.getLb(), headerRect.getRb(), crossLine.getWidth(), crossLine.getDash(), crossLine.getColor()));
            }
            if (Objects.nonNull(header.getLayout())) {
                itextDocument.add(header.getLayout());
            }
        }

        if (setup.hasFooter()) {
            Rect footerRect = this.pdf.getFooterRect();
            PDFCanvas footerCanvas = new PDFCanvas(page, footerRect);
            Footer footer = new Footer(pageNumber, footerCanvas);
            render.footer(footer);
            // 分割线
            CrossLine crossLine = footer.getCrossLine();
            if (Objects.nonNull(crossLine)) {
                footerCanvas.line(Line.build(footerRect.getLb(), footerRect.getRb(), crossLine.getWidth(), crossLine.getDash(), crossLine.getColor()));
            }
            if (Objects.nonNull(footer.getLayout())) {
                itextDocument.add(footer.getLayout());
            }
        }

    }
}
