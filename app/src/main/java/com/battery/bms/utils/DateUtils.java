package com.battery.bms.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liujia.json on 2019/1/1.
 */

public class DateUtils {
    public static String getDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
}
