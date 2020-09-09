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

    public static Date getThisMonthLastDay(String thisMonthFirstDay){
        try {
            //把本月第一天的日期字符串转换为日期类型
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(thisMonthFirstDay);
            //获取日历对象
            Calendar calendar = Calendar.getInstance();
            //设置日历为 本月第一天
            calendar.setTime(date);
            //设置下月第一天
            calendar.add(Calendar.MONTH, 1);
            //调整为前一天， 为本月最后一天
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            return calendar.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parseString2Date(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(dateStr);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String parseDate2String(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);
        return dateStr;
    }

    /**
     * 获取本周一
     * @return
     */
    public static Date getThisWeekMonday(){
        Calendar calendar = Calendar.getInstance();
        int index = calendar.get(Calendar.DAY_OF_WEEK);
        if (index == 1){
            calendar.add(Calendar.DAY_OF_MONTH, -6);
        }else{
            calendar.add(Calendar.DAY_OF_MONTH, -(index - 2));
        }
        return calendar.getTime();
    }

    /**
     * 获取本周日
     * @return
     */
    public static Date getThisWeekSunday(){
        Calendar calendar = Calendar.getInstance();
        int index = calendar.get(Calendar.DAY_OF_WEEK);
        if(index == 1){}
        else{
           calendar.add(Calendar.DAY_OF_MONTH, 8 - index );
        }
        return calendar.getTime();
    }

    /**
     * 获取本地第一天
     * @return
     */
    public static Date getThisMonthFirstDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH , 1);
        return calendar.getTime();
    }

    /**
     * 获取本地第一天
     * @return
     */
    public static Date getThisMonthLastDay(){
        Calendar calendar = Calendar.getInstance();
        //设置为本月1号
        calendar.set(Calendar.DAY_OF_MONTH , 1);
        //设置为下月1号
        calendar.add(Calendar.MONTH, 1);
        //前移一天
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }

    public static void main(String[] args) {
        System.out.println(getThisMonthLastDay());
    }
}