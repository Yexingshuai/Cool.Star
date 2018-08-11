package com.example.myapp.myapp.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.myapp.myapp.common.InitializeConfig;


/**
 * @author pd_liu on 2018/3/1.
 *         <p>
 *         吐司工具类
 *         </p>
 *         <p>
 *         {@link #showApp(String)}
 *         </p>
 */

public class ToastUtil {

    private static final String TAG_LOG = "ToastUtil";

    private static Context mContextApp;

    private static Toast mToast;

    private ToastUtil() {
    }

    public static void initToastApp(Context appContext) {
        mContextApp = appContext;
    }

    @Deprecated
    public static void show(Context context, String message) {
        toast(context, message);
    }

    public static void showNewToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showAppDebug(String msg) {
        if (InitializeConfig.isDebug) {
            showApp(msg);
        }
    }

    /**
     * 土司
     * 如果消息为空、并且在调试状态下，则显示提示信息
     * 如果消息为空、并且在上线状态下，则不显示任何信息
     *
     * @param msg 消息
     */
    public static void showApp(String msg) {

        if (mContextApp != null) {
            if (TextUtils.isEmpty(msg)) {
                if (InitializeConfig.isDebug) {

                } else {
                    return;
                }

            }

            toast(mContextApp, msg);
        }
    }

    private static final void toast(Context context, String msg) {
//        if (mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
//        }

        mToast.setText(msg);
        mToast.show();
    }
}
