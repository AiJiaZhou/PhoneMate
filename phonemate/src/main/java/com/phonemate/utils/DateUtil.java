package com.phonemate.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtil {

    public final static String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public final static SimpleDateFormat DEFAULT_SDF = new SimpleDateFormat(
            DEFAULT_PATTERN);

    public static String longToString(long currentTime,String type) {
        Date date=new Date(currentTime);
        return  dateToString(date, type);

    }

    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    /**
     * 把日期转换成yyyy年MM月dd日 HH:mm:ss格式
     *
     * @param date
     * @return
     */
    public static String convertDate(Date date) {
        return DEFAULT_SDF.format(date);
    }

    /**
     * 把日期转换成pattern格式
     *
     * @param date
     * @param pattern
     * @return
     */
    
    public static String convertDate(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * @param date
     * @return
     */
    public static Date convertStringToDate(String date) {
        try {
            return DEFAULT_SDF.parse(date);
        } catch (ParseException e) {
        }
        return new Date();
    }

    /**
     * @param date
     * @param pattern
     * @return
     */
    public static Date convertStringToDate(String date, String pattern) {
        try {
            return new SimpleDateFormat(pattern).parse(date);
        } catch (ParseException e) {
        }
        return new Date();
    }

    /**
     * 判断传入的日期是不是当月的第一天
     *
     * @param date
     * @return
     */
    public static boolean isFirstDayInMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH) == 1;
    }

    /**
     * 判断传入的日期是不是当年的第一天
     *
     * @param date
     * @return
     */
    public static boolean isFirstDayInYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_YEAR) == 1;
    }

    /**
     * 去点时分秒后返回
     *
     * @param date
     * @return
     */
    public static Date rounding(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 把时间加上day天后返回，如果传负数代表减day天
     *
     * @param date
     * @param day
     * @return
     */
    public static Date dateAdd(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + day);
        return calendar.getTime();
    }

    /**
     * 日期加或者减掉多少天，day为负数就为减掉
     *
     * @param data
     * @param day
     * @return
     */
    public static String getLaterDate2(String data, int day) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateT = null;
        try {
            dateT = sdf.parse(data);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateT);
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + day);
            String str = sdf.format(calendar.getTime());
            return str;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取上一个月的第一天
     *
     * @return
     */
    public static Date getFirstDayOfPreviousMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取上一年的第一天
     *
     * @return
     */
    public static Date getFirstDayOfPreviousYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static void main(String[] args) {
        System.out.println(convertDate(getFirstDayOfPreviousMonth(),
                DEFAULT_PATTERN));
        System.out.println(convertDate(getFirstDayOfPreviousYear(),
                DEFAULT_PATTERN));
        System.out.println(convertStringToDate("2012-07-10 11:09:38"));
    }

    /**
     * 时间差计算，当时差小于12小时时，显示N小时前，小于一小时是，显示刚刚
     *
     * @param date
     * @return string
     */
    public static String cutDate(String date) {
        String dateStr = null;
        Date dateTemp;
        Date dateNow;
        dateNow = new Date();
        dateTemp = convertStringToDate(date);
        long day = (dateNow.getTime() - dateTemp.getTime()) / (24 * 60 * 60 * 1000);
        long hour = ((dateNow.getTime() - dateTemp.getTime()) / (60 * 60 * 1000) - day * 24);
        //long min=((l/(60*1000))-day*24*60-hour*60);
        if (day > 0) {
            dateStr = day + "天前";
        } else if (day == 0 && hour == 0) {
            dateStr = "刚刚更新";
        } else {
            dateStr = hour + "小时前";
        }
        return dateStr;
    }

    /**
     * 时间差计算，当时差小于12小时时，显示N小时前，小于一小时是，显示刚刚
     *
     * @param date
     * @return string
     */
    public static String cutDate2(String date) {
        String dateStr = null;
        Date dateTemp;
        Date dateNow;
        dateNow = new Date();
        dateTemp = convertStringToDate(date);
        long day = (dateNow.getTime() - dateTemp.getTime()) / (24 * 60 * 60 * 1000);
        long hour = ((dateNow.getTime() - dateTemp.getTime()) / (60 * 60 * 1000) - day * 24);
        long min = (((dateNow.getTime() - dateTemp.getTime()) / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long sec = (((dateNow.getTime() - dateTemp.getTime()) / (1000)) - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        if (day > 0) {
            if (day == 1) {
                dateStr = "昨天";
            } else if (day == 2) {
                dateStr = "前天";
            } else if (day > 2 && day <= 10) {
                dateStr = day + "天前";
            } else {
                dateStr = date;
            }
        } else if (day == 0 && hour <= 0) {
            if (min >= 1) {
                dateStr = min + "分钟前";
            } else {
                dateStr = Math.abs(sec) + "秒前";
            }
        } else {
            dateStr = hour + "小时前";
        }
        return dateStr;
    }

    /**
     * 返回时间的年月日
     *
     * @param date
     * @return string
     */
    public static String cutDateYmd(String date) {
        String dateStr = null;
        Date dateTemp = convertStringToDate(date);
        Log.i("dateTemp", "" + dateTemp);
        dateStr = dateTemp.getYear() + 1900 + "." + (dateTemp.getMonth() + 1) + "." + dateTemp.getDate();
        return dateStr;
    }

    /**
     * 返回当前系统时间
     *
     * @return string
     */
    public static String getcurrentDay() {
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = DEFAULT_SDF.format(curDate);
        return str;
    }

    /**
     * 获取日期，格式如2014-04-03
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDate(String time) {
        String str = null;
        Date dateTemp = convertStringToDate(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        str = sdf.format(dateTemp);
        return str;
    }

    /**
     * 获取日期，格式如04-03 08:33
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDate5(String time) {
        String str = null;
        Date dateTemp = convertStringToDate(time);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        str = sdf.format(dateTemp);
        return str;
    }

    /**
     * 获取日期，格式如04-03
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDate6(String time) {
        String str = null;
        Date dateTemp = convertStringToDate(time);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
        str = sdf.format(dateTemp);
        return str;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDate8(String time) {
        String str = null;
        Date dateTemp = convertStringToDate(time);
        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd");
        str = sdf.format(dateTemp);
        return str;
    }

    /**
     * 获取日期，格式如201404031244
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDate3(String time) {
        String str = null;
        Date dateTemp = convertStringToDate(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        str = sdf.format(dateTemp);
        return str;
    }

    /**
     * 获取日期，格式如2014-04-03 12:44
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDate2(String time) {
        String str = null;
        Date dateTemp = convertStringToDate(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        str = sdf.format(dateTemp);
        Log.e("time", time + "---------" + str);
        return str;
    }

    /**
     * 获取日期，格式如2014-04-03 12:44:52
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDate7(String time) {
        String str = null;
        Date dateTemp = convertStringToDate(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        str = sdf.format(dateTemp);
        return str;
    }

    public static String getDate9(String time) {
        String str = null;
        Date dateTemp = convertStringToDate(time);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        str = sdf.format(dateTemp);
        return str;
    }

    public static String getDate10(String time) {
        String str = null;
        Date dateTemp = convertStringToDate(time);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        str = sdf.format(dateTemp);
        return str;
    }

    /**
     * 方法描述:抢购中把时间转换成想要的格式
     *
     * @param @param  time 剩余秒数
     * @param @return
     * @return String
     */
    public static String getTime(long time) {
        long day = (time) / (24 * 60 * 60 * 1000);
        String hms;
        if (day > 0) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd天HH:mm:ss");//初始化Formatter的转换格式。
            hms = formatter.format(time);
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");//初始化Formatter的转换格式。
            hms = formatter.format(time);
        }
//		 String hms = formatter.format(time);
        return hms;
    }

    /**
     * 将指定日期转换为秒数
     *
     * @param Date
     * @return
     */
    public static Long getSecondsFromDate(String Date) {
        if (Date == null || Date.trim().equals(""))
            return 0l;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = null;
        try {
            date = sdf.parse(Date);
            return (Long) (date.getTime() / 1000);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0l;
        }
    }

    /**
     * 将指定日期转换为秒数
     *
     * @param Date
     * @return
     */
    public static Long getSecondsFromDate2(String Date) {
        if (Date == null || Date.trim().equals(""))
            return 0l;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(Date);
//		            return (Long)(date.getTime()/1000);
            return sdf.parse(Date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0l;
        }
    }

    /**
     * 获取日期，格式如2014-04-03
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDate4(String time) {
        String str = null;
        Date dateTemp = convertStringToDate(time);
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        str = sdf.format(dateTemp);
        Log.e("time", time + "---------" + str);
        return str;
    }

    @SuppressLint("SimpleDateFormat")
    public static boolean compareTime(String startT, String endT, String sysT) {
        boolean isTrue = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date startTemp = sdf.parse(startT);
            Date endTemp = sdf.parse(endT);
            Date sysTemp = sdf.parse(sysT);
            Log.i("活动时间：", "开始：" + startTemp.getTime() + "--系统：" + sysTemp.getTime() + "--结束：" + endTemp.getTime());
            if (sysTemp.getTime() >= startTemp.getTime() && sysTemp.getTime() < endTemp.getTime()) {
                isTrue = true;
            } else {
                isTrue = false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isTrue;
    }
}
