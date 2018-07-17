package com.example.myapp.myapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by DuanJiaNing on 2017/5/30.
 */

public class Utils {

    public static DisplayMetrics getMetrics(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics;
    }

    public static int getStatusBarHeight(Activity activity) {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        if (statusBarHeight <= 0) { // 有时会获取失败
            int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                //根据资源ID获取响应的尺寸值
                statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
            }
        }

        if (statusBarHeight <= 0) {
            statusBarHeight = 63;
        }

        return statusBarHeight;
    }

    public static void transitionStatusBar(Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    //该参数指布局能延伸到navigationbar，我们场景中不应加这个参数
                    //| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

    }


    public static void pretendToRun(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获得 ListView 的高度，每一项的高度之和
     */
    public static int getListViewHeight(@NonNull ListView listView) {
        int totalHeight = 0;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
        }
        return totalHeight;
    }

    public static String getApplicationMetaData(Context context, String name) throws PackageManager.NameNotFoundException {
        ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        String str = appInfo.metaData.getString(name);
        String res = null;
        if (StringUtils.isReal(str) && str.indexOf("*") == str.length() - 1) {
            res = str.substring(0, str.length() - 1);
        }
        return res;

    }


    // 把dp转换成px
    public static int dp2px(Context context,int dp) {
        // px = dp * 密度比 0.75 1 1.5 2
        float density = context.getResources().getDisplayMetrics().density;// 获取当前手机的密度比
        return (int) (dp * density + 0.5f);// 四舍五入
    }


    /**
     * 手机标识码
     */
    public static String getImCode(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = telephonyManager.getDeviceId();
        return deviceId;
    }


    /**
     * 获取屏幕的宽度
     * @param context
     * @return
     */
    public static  int getScreenWidthDp(Context context){
        WindowManager wm = (WindowManager)
                context.getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }
}