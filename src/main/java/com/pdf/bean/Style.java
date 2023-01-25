package com.pdf.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Style {
    private Margin margin = new Margin();

    public Style margin(String margin) {
        this.margin = Margin.parse(margin);
        return this;
    }

    public Style margin(Margin margin) {
        this.margin = margin;
        return this;
    }
}
