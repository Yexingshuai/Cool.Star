package com.example.myapp.myapp.utils;

import com.orhanobut.logger.Logger;



/**
 * @author  star
 *         <p>
 *         日志工具类
 *         </p>
 *         <p>
 *         Switch log print status {@link }
 *         </p>
 */

public final class LogUtil {

    private static final String TAG_LOG = "LogUtil";

    private LogUtil() {
        new UnsupportedOperationException("非法的创建对象!");
    }

    public static boolean sDebug = true;

    public static void setDebug(boolean isDebug) {
        sDebug = isDebug;
    }

    public static void e(String tag, String message) {
        if (sDebug) {
            Logger.e(String.format("TAG: %s \nMsg: %s", tag, message));
        }
    }

    public static void i(String tag, String message) {
        if (sDebug) {
            Logger.i(String.format("TAG: %s \nMsg: %s", tag, message));
        }
    }


    public static void d(String tag, String message) {
        if (sDebug) {
            Logger.d(String.format("TAG: %s \nMsg: %s", tag, message));
        }
    }

    public static void w(String tag, String message) {
        if (sDebug) {
            Logger.w(String.format("TAG: %s \nMsg: %s", tag, message));
        }
    }
}
