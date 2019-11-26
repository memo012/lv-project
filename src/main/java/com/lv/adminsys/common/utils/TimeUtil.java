package com.lv.adminsys.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.common
 * @Description: 时间工具
 * @Date: 2019/7/4 0004 11:05
 **/
public class TimeUtil {

    /**
     *  日期转星期
     * @param datetime
     * @return
     */
    public static String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 格式化日期
     * 使用线程安全的DateTimeFormatter
     * @return “年-月-日”字符串
     */
    public String getFormatDateForThree(){

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return now.format(format);
    }

    /**
     * 格式化日期
     * 使用线程安全的DateTimeFormatter
     * @return “年-月-日 时:分:秒”字符串
     */
    public String getFormatDateForSix(){
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(format);
    }

    /**
     * 格式化日期
     * 使用线程安全的DateTimeFormatter
     * @return “年-月-日 时:分”字符串
     */
    public String getFormatDateForFive(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return now.format(format);
    }

    /**
     * 解析日期
     * @param date 日期 2018-06-21
     * @return
     */
    public LocalDate getParseDateForThree(String date){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDate.parse(date, format);
    }

    /**
     * 解析日期
     * 日期 2018-06-21 12:01:25
     * @return
     */
    public String getParseDateForSix(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(format);
    }

    /**
     * 解析日期
     * 日期 2018-06-21 12:01:25
     * @param data 日期date类型
     * @return
     */
    public String getParseDateForSix(Date data){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.format(data);
    }

    /**
     * 解析日期
     * 日期 2018-06-21
     * @param data 日期date类型
     * @return
     */
    public String getParseDateForFour(Date data){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(data);
    }

    /**
     * 获得当前时间的时间戳
     * @return 时间戳
     */
    public String getLongTime(){
        Date now = new Date();
        return String.valueOf(now.getTime()/1000);
    }

    /**
     * 时间中横杆转换为年
     * @param str 2018-08
     * @return 2018年8月
     */
    public String timeWhippletreeToYear(String str){
        StringBuilder s = new StringBuilder();
        s.append(str.substring(0,4));
        s.append("年");
        s.append(str.substring(5,7));
        s.append("月");
        return String.valueOf(s);
    }

    /**
     * 时间中的年转换为横杠
     * @param str 2018年07月
     * @return 2018-07
     */
    public String timeYearToWhippletree(String str){
        StringBuilder s = new StringBuilder();
        s.append(str.substring(0,4));
        s.append("-");
        s.append(str.substring(5,7));
        return String.valueOf(s);
    }

    /**
     * CST转变成字符串日期
     * @param str Thu May 07 14:33:19 CST 2015
     * @return
     * @throws ParseException
     */
    public String CSTChangeString(String str) throws ParseException {
        String pattern = "EEE MMM dd HH:mm:ss zzz yyyy";
        SimpleDateFormat df = new SimpleDateFormat(pattern,Locale.US);
        Date date = df.parse(str);
        return new TimeUtil().getParseDateForSix(date);
    }

    /**
     * 字符串日期变相应字符串日期
     * @param str 2019-11-09 17:06:50
     * @return 2019-11-09
     * @throws ParseException
     */
    public String StringFourTime(String str) throws ParseException {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        Date date = df.parse(str);
        return getParseDateForFour(date);
    }

    /**
     * 字符串日期变时间戳
     * @param str Thu May 07 14:33:19 CST 2015
     * @return
     * @throws ParseException
     */
    public String StringToLongTime(String str) throws ParseException {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        Date date = df.parse(str);
        return String.valueOf(date.getTime()/1000);
    }
    
    public static void main(String[] args) throws ParseException {
        System.out.println(new TimeUtil().StringFourTime("2019-11-09 17:06:50"));
    }
}
