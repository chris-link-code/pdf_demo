package com.pdf.bean;

/**
 * @author chris
 * @create 2023/1/25
 */
public class PdfSetup {
    /**
     * 输出文件
     */
    private String output;
    /**
     * 字体路径
     */
    private String font;

    /**
     * 是否需要封面
     */
    private boolean cover;
    /**
     * 是否需要大纲
     */
    private boolean directory;
    /**
     * 是否需要页脚
     */
    private boolean footer;
    /**
     * 是否需要页眉
     */
    private boolean header;

    public PdfSetup(String output, String font) {
        this.output = output;
        this.font = font;
    }

    public PdfSetup(String output, String font, boolean cover, boolean directory, boolean footer, boolean header) {
        this.output = output;
        this.font = font;
        this.cover = cover;
        this.directory = directory;
        this.footer = footer;
        this.header = header;
    }

    public PdfSetup(String output, String font, boolean cover, boolean directory) {
        this.output = output;
        this.font = font;
        this.cover = cover;
        this.directory = directory;
    }

    public PdfSetup(String output, String font, boolean cover, boolean footer, boolean header) {
        this.output = output;
        this.font = font;
        this.cover = cover;
        this.footer = footer;
        this.header = header;
    }

    public int getOffset() {
        int offset = 0;
        if (this.cover) offset -= 1;
        if (this.directory) offset -= 1;
        return offset;
    }


    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public boolean hasCover() {
        return cover;
    }

    public void setCover(boolean cover) {
        this.cover = cover;
    }

    public boolean hasDirectory() {
        return directory;
    }

    public void setDirectory(boolean directory) {
        this.directory = directory;
    }

    public boolean hasFooter() {
        return footer;
    }

    public void setFooter(boolean footer) {
        this.footer = footer;
    }

    public boolean hasHeader() {
        return header;
    }

    public void setHeader(boolean header) {
        this.header = header;
    }

    public boolean hasFooterOrHeader() {
        return this.footer || this.header;
    }
}
