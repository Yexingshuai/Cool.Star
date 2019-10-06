package com.example.myapp.myapp.component.map.listener;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.MyLocationData;

public class LocationListener extends BDAbstractLocationListener {
    private LocationCallBack mLocationCallBack;

    @Override
    public void onReceiveLocation(BDLocation location) {

        if (location == null) {
            return;
        }

        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(location.getDirection()).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        if (mLocationCallBack != null) {
            mLocationCallBack.setLocationInfo(locData);
        }
    }


    public LocationListener(LocationCallBack locationCallBack) {
        mLocationCallBack = locationCallBack;
    }

    public interface LocationCallBack {
        void setLocationInfo(MyLocationData locData);
    }

}
