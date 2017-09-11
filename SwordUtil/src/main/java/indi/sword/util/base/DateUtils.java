package indi.sword.util.base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description 日期工具类
 * @Author:rd_jianbin_lin
 * @Date: 17:42 2017/9/11
 */
public class DateUtils {

    /**
     * @Description: TODO 将标准的时间字符串("yyyy-MM-dd HH:mm:ss")转化为Date型数据
     * @author rd_jianbin_lin 347576073@qq.com
     * @date 2015年6月17日 下午3:51:32
     */
    public static Date toTime(String str) {

        return toTime(str, getFormatPatternByDate(str));
    }

    /**
     * @Descrption 拿到SimpleDateFormat适配字符串
     * @author rd_jianbin_lin
     * @Date Jun 14, 2017 6:58:38 PM
     * @param str
     * @return
     */
    public static String getFormatPatternByDate(String str){

        str = str.trim();

        String pattern_yyyy = "^[0-9]{4}$";
        String pattern_yyyyMM = "^[0-9]{4}-[0-1]{0,1}[0-9]{1}$";
        String pattern_yyyyMMdd = "^[0-9]{4}-[0-1]{0,1}[0-9]{1}-[0-3]{0,1}[0-9]{1}$";
        String pattern_yyyyMMddHH = "^[0-9]{4}-[0-1]{0,1}[0-9]{1}-[0-3]{0,1}[0-9]{1} [0-2]{1}[0-9]{1}$";
        String pattern_yyyyMMddHHmm = "^[0-9]{4}-[0-1]{0,1}[0-9]{1}-[0-3]{0,1}[0-9]{1} [0-2]{1}[0-9]{1}(:|：){1}[0-6]{1}[0-9]{1}$";
        String pattern_yyyyMMddHHmmss = "^[0-9]{4}-[0-1]{0,1}[0-9]{1}-[0-3]{0,1}[0-9]{1} [0-2]{1}[0-9]{1}(:|：){1}[0-6]{1}[0-9]{1}(:|：){1}[0-6]{1}[0-9]{1}$";
        String pattern_yyyyMMddHHmmssSSS = "^[0-9]{4}-[0-1]{0,1}[0-9]{1}-[0-3]{0,1}[0-9]{1} [0-2]{1}[0-9]{1}(:|：){1}[0-6]{1}[0-9]{1}(:|：){1}[0-6]{1}[0-9]{1} [0-9]{3}$";

        String df_yyyy = "yyyy";
        String df_yyyyMM = "yyyy-MM";
        String df_yyyyMMdd = "yyyy-MM-dd";
        String df_yyyyMMddHH = "yyyy-MM-dd HH";
        String df_yyyyMMddHHmm = "yyyy-MM-dd HH:mm";
        String df_yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
        String df_yyyyMMddHHmmssSSS = "yyyy-MM-dd HH:mm:ss SSS";

        Map<String,String> map = new HashMap<String,String>();

        map.put(df_yyyy,pattern_yyyy);
        map.put(df_yyyyMM,pattern_yyyyMM);
        map.put(df_yyyyMMdd,pattern_yyyyMMdd);
        map.put(df_yyyyMMddHH,pattern_yyyyMMddHH);
        map.put(df_yyyyMMddHHmm,pattern_yyyyMMddHHmm);
        map.put(df_yyyyMMddHHmmss,pattern_yyyyMMddHHmmss);
        map.put(df_yyyyMMddHHmmssSSS,pattern_yyyyMMddHHmmssSSS);

        Pattern p1 = null;
        Matcher m1 = null;
        for(Map.Entry<String, String> entry : map.entrySet()){
            p1 = Pattern.compile(entry.getValue());
            m1 = p1.matcher(str);
            if(m1.find()){
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * @Description 将标准的时间字符串转化为Date型数据
     * @Author:rd_jianbin_lin
     * @Date: 17:41 2017/9/11
     */
    public static Date toTime(String str, String format_pattern, Locale locale) {
        if(!isValidDate(str,format_pattern)){
            return null;
        }
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format_pattern,
                    locale);
            Date date;
            date = formatter.parse(str);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @Title: 将标准的时间字符串转化为Date型数据
     * @author rd_jianbin_lin
     * @date 2017年6月17日 下午3:51:32
     */
    public static Date toTime(String str, String format_pattern) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format_pattern);
            return formatter.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @Description: TODO 将时间戳转化成固定格式数据
     * @param time 时间戳
     * @return Date
     * @author rd_jianbin_lin
     * @date 2017年2月24日 上午10:05:17
     */
    public static Date toTime(Long time){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String str = formatter.format(time);
            Date date = formatter.parse(str);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @Description 将标准的时间Date字符串转化为字符串("yyyy-MM-dd HH:mm:ss")
     * @Author:rd_jianbin_lin
     * @Date: 17:40 2017/9/11
     */
    public static String toString(Date date) {
        return toString(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String toString(Date date, String format) {
        if (StringUtils.isEmpty(date)) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format,Locale.US);
        String time = formatter.format(date);
        return time;
    }

    public static String toString(Date date, String format, Locale loc) {
        if (StringUtils.isEmpty(date)) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format, loc);
        String time = formatter.format(date);
        return time;
    }

    /**
     * @Description 给日期增加XX天
     * @Author:rd_jianbin_lin
     * @Date: 17:40 2017/9/11
     */
    public Date addDay(Date date, int days) {
        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        calender.add(Calendar.DAY_OF_YEAR, 60);
        date = calender.getTime();
        return date;
    }

    /**
     * @Description 时间格式化
     * @Author:rd_jianbin_lin
     * @Date: 17:40 2017/9/11
     */
    public static String formatDate(String date_string) {
        if(StringUtils.isEmpty(date_string)){
            return "";
        }

        String temp_string = date_string.replaceAll("\\/", "-")
                .replaceAll("\\\\", "-").replaceAll("\\.", "-")
                .replaceAll("\\s", "-").replaceAll("年", "-")
                .replaceAll("月", "-").replaceAll("日", "");

        String[] year_month_day = temp_string.split("-");
        String year = null;
        String month = null;
        String day = null;

        year = year_month_day[0];
        if (!year.matches("\\d{4}")) {
            if (year.matches("\\d{2}")) {
                if (year.charAt(0) == '0') {
                    year = "20" + year;
                } else {
                    year = "19" + year;
                }
            } else {
                return "";
            }
        }

        if (year_month_day.length == 1) {
            return year;
        }

        if (year_month_day.length >= 2) {
            month = year_month_day[1];
            if (!month.matches("\\d{2}")) {
                if (month.matches("\\d{1}")) {
                    month = "0" + month;
                } else {
                    return "";
                }
            }
        }

        if (year_month_day.length == 3) {
            day = year_month_day[2];
            if (!day.matches("\\d{2}")) {
                if (day.matches("\\d{1}")) {
                    day = "0" + day;
                } else {
                    return "";
                }
            }
        }
        if (StringUtils.isEmpty(day)) {
            return year + "-" + month;
        }
        return year + "-" + month + "-" + day;
    }

    /**
     * @Description 判断日期格式
     * @Author:rd_jianbin_lin
     * @Date: 17:41 2017/9/11
     */
    public static boolean isValidDate(String date_string, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            Date date = formatter.parse(date_string);
            return date_string.equals(formatter.format(date));
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println(formatDate("2013"));
        System.out.println(formatDate("2013").length());
    }
}

