package com.pdf.handle;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfOutline;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.pdf.navigation.PdfDestination;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.pdf.bean.Point;
import com.pdf.bean.Rect;
import com.pdf.canvas.Color;
import com.pdf.canvas.Footer;
import com.pdf.canvas.Header;
import com.pdf.canvas.PDFCanvas;
import com.pdf.heading.H1;
import com.pdf.heading.H2;
import lombok.SneakyThrows;

public class PdfRenderHandle implements PdfRender {

    @Override
    public void cover(ItextDocument document) {
        Div div = new Div();
        div.setDestination("cover");
        div.add(new Paragraph("封面").setFontColor(Color.parse("#FFFFFF")));
        div.setWidth(document.getPageWidth());
        div.setHeight(document.getPageHeight());
        //PdfImageXObject imageXObject = new PdfImageXObject(ImageDataFactory.create(new URL("https://zhiper-cdn.oss-cn-shanghai.aliyuncs.com/2051961.jpg")));
        //BackgroundImage image = new BackgroundImage(imageXObject);
        //div.setProperty(Property.BACKGROUND_IMAGE, image);

        Table table = new Table(4);
        table.setBorder(Border.NO_BORDER);
        table.addHeaderCell("标题一");
        table.addHeaderCell("标题二");
        table.addHeaderCell("标题三");
        table.addHeaderCell("标题四");
        div.add(table);

        document.add(div);

        PdfOutline outlines = document.getPdfDocument().getOutlines(true);
        outlines.addOutline("封面", 0).addDestination(PdfDestination.makeDestination(new PdfString("cover")));
    }

    @Override
    public void directory(ItextDocument document) {
        Div div = new Div();
        div.add(new Paragraph("目录").setDestination("directory").setHorizontalAlignment(HorizontalAlignment.CENTER));
        ItextPdfDocument itextPdfDocument = document.getItextPdfDocument();
        itextPdfDocument.eachCatalog(catalog -> {
            Link link = new Link(catalog.getTitle(), PdfAction.createGoTo(catalog.getCode()));
            Paragraph paragraph = new Paragraph(link);
            paragraph.add("......................")
                    .add(catalog.getPageNumber() + "")
                    .setFirstLineIndent(10 * (1 - catalog.getLevel()));
            div.add(paragraph);
        });

        PdfOutline outlines = document.getPdfDocument().getOutlines(true);
        outlines.addOutline("目录", 0)
                .addDestination(PdfDestination.makeDestination(new PdfString("directory")));
        document.add(div);
    }

    @Override
    public void header(Header header) {
        try {
            Table table = new Table(1);
            table.setBorder(Border.NO_BORDER);
            Cell cell = new Cell();
            cell.setTextAlignment(TextAlignment.CENTER);
            Paragraph paragraph = new Paragraph("页眉" + header.getPageNumber());
            cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
            paragraph.setBackgroundColor(Color.parse("#d0d0d0"));
            cell.add(paragraph);
            table.addCell(cell);
            header.layout(table);
        } catch (Exception e) {
            e.printStackTrace();
        }
//      header.addText(new Text(Point.build(0, 0), "页眉" + header.getPageNumber()).centered());
    }

    @Override
    public void footer(Footer footer) {
//        footer.addText(new Text(Point.build(0, 0), "第" + footer.getPageNumber() + "页")
//                .style(new FontStyle(10)).align(HorizontalAlign.CENTER, VerticalAlign.MIDDLE));
        Table table = new Table(3);
        table.setBorderTop(new SolidBorder(Color.parse("#EEEEEE"), 2f));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("左边")).setTextAlignment(TextAlignment.LEFT));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("第" + footer.getPageNumber() + "页")).setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("右边")).setTextAlignment(TextAlignment.RIGHT));
        footer.layout(table);
    }

    @SneakyThrows
    @Override
    public void body(ItextDocument document) {

        ItextPdfDocument pdfDocument = document.getItextPdfDocument();

        for (int i = 0; i < 10; i++) {
            String title = "第" + i + "部分内容";
            // 添加目录
            H1 h1 = new H1(title);
            Catalog catalog = pdfDocument.addCatalog(h1, title);
            // 添加书签
            pdfDocument.addBookmark(h1, catalog.getCode());
            document.add(new Div().add(h1));
            Div div = new Div();
            Table table = new Table(6);
            table.setBorder(Border.NO_BORDER);
            table.addHeaderCell("标题一");
            table.addHeaderCell("标题二");
            table.addHeaderCell("标题三");
            table.addHeaderCell("标题四");
            table.addHeaderCell("标题五");
            table.addHeaderCell("标题六");
            for (int j = 0; j < 360; j++) {
                Cell cell = new Cell();
                cell.setBorder(Border.NO_BORDER);
                cell.add(new Paragraph("表格"));
                table.addCell(cell);
            }
            table.setWidth(document.getWidth());
            table.setHorizontalAlignment(HorizontalAlignment.CENTER);
            div.add(table);

            String secondTitle = title + "二级书签";
            H2 h2 = new H2(secondTitle);
            Catalog secCatalog = pdfDocument.addCatalog(h2);
            div.add(h2);
            pdfDocument.addBookmark(h2, secCatalog.getCode());

            Rect bodyRect = document.getBodyRect();

            // canvas绘图
            Rectangle boundingBox = new Rectangle(0, 0, bodyRect.getWidth(), 200);
            PdfFormXObject xObject = new PdfFormXObject(boundingBox);
            xObject.makeIndirect(pdfDocument);//Make sure the XObject gets added to the document
            PDFCanvas pCanvas = new PDFCanvas(xObject, pdfDocument);//Create a canvas from the XObject
            pCanvas.text(Point.build(60, 60), "吃个桃桃", "#d3d3d3", 12);
            pCanvas.rectangle(Point.build(20, 20), 30, 30).setFillColor(Color.BLUE).fill();

//            Canvas canvas = new Canvas(xObject, pdfDocument);//Create a canvas from the XObject
//            canvas.setRenderer(new CanvasRenderer(canvas){
//                @Override
//                public void addChild(IRenderer renderer) {
//                    super.addChild(renderer);
//                }
//            });
            pCanvas.draw(canvas -> {
                canvas.setFontColor(Color.RED);
                canvas.showTextAligned("吃个桃桃好凉凉", 200, 100, TextAlignment.LEFT, 90);
            });
            pCanvas.text(Point.build(60, 80), "一个小桃子", "#d3d3d3", 12);
            Image rect = new Image(xObject);
//            rect.setAutoScale(true);
//            rect.setBackgroundColor(Color.parse("#000000"));
            div.add(rect);

//            div.add(rect);
            document.add(div);
            /**
             * 将文本放置在指定位置并旋转角度
             */
            document.showTextAligned("吃个桃桃好凉凉0", 90, 90, TextAlignment.LEFT);
            document.showTextAligned("吃个桃桃好凉凉30", 90, 90, TextAlignment.LEFT, 30);
            document.showTextAligned("吃个桃桃好凉凉60", 90, 90, TextAlignment.LEFT, 60);
            document.showTextAligned("吃个桃桃好凉凉90", 90, 90, TextAlignment.LEFT, 90);
            document.showTextAlignedKerned("吃个桃桃好凉凉120", 90, 90, TextAlignment.LEFT, VerticalAlignment.MIDDLE, 120);
//            document.add(SvgUtil.convert(svg, pdfDocument).setHorizontalAlignment(HorizontalAlignment.CENTER));

//            PdfPage page = pdfDocument.getPage(pdfDocument.getNumberOfPages());
//            Canvas canvas = new Canvas(page);
//            canvas.circle(Point.build(200, 400), 100).setFillColor(Color.parse("#EEEEEE")).fill();
//            canvas.beginText().setFontAndSize(pdfDocument.getDefaultFont(), 12).setTextRise(120).showText("中文").endText();

            if (i < 9) document.nextPage();
        }


    }
}
