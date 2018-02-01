package com.yijian.person.util;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * desc:
 *
 * @author:nickming date:2016/2/4
 * time: 01:46
 * e-mail：962570483@qq.com
 */

public class TimeUtil {
    protected static final String TAG = TimeUtil.class.getSimpleName();
    /**
     * 将yyyy-MM-dd HH:mm:ss转换为yyyy-MM-dd
     *
     * @param time
     * @return
     */
    public static String convertTime(String time) {
        String result = "";
        DateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dft.parse(time);
            result = dft.format(date);
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int getMinutes(String start)
    {
        String currentTime=getCurrentExictTimeStr();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date date1=format.parse(start);
            Date date2=format.parse(currentTime);
            return (int) ((date2.getTime()-date1.getTime())/60000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getMinutes(String start,String end)
    {
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date date1=format.parse(start);
            Date date2=format.parse(end);
            return (int) ((date2.getTime()-date1.getTime())/60000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getCurrentTimeStr() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date()).toString();
    }

    public static String getCurrentDay() {
        SimpleDateFormat format = new SimpleDateFormat("dd");
        return format.format(new Date()).toString();
    }

    public static String getCurrentExictTimeStr() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date()).toString();
    }

    public static String getCurrentExictTimeStrExpectSec() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(new Date()).toString();
    }
    /**
     * 比较选择时间是否大于当前时间，大于返回true，小于返回false
     *
     * @param str1
     * @return
     */
    public static boolean compareWithCurrentDate(String str1) {
        DateFormat dft1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = dft1.parse(str1);
            Date date2 = dft1.parse(getCurrentTimeStr());
            int result = date1.compareTo(date2);
            if (result == 1)
                return true;
            else
                return false;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 比较选择时间是否大于当前时间，大于返回true，小于返回false
     *
     * @param str1
     * @return
     */
    public static boolean compareWithCurrentTime(String str1) {
        DateFormat dft1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date1 = dft1.parse(str1);
            Date date2 = dft1.parse(getCurrentExictTimeStrExpectSec());
            int result = date1.compareTo(date2);

            //Logger.e("以前的时间", str1 + "    " + getCurrentExictTimeStrExpectSec() + "    boolean: " + result);
            if (result == 1 || result == 0)
                return true;
            else
                return false;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 比较选择时间是否大于当前时间，大于等于返回true，小于返回false
     *
     * @param str1
     * @return
     */
    public static boolean comparePatrolCurrentTime(String str1) {
        DateFormat dft1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            if (str1==null){
                return false;
            }
            Date date1 = dft1.parse(str1);
            Date date2 = dft1.parse(getCurrentExictTimeStr());
            int result = date1.compareTo(date2);
            if (result == 1||result==0)
                return true;
            else
                return false;

        } catch (Exception e) {
            Logger.i(TAG,e.toString());
        }
        return false;
    }

    public static List<String> convertPatrolDate(String start, String end) {
        List<String> result = new ArrayList<>();
        DateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat dft3 = new SimpleDateFormat("HH:mm:ss");
        DateFormat dft1 = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date1 = dft1.parse(start);
            Date date2 = dft1.parse(end);
            if (date1.getTime() == date2.getTime()) {
                date2 = dft.parse(end);
                result.add(start);
                result.add(dft3.format(date2));
                return result;
            } else {
                result.add(dft1.format(date1));
                result.add(dft1.format(date2));
                return result;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<String> convertPatrolToHours(String start, String end) {
        List<String> result = new ArrayList<>();
        DateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat dft3 = new SimpleDateFormat("HH:mm");

        try {
            Date date1 = dft.parse(start);
            Date date2 = dft.parse(end);
            result.add(dft3.format(date1));
            result.add(dft3.format(date2));
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String convertPatrolToHourAndMin(String start) {
        DateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat dft3 = new SimpleDateFormat("HH:mm");

        try {
            Date date1 = dft.parse(start);
            return dft3.format(date1).toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * str1与str2的大小，str1小于等于str2返回true，否则返回false
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean compareTwoDate(String str1, String str2) {
        DateFormat dft1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = dft1.parse(str1);
            Date date2 = dft1.parse(str2);
            int result = date1.compareTo(date2);
            if (result == 0 || result < 0)
                return true;
            else
                return false;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }



    /**
     * 给出指定的年份和年数，得到相加的年份
     *
     * @param str, year
     * @return String
     */
    public static String addYear(String str, int year) {
        if(TextUtils.isEmpty(str)){
            return year + "年";
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date dt= null;
        try {
            dt = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.YEAR,year);//日期减1年
        Date dt1=rightNow.getTime();
        String reStr = sdf.format(dt1);

        return reStr;
    }

    public static String getCurrentMonday() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.add(Calendar.DAY_OF_MONTH, -1); //解决周日会出现 并到下一周的情况
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        return format.format(cal.getTime());
    }

    public static String getCurrentMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String first = format.format(c.getTime());
        return first;
    }
    public static String getYesterday(){
        long currentTime=System.currentTimeMillis();
        long yesterday=currentTime-1000*60*60*24;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date(yesterday));
    }






}
