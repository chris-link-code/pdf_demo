package com.pdf.bean;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Arrays;

@Data
@Builder
@AllArgsConstructor
public class Margin {

    private float top = 40;
    private float right = 30;
    private float bottom = 30;
    private float left = 30;

    public Margin() {
    }

    /**
     * @param margin css margin
     */
    public static Margin parse(String margin) {
        if (StrUtil.isBlank(margin)) return new Margin();
        if (!margin.matches("^([0-9]+\\s?){1,4}$")) {
            throw new RuntimeException("Margin syntax error");
        }
        String[] split = margin.trim().split("\\s");
        Float[] items = Arrays.stream(split).filter(StrUtil::isNotBlank)
                .map(Float::parseFloat).toArray(Float[]::new);
        float top = items[0], right = items[0], bottom = items[0], left = items[0];
        if (items.length > 1) {
            right = left = items[1];
            if (items.length > 2) {
                bottom = items[2];
                if (items.length > 3) {
                    left = items[3];
                }
            }
        }
        return new Margin(top, right, bottom, left);
    }
}
