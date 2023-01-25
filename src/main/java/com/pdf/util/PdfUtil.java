package com.pdf.util;

import com.pdf.bean.Itext7Pdf;
import com.pdf.bean.PdfSetup;
import com.pdf.handle.PdfRenderHandle;

import java.io.IOException;

/**
 * @author chris
 * @create 2023/1/25
 */
public class PdfUtil {
    private static final String output = System.getProperty("user.dir") + "/test.pdf";
    private static final String font = System.getProperty("user.dir") + "/font.ttf";

    public void createPdf() throws IOException {
        PdfSetup setup = new PdfSetup(output, font);
        setup.setDirectory(true);
        setup.setCover(true);
        setup.setFooter(true);
        setup.setHeader(true);
        Itext7Pdf.getPdf(new PdfRenderHandle(), setup).write();
    }
}
