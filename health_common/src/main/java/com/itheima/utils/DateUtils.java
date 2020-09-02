package com.itheima.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
public class DateUtils {

    public static Date parseStr2Date(String dateStr){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(dateStr);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String parseDate2Str(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);
        return dateStr;
    }

    public static Date getThisMonthLastDayByFirstDay(String thisMonthFirstDay){
        //先把字符串转换为日期
        Date date = parseStr2Date(thisMonthFirstDay);
        //把date转换为日历
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //设置为下月1号
        calendar.add(Calendar.MONTH,1);
        //往前设置一天
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        //返回日期对象
        return calendar.getTime();
    }
}
