package com.wolvesres.helper;

import java.text.DecimalFormat;

public class XFormatMoney {

    private static final DecimalFormat FORMATER = new DecimalFormat("###,###,###,### VNĐ");

    public static String formatMoney(float number) {
        return FORMATER.format(number);
    }

}
