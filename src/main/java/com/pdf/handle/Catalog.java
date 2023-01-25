package com.pdf.handle;

import cn.hutool.core.lang.UUID;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Data
public class Catalog {
    /**
     * 标题内容
     */
    private String title;
    /**
     * 标题的级别 1-5
     */
    private int level = 1;
    /**
     * 标题唯一编码 用于定位跳转 必须全局唯一
     */
    private String code;

    /**
     * 所在页码
     */
    private int pageNumber;
    /**
     * 子标题
     */
    private List<Catalog> catalogs = new ArrayList<>();

    public Catalog(String title) {
        this.title = title;
        this.code = UUID.fastUUID().toString();
    }


    public Catalog(String title, int level) {
        this(title);
        this.level = level;
    }

    public Catalog(String title, int level, String code) {
        this(title, level);
        this.code = code;
    }

    public void add(Catalog catalog) {
        int level = catalog.getLevel();
        if (level == this.level - 1 || catalogs.isEmpty()) {
            catalog.setLevel(this.level - 1);
            catalogs.add(catalog);
        } else {
            catalogs.get(catalogs.size() - 1).add(catalog);
        }
    }

    public void forEach(Consumer<Catalog> consumer) {
        consumer.accept(this);
        this.catalogs.forEach(catalog -> catalog.forEach(consumer));
    }
}