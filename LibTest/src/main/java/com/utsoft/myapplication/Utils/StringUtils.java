package com.utsoft.myapplication.Utils;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作工具包
 *
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class StringUtils {
    /**
     * Helper function for making null strings safe for comparisons, etc.
     *
     * @return (s == null) ? "" : s;
     */
    public static String makeSafe(String s) {
        return (s == null) ? "" : s;
    }

    public static final String EMPTY = "";

    /**
     * @param str
     * @return
     */
    public static String trim(String str) {
        return str == null ? EMPTY : str.trim();
    }

    private final static Pattern emailer = Pattern
            .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

    private final static Pattern password = Pattern
            .compile("[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]");


    private final static Pattern IMG_URL = Pattern
            .compile(".*?(gif|jpeg|png|jpg|bmp)");

    private final static Pattern URL = Pattern
            .compile("^(https|http)://.*?$(net|com|.com.cn|org|me|)");

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        return toDate(sdate, dateFormater.get());
    }

    public static Date toDate(String sdate, SimpleDateFormat dateFormater) {
        try {
            return dateFormater.parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getDateString(Date date) {
        return dateFormater.get().format(date);
    }

    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public static String friendly_time(String sdate) {
        Date time = null;
        if (TimeZoneUtil.isInEasternEightZones())
            time = toDate(sdate);
        else
            time = TimeZoneUtil.transformTime(toDate(sdate),
                    TimeZone.getTimeZone("GMT+08"), TimeZone.getDefault());
        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();
        // 判断是否是同一天
        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
            return ftime;
        }
        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天 ";
        } else if (days > 2 && days < 31) {
            ftime = days + "天前";
        } else if (days >= 31 && days <= 2 * 31) {
            ftime = "一个月前";
        } else if (days > 2 * 31 && days <= 3 * 31) {
            ftime = "2个月前";
        } else if (days > 3 * 31 && days <= 4 * 31) {
            ftime = "3个月前";
        } else {
            ftime = dateFormater2.get().format(time);
        }
        return ftime;
    }

    public static String friendly_time2(String sdate) {
        String res = "";
        if (isEmpty(sdate))
            return "";

        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        String currentData = StringUtils.getDataTime("MM-dd");
        int currentDay = toInt(currentData.substring(3));
        int currentMoth = toInt(currentData.substring(0, 2));

        int sMoth = toInt(sdate.substring(5, 7));
        int sDay = toInt(sdate.substring(8, 10));
        int sYear = toInt(sdate.substring(0, 4));
        Date dt = new Date(sYear, sMoth - 1, sDay - 1);

        if (sDay == currentDay && sMoth == currentMoth) {
            res = "今天 / " + weekDays[getWeekOfDate(new Date())];
        } else if (sDay == currentDay + 1 && sMoth == currentMoth) {
            res = "昨天 / " + weekDays[(getWeekOfDate(new Date()) + 6) % 7];
        } else {
            if (sMoth < 10) {
                res = "0";
            }
            res += sMoth + "/";
            if (sDay < 10) {
                res += "0";
            }
            res += sDay + " / " + weekDays[getWeekOfDate(dt)];
        }

        return res;
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static int getWeekOfDate(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return w;
    }

    /**
     * 判断给定字符串时间是否为今日
     *
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 返回long类型的今天的日期
     *
     * @return
     */
    public static long getToday() {
        Calendar cal = Calendar.getInstance();
        String curDate = dateFormater2.get().format(cal.getTime());
        curDate = curDate.replace("-", "");
        return Long.parseLong(curDate);
    }

    public static String getCurTimeStr() {
        Calendar cal = Calendar.getInstance();
        String curDate = dateFormater.get().format(cal.getTime());
        return curDate;
    }

    /***
     * 计算两个时间差，返回的是的秒s
     *
     * @param dete1
     * @param date2
     * @return
     * @author 火蚁 2015-2-9 下午4:50:06
     */
    public static long calDateDifferent(String dete1, String date2) {

        long diff = 0;

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = dateFormater.get().parse(dete1);
            d2 = dateFormater.get().parse(date2);

            // 毫秒ms
            diff = d2.getTime() - d1.getTime();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return diff;
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input) || "none".equals(input) || "null".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        return emailer.matcher(email).matches();
    }


    public static boolean isPassword(String str) {
        if (str == null || str.trim().length() == 0)
            return false;

        if (password.matcher(str).find()) {
            return false;
        }

        if (containsEmoji(str)) {
            return false;
        }
        return true;
//        return password.matcher(str).matches();
    }


    public static boolean checkPassword(String input) {
        // 6-12 位，字母、数字、字符
        String regStr = "^([A-Z]|[a-z]|[0-9]|[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]){6,12}$";
        Pattern password = Pattern
                .compile(regStr);
        return password.matcher(input).matches();
    }


    public static boolean containsEmoji(String source) {
        Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher emojiMatcher = emoji.matcher(source);
        if (emojiMatcher.find()) {
            //source = emojiMatcher.replaceAll("*");
            return true;
        }
        return false;
    }


    /**
     * 判断一个url是否为图片url
     *
     * @param url
     * @return
     */
    public static boolean isImgUrl(String url) {
        if (url == null || url.trim().length() == 0)
            return false;
        return IMG_URL.matcher(url).matches();
    }

    /**
     * 判断是否为一个合法的url地址
     *
     * @param str
     * @return
     */
    public static boolean isUrl(String str) {
        if (str == null || str.trim().length() == 0)
            return false;
        return URL.matcher(str).matches();
    }

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 字符串转布尔值
     *
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }

    public static String getString(String s) {
        return s == null ? "" : s;
    }

    /**
     * 将一个InputStream流转换成字符串
     *
     * @param is
     * @return
     */
    public static String toConvertString(InputStream is) {
        StringBuffer res = new StringBuffer();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader read = new BufferedReader(isr);
        try {
            String line;
            line = read.readLine();
            while (line != null) {
                res.append(line + "<br>");
                line = read.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != isr) {
                    isr.close();
                    isr.close();
                }
                if (null != read) {
                    read.close();
                    read = null;
                }
                if (null != is) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
            }
        }
        return res.toString();
    }

    /***
     * 截取字符串
     *
     * @param start 从那里开始，0算起
     * @param num   截取多少个
     * @param str   截取的字符串
     * @return
     */
    public static String getSubString(int start, int num, String str) {
        if (str == null) {
            return "";
        }
        int leng = str.length();
        if (start < 0) {
            start = 0;
        }
        if (start > leng) {
            start = leng;
        }
        if (num < 0) {
            num = 1;
        }
        int end = start + num;
        if (end > leng) {
            end = leng;
        }
        return str.substring(start, end);
    }

    /**
     * 获取当前时间为每年第几周
     *
     * @return
     */
    public static int getWeekOfYear() {
        return getWeekOfYear(new Date());
    }

    /**
     * 获取当前时间为每年第几周
     *
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        int week = c.get(Calendar.WEEK_OF_YEAR) - 1;
        week = week == 0 ? 52 : week;
        return week > 0 ? week : 1;
    }

    public static int[] getCurrentDate() {
        int[] dateBundle = new int[3];
        String[] temp = getDataTime("yyyy-MM-dd").split("-");

        for (int i = 0; i < 3; i++) {
            try {
                dateBundle[i] = Integer.parseInt(temp[i]);
            } catch (Exception e) {
                dateBundle[i] = 0;
            }
        }
        return dateBundle;
    }

//    public static int getAge(String birthday) {
//        int age = 0;
//        if (StringUtils.isEmpty(birthday))
//            return 0;
//        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
//        age = currentYear - Integer.parseInt(birthday.split("-")[0]);
//        if (age > 120) {
//            age = 100;
//        }
//        if (age < 5) {
//            age = 5;
//        }
//        return age;
//    }

    /**
     * 返回当前系统时间
     */
    public static String getDataTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }


    public static String encode(String testStr) {
        String ut_utf8 = "";
        if (isEmpty(testStr)) return ut_utf8;
        try {
            ut_utf8 = URLEncoder.encode(testStr, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ut_utf8;
    }

    public static String DEcode(String testStr) {
        String ut_utf8 = "";
        if (isEmpty(testStr)) return ut_utf8;
        try {
            ut_utf8 = URLDecoder.decode(testStr, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ut_utf8;
    }


    /*
     * 毫秒转化时分秒毫秒
     */
    public static String formatTime(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        if (day > 0) {
            sb.append(day + "d");
        }
        if (hour > 0) {
            sb.append(hour + "h");
        }
        if (minute > 0) {
            sb.append(minute + "'");
        }
        if (second > 0) {
            sb.append(second + "''");
        }
//        if (milliSecond > 0) {
//            sb.append(milliSecond + "毫秒");
//        }
        return sb.toString();
    }

    public static String formatTime_to_seek_video(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
//        if (day > 0) {
//            sb.append(day + "d");
//        }
        if (hour > 0) {
            sb.append(hour + ":");
        } else {
            sb.append(0 + ":");
        }
        if (minute >= 10) {
            sb.append(minute + ":");
        } else {
            sb.append("0" + minute + ":");
        }
        if (second >= 10) {
            sb.append(second + "");
        } else {
            sb.append("0" + second + "");
        }
        return sb.toString();
    }


    public static String formatTime_to_select_video(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        if (day > 0) {
            sb.append(day + "d");
        }
        if (hour > 0) {
            sb.append(hour + "h");
        }
        if (minute >= 0) {
            sb.append(minute + "'");
        }
        if (second > 0) {
            sb.append(second + "''");
        }
//        if (milliSecond > 0) {
//            sb.append(milliSecond + "毫秒");
//        }
        return sb.toString();
    }

    public static String parseUrl(String surl) {
        String utf_8 = surl;
        for (int i = 0; i < surl.length(); i++) {
            if ((surl.charAt(i) + "").getBytes().length > 1) {
                try {
                    utf_8 = utf_8.replace(surl.charAt(i) + "", URLEncoder.encode(surl.charAt(i) + "", "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return utf_8;
                }
            }
        }
        return utf_8;
    }


    public static String encodeWEBurl(String surl) {

        surl = surl
                .replace("%20", "")
                .replace("%21", "!")
                .replace("%22", " \"")
                .replace("%23", "#")
                .replace("%24", "$")
                .replace("%25", "%")
                .replace("%26", "&")
                .replace("%27", "'")
                .replace("%28", "(")
                .replace("%29", ")")
                .replace("%2A", "*")
                .replace("%2B", "+")
                .replace("%2c", ",")
                .replace("%2d", "-")
                .replace("%2e", ".")
                .replace("%2F", "/")
                .replace("%3A", ":")
                .replace("%3B", ";")
                .replace("%3D", "=")
                .replace("%3F", "?")
                .replace("%40", "@");


        return surl;
    }

//    public static String formatTime(long time) {
//        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
//        return formatter.format(new Date(time));
//    }


    public static String getReviewTimes(String hotNum) {
        int k = 0;
        if (!TextUtils.isEmpty(hotNum) && hotNum.matches("[0-9]+"))
            k = Integer.parseInt(hotNum);
        else
            return "0 次播放";
        String h = k + "";
        if (k >= 1000)
            h = (k / 1000.00) + "k";
        return h + " 次播放";
    }

    public static String getReviewTimesInt(int hotNum) {
        String hotNum1 = hotNum + " 次播放";
        DecimalFormat df = new DecimalFormat("#.0");

        Logger.i("hotNum1", "" + hotNum1);
        if (hotNum >= 10000) {
            double percent = (hotNum / 10000.00);


            hotNum1 = df.format(percent) + "万" + " 次播放";
            return hotNum1;
        }
//        else if (hotNum >= 1000) {
//            double percent = (hotNum / 1000.00);
//
//
//            hotNum1 = df.format(percent) + "" + " 次播放";
//            return hotNum1;
//        }
        return hotNum1;
    }


    public static String getReviewTimesInt_trace_pic(int hotNum) {
        String hotNum1 = hotNum + " 次浏览";
        DecimalFormat df = new DecimalFormat("#.0");

        Logger.i("hotNum1", "" + hotNum1);
        if (hotNum >= 10000) {
            double percent = (hotNum / 10000.00);


            hotNum1 = df.format(percent) + "万" + " 次浏览";
            return hotNum1;
        }
//        else if (hotNum >= 1000) {
//            double percent = (hotNum / 1000.00);
//
//
//            hotNum1 = df.format(percent) + "" + " 次播放";
//            return hotNum1;
//        }
        return hotNum1;
    }
}
