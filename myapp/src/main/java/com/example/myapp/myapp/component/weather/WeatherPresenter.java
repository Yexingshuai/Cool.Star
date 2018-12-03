package com.example.myapp.myapp.component.weather;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.myapp.myapp.base.BasePresenter;
import com.example.myapp.myapp.utils.PermissonUtil;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.Lang;
import interfaces.heweather.com.interfacesmodule.bean.Unit;
import interfaces.heweather.com.interfacesmodule.bean.weather.forecast.Forecast;
import interfaces.heweather.com.interfacesmodule.bean.weather.hourly.Hourly;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;

/**
 * Created by yexing on 2018/8/30.
 */

public class WeatherPresenter implements BasePresenter {
    private WeatherActivity mActivity;

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    public WeatherPresenter(WeatherActivity activity) {
        this.mActivity = activity;
        mActivity.setPresenter(this);
    }

    /**
     * 获取实时数据
     */
    public void getWeatherHourly() {
        HeWeather.getWeatherHourly(mActivity, new HeWeather.OnResultWeatherHourlyBeanListener() {
            @Override
            public void onError(Throwable throwable) {
                mActivity.getHourlyListFail(throwable.getMessage());
            }

            @Override
            public void onSuccess(List<Hourly> list) {
                mActivity.setHourlyList(list);
            }
        });
    }

    /**
     * 获取实况天气
     */
    public void getWeatherNow() {
        HeWeather.getWeatherNow(mActivity, "auto_ip", Lang.CHINESE_SIMPLIFIED, Unit.METRIC, new HeWeather.OnResultWeatherNowBeanListener() {
            @Override
            public void onError(Throwable throwable) {
                mActivity.getWeatherNowFail(throwable.getMessage());
            }

            @Override
            public void onSuccess(List<Now> list) {
                mActivity.setWeatherNow(list);
            }
        });

    }

    /**
     * 检查权限
     */
    public void checkPermisson() {
        PermissonUtil.requestPermisson(mActivity, Manifest.permission.ACCESS_FINE_LOCATION, "地理位置", new PermissonUtil.PermissonListener() {
            @Override
            public void onPermissonGranted() {
                getWeatherNow();
            }

            @Override
            public void onPermissonDenied() {
                mActivity.finish();
            }
        });
    }

    /**
     * 获取3-10天 天气预报
     */
    public void getWeatherForecast() {
        HeWeather.getWeatherForecast(mActivity, new HeWeather.OnResultWeatherForecastBeanListener() {
            @Override
            public void onError(Throwable throwable) {
                mActivity.getWeatherFail(throwable.getMessage());
            }

            @Override
            public void onSuccess(List<Forecast> list) {
                mActivity.setWeatherForecast(list);
            }
        });
    }
}
