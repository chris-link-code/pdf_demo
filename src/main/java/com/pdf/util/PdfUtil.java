package com.pdf.util;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.pdf.bean.Itext7Pdf;
import com.pdf.bean.PdfSetup;
import com.pdf.handle.PdfRenderHandle;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author chris
 * @create 2023/1/25
 */
@Slf4j
public class PdfUtil {

    public void createPdf() throws IOException {
        String output = System.getProperty("user.dir") + "\\test.pdf";
        String font = System.getProperty("user.dir") + "\\font.ttf";
        log.info("output: {}", output);
        log.info("font: {}", font);

        PdfSetup setup = new PdfSetup(output, font);
        setup.setDirectory(true);
        setup.setCover(true);
        setup.setFooter(true);
        setup.setHeader(true);
        Itext7Pdf.getPdf(new PdfRenderHandle(), setup).write();
    }


    public static void getPdf(File file) {
        String input = file.getAbsolutePath();
        String pdfName = file.getName().replaceAll(".txt", "");
        String output = System.getProperty("user.dir") + "\\" + pdfName + ".pdf";

        BufferedReader reader = null;
        Document document = null;
        PdfWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            StringBuffer sb = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String text = sb.toString();
            text = text.substring(text.indexOf("录") + 2, text.length());
            text = text.replaceAll(":", ":\r\n");
            text = text.replaceAll("、", ", ");
            text = text.replaceAll("\\.", ".\r\n");
            log.info(text);

            writer = new PdfWriter(output);
            PdfDocument pdfDocument = new PdfDocument(writer);
            document = new Document(pdfDocument);
            document.setFontSize(16);
            document.add(new Paragraph(text));

            //TODO 页眉

            //addPageHeader(document, PageSize.A4.getHeight(), pdfName);
            //addPageHeader(document, 6000, pdfName);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
            if (document != null) {
                document.close();
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 页眉
     * https://blog.csdn.net/running17/article/details/127122897
     */
    private static void addPageHeader(Document document, float pdfHeight, String header) {
        //创建字体
        //Font textFont = new Font(BASE_FONT, 10f);
        float width = PageSize.A4.getWidth() - 60;
        //表格 一行两列
        Table table = new Table(2);
        table.setWidth(width);

        //logo
        //Image logo = new Image(ImageDataFactory.create(logoPath));
        table.addCell(new Cell().setHeight(40).setBorderTop(Border.NO_BORDER).setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER));

        //名称
        Paragraph nameP = new Paragraph(header);
        table.addCell(new Cell().add(nameP).setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE).setTextAlignment(TextAlignment.CENTER).setBorderTop(Border.NO_BORDER).setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER));

        //设置表格的位置 页眉处
        table.setFixedPosition(document.getLeftMargin() - 10, pdfHeight - document.getTopMargin() - 40, table.getWidth());
        document.add(table);
    }

}
