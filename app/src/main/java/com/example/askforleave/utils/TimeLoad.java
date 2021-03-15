package com.example.askforleave.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by AK on 2021/3/12 19:33.
 * God bless my code!
 */
public class TimeLoad {
    public static String getNowTimeText() {
        long timeMillis = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("当前时间:yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(timeMillis);
    }
}
