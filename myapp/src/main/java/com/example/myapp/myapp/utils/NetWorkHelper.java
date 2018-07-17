package com.example.myapp.myapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author pd_liu on 2018/4/12.
 *         <p>
 *         NetWorkHelper
 *         </p>
 *         <p>
 *         Usage:
 *         1、在全局进行初始化 {@link #initialize(Context)}
 *         2、获取各种网络信息 {@link #isMobileAvailable()} {@link #isMobileConnected()} {@link #isWifiAvailable()}{@link #isWifiConnected()}
 *         {@link #isNetworkAvailable()}{@link #isNetworkConnected()}
 *         </p>
 */

public class NetWorkHelper {

    private Context mContext;

    private NetWorkHelper() {
    }

    public static NetWorkHelper getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * Initialize config.
     *
     * @param applicationContext 应用上下文对象
     */
    public void initialize(Context applicationContext) {
        mContext = applicationContext;
    }

    /**
     * 判断是否有网络开关打开，并且有网络连接
     *
     * @return if true, the net connected
     * @see #isNetworkAvailable()
     */
    public boolean isNetworkConnected() {
        if (mContext != null) {

            NetworkInfo mNetworkInfo = getConnectivityManager().getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isConnected();
            }
        }
        return false;
    }

    /**
     * 判断是否有网络开关为打开状态
     *
     * @return
     * @see #isNetworkConnected()
     */
    public boolean isNetworkAvailable() {
        if (mContext != null) {

            NetworkInfo mNetworkInfo = getConnectivityManager().getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 是否打开Wifi开关，并且正在使用Wifi传输数据
     *
     * @return
     * @see #isWifiAvailable()
     */
    public boolean isWifiConnected() {

        if (mContext != null) {
            NetworkInfo wifi = getConnectivityManager().getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (wifi != null) {
                return wifi.isConnected();
            }
        }

        return false;
    }

    /**
     * 是否打开Wifi 开关(当打开Wifi开关，并没有连接上Wifi时，此方法依然返回true)
     *
     * @return
     * @see #isWifiConnected()
     */
    public boolean isWifiAvailable() {
        if (mContext != null) {
            NetworkInfo wifi = getConnectivityManager().getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (wifi != null) {
                return wifi.isAvailable();
            }
        }

        return false;
    }

    private ConnectivityManager getConnectivityManager() {
        return (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    /**
     * 判断手机移动网络是否连接
     *
     * @return
     * @see #isMobileConnected()
     */
    public boolean isMobileAvailable() {
        if (mContext != null) {
            NetworkInfo mobile = getConnectivityManager().getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mobile != null) {
                return mobile.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断手机移动网络是否连接，并且进行传递数据
     *
     * @return
     * @see #isMobileAvailable()
     */
    public boolean isMobileConnected() {
        if (mContext != null) {
            NetworkInfo mobile = getConnectivityManager().getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mobile != null) {
                return mobile.isConnected();
            }
        }
        return false;
    }

    static class Holder {
        private static final NetWorkHelper INSTANCE = new NetWorkHelper();
    }
}
