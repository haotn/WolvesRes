package com.wolvesres.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class XDate {

    static SimpleDateFormat formater = new SimpleDateFormat();

    public static Date toDate(String date, String pattern) {
        try {
            formater.applyPattern(pattern);
            return formater.parse(date);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String toString(Date date, String pattern) {
        formater.applyPattern(pattern);
        return formater.format(date);
    }

    public static Date addDays(Date date, long days) {
        date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
        return date;
    }

//    // Phương thức chuyển ngày yyyy-MM-dd
//    private String toYMD(String ngay) {
//        return XDate.toString(XDate.toDate(ngay, "dd-MM-yyyy"), "yyyy-MM-dd");
//    }

}
