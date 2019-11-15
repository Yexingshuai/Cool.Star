package com.example.myapp.myapp.component.map;

import android.content.Context;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.myapp.myapp.component.map.listener.LocationListener;


/**
 * 获取位置信息
 */
public class LocationHelper {


    private Context mContext;
    private LocationClient mLocationClient;


    /**
     * 获取单例对象
     *
     * @return
     */
    public static LocationHelper getInstance() {

        return Holder.INSTANCE;
    }


    public static class Holder {

        private static final LocationHelper INSTANCE = new LocationHelper();
    }

    public void init(Context context, LocationListener.LocationCallBack callBack) {
        this.mContext = context;

//        //定位初始化
        mLocationClient = new LocationClient(mContext);

        //通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        option.setIsNeedAddress(true); //需要获取位置信息

        //设置locationClientOption
        mLocationClient.setLocOption(option);

        //注册LocationListener监听器
        LocationListener myLocationListener = new LocationListener(callBack);
        mLocationClient.registerLocationListener(myLocationListener);

    }


    /**
     * 开启定位
     */
    public void startLocation() {
        //开启地图定位图层
        mLocationClient.start();
    }


    public void stop() {
        mLocationClient.stop();
    }


}

