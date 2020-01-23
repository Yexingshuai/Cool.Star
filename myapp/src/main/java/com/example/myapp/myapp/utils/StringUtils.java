package com.example.myapp.myapp.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by DuanJiaNing on 2017/6/9.
 */

public class StringUtils {

    public static String stringToMd5(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static String getGenTimeMS(int misec) {
        int min = misec / 1000 / 60;
        int sec = (misec / 1000) % 60;
        String minStr = min < 10 ? "0" + min : min + "";
        String secStr = sec < 10 ? "0" + sec : sec + "";
        return minStr + ":" + secStr;
    }

    public static String getGenDateYMDHMS(long time) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(time));
    }

    public static String getGenDateYMD(long time) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date(time));
    }

    public static boolean isReal(String string) {
        return string != null && string.length() > 0 && !"null".equals(string);
    }


    public static String getZHMonth(int month) {
        String s = "";
        switch (month) {
            case 1:
                s = "一月";
                break;
            case 2:
                s = "二月";
                break;
            case 3:
                s = "三月";
                break;
            case 4:
                s = "四月";
                break;
            case 5:
                s = "五月";
                break;
            case 6:
                s = "六月";
                break;
            case 7:
                s = "七月";
                break;
            case 8:
                s = "八月";
                break;
            case 9:
                s = "九月";
                break;
            case 10:
                s = "十月";
                break;
            case 11:
                s = "十一月";
                break;
            case 12:
                s = "十二月";
                break;

        }
        return s;
    }


    /**
     * 根据日期获取当天是周几
     *
     * @param datetime 日期
     * @return 周几
     */
    public static String dateToWeek(String datetime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        Calendar cal = Calendar.getInstance();
        Date date;
        try {
            date = sdf.parse(datetime);
            cal.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDays[w];
    }

}
