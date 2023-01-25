
package com.pdf.canvas;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.exceptions.PdfException;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Color extends DeviceRgb {

    private static final Map<String, Color> cacheMap = new HashMap<>();
    private static final Pattern HEX_PATTERN = Pattern.compile("([0-9a-fA-F]{2})([0-9a-fA-F]{2})([0-9a-fA-F]{2})");
    private static final Pattern RGB_PATTERN = Pattern.compile("rgb[(](\\d{1,3}),(\\d{1,3}),(\\d{1,3})[)]", Pattern.CASE_INSENSITIVE);
    private static final Pattern RGBA_PATTERN = Pattern.compile("rgba[(](\\d{1,3}),(\\d{1,3}),(\\d{1,3}),([0-9.]+)[)]", Pattern.CASE_INSENSITIVE);

    private final int red;
    private final int green;
    private final int blue;


    public Color(int red, int green, int blue) {
        super(red, green, blue);
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     *  rgb(121,121,212) #fffaaa
     * @param colorStr
     * @return
     */
    public static Color parse(String colorStr) {
        if (cacheMap.containsKey(colorStr)) {
            return cacheMap.get(colorStr);
        }
        String str = colorStr.replaceAll("[#\\s]", "");
        Matcher matcher = HEX_PATTERN.matcher(str);
        boolean matched = matcher.find();
        int radix = 10;
        if (!matched) {
            matcher = RGB_PATTERN.matcher(str);
            matched = matcher.find();
            if (!matched) {
                matcher = RGBA_PATTERN.matcher(str);
                matched = matcher.find();
            }
        } else {
            radix = 16;
        }
        if (matched) {
            int r = Integer.parseInt(matcher.group(1), radix);
            int g = Integer.parseInt(matcher.group(2), radix);
            int b = Integer.parseInt(matcher.group(3), radix);
            Color color = new Color(r, g, b);
            cacheMap.put(colorStr, color);
            return color;
        }
        throw new PdfException("invalid color string");
    }

    @Override
    public String toString() {
        return "Color{" +
                "red=" + red +
                ", green=" + green +
                ", blue=" + blue +
                '}';
    }

}
