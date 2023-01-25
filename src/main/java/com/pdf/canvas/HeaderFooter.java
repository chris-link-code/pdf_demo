package com.pdf.canvas;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.util.UrlUtil;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.element.Table;
import com.itextpdf.styledxmlparser.resolver.font.BasicFontProvider;
import com.itextpdf.svg.converter.SvgConverter;
import com.itextpdf.svg.processors.impl.SvgConverterProperties;
import com.zhipeipt.pdf.ItextPdfDocument;
import com.zhipeipt.pdf.PDFException;
import com.zhipeipt.pdf.emement.Point;
import com.zhipeipt.pdf.emement.Rect;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public abstract class HeaderFooter {
    /**
     * 当前页码
     */
    private final int pageNumber;

    /**
     * 页眉页脚分割线
     */
    private CrossLine crossLine;

    private final PDFCanvas canvas;

    private Table layout;

    private final Map<String, ImageData> imageCacheMap = new HashMap<>();

    public HeaderFooter(int pageNumber, PDFCanvas canvas) {
        this.pageNumber = pageNumber;
        this.canvas = canvas;
    }

    /**
     * 使用表格布局页眉页脚
     * @param table 页眉或者页脚布局的表格
     */
    public void layout(Table table) {
        Rect rect = this.canvas.getRect();
        table.setHeight(rect.getHeight());
        table.setWidth(rect.getWidth());
        Point lb = rect.getLb();
        table.setFixedPosition(lb.x, lb.y, rect.getWidth());
        table.setFixedLayout();
        this.layout = table;
    }

    public void addText(Text text) {
        Rect rect = this.canvas.getRect();
        Point point = text.getPoint();
        Point offset = rect.getLb().offset(point.x, point.y);
        this.canvas.text(text.move(offset));
    }

    public void addText(Point point, String text) {
        this.addText(new Text(point, text));
    }

    /**
     *
     * @param image 图片字节流
     * @param area 定义图片所在区域 图片坐标左下角开始计算 宽高设置一个或者全部默认
     */
    public void addImage(byte[] image, Rect area) {
        Rect rect = this.canvas.getRect();
        ImageData imageData = ImageDataFactory.create(image);
        Point point = area.getPoint();
        Point offset = rect.getLb().offset(point.x, point.y);
        this.canvas.addImage(imageData, new Rectangle(offset.x, offset.y, area.getWidth(), area.getHeight()), true);
    }

    /**
     *
     * @param image 图片内容 链接 或者文件路径
     * @param area 定义图片所在区域 图片坐标左下角开始计算 宽高设置一个或者全部默认
     */
    public void addImage(String image, Rect area) {
        Rect rect = this.canvas.getRect();
        ImageData imageData = imageCacheMap.computeIfAbsent(image, url -> {
            try {
                return ImageDataFactory.create(UrlUtil.toURL(url));
            } catch (MalformedURLException e) {
                throw new PDFException(e.getMessage(), e);
            }
        });
        Point point = area.getPoint();
        Point offset = rect.getLb().offset(point.x, point.y);
        this.canvas.addImage(imageData, new Rectangle(offset.x, offset.y, area.getWidth(), area.getHeight()), true);
    }

    /**
     * 增加一个svg图片
     * @param svg svg字符串
     * @param area 图片所在区域
     */
    public void addSvg(String svg, Rect area) throws IOException {
        Rect rect = this.canvas.getRect();
        Point point = area.getPoint();
        Point offset = rect.getLb().offset(point.x, point.y);
        SvgConverterProperties properties = new SvgConverterProperties();
        PdfFont font = this.canvas.getDocument().getDefaultFont();
        BasicFontProvider basicFontProvider = new BasicFontProvider(false, false);
        basicFontProvider.addFont(font.getFontProgram());
        properties.setFontProvider(basicFontProvider);
        PdfFormXObject xObject = SvgConverter.convertToXObject(new ByteArrayInputStream(svg.getBytes(StandardCharsets.UTF_8)),
                canvas.getDocument(), properties);
        canvas.addXObject(xObject, offset.x, offset.y, area.getWidth());
    }

    public float getWidth() {
        return canvas.getRect().getWidth();
    }

    public float getHeight() {
        return canvas.getRect().getWidth();
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public CrossLine getCrossLine() {
        return crossLine;
    }

    public void setCrossLine(CrossLine crossLine) {
        this.crossLine = crossLine;
    }

    public Table getLayout() {
        return layout;
    }

    public ItextPdfDocument getPdfDocument() {
        return (ItextPdfDocument) this.canvas.getDocument();
    }
}
