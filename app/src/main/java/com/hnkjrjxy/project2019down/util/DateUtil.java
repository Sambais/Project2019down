package com.hnkjrjxy.project2019down.util;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtil {
    public static String getDate(String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return (String) DateUtils.getRelativeTimeSpanString(sdf.parse(time).getTime());
    }
}
