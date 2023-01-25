package com.pdf.handle;

import com.pdf.canvas.Footer;
import com.pdf.canvas.Header;

public interface PdfRender {

    /**
     * 封面渲染
     *
     * @param document pdf文档对象
     */
    default void cover(ItextDocument document) {
    }

    /**
     * 目录大纲渲染
     *
     * @param document pdf文档对象
     */
    default void directory(ItextDocument document) {
    }

    /**
     * pdf页眉
     *
     * @param header 页眉对象
     */
    default void header(Header header) {
    }

    /**
     * pdf 内容
     *
     * @param document pdf文档对象
     */
    void body(ItextDocument document);

    /**
     * pdf页脚
     *
     * @param footer 页脚对象
     */
    default void footer(Footer footer) {

    }

}
