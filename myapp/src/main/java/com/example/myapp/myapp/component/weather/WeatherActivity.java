package com.example.myapp.myapp.component.weather;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseActivity;
import com.example.myapp.myapp.base.BaseView;
import com.example.myapp.myapp.ui.adapter.RecyclerAdapter;
import com.example.myapp.myapp.ui.adapter.RecyclerHolder;
import com.example.myapp.myapp.ui.layoutmanager.ScrollLinearManager;
import com.example.myapp.myapp.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.basic.Update;
import interfaces.heweather.com.interfacesmodule.bean.weather.forecast.Forecast;
import interfaces.heweather.com.interfacesmodule.bean.weather.forecast.ForecastBase;
import interfaces.heweather.com.interfacesmodule.bean.weather.hourly.Hourly;
import interfaces.heweather.com.interfacesmodule.bean.weather.hourly.HourlyBase;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.NowBase;

/**
 * Created by yexing on 2018/8/30.
 */

public class WeatherActivity extends BaseActivity implements BaseView<WeatherPresenter> {

    private LinearLayout mBg;
    private WeatherPresenter mPresenter;
    private Toolbar mToolBar;
    private TextView mTitle;
    private RecyclerView mRecyclerView;
    private List<HourlyBase> hourlyList = new ArrayList<>();
    private RecyclerAdapter<ForecastBase> mForecastAdapter;
    private RecyclerAdapter<HourlyBase> mAdapter;
    private TextView mLastUpdateTime;
    private TextView mTmp;
    private TextView mWeatherState;
    private RecyclerView mRecyclerView_2;
    //未来天气情况
    private List<ForecastBase> daily_forecast = new ArrayList<>();


    @Override
    public int inflateContentView() {
        return R.layout.activity_weather;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        new WeatherPresenter(this);
        mBg = findViewById(R.id.ll_bg);
        mToolBar = findViewById(R.id.toolbar);
        mTitle = findViewById(R.id.title);
        mTmp = findViewById(R.id.tv_temperature);
        mWeatherState = findViewById(R.id.tv_weather_state);
        mLastUpdateTime = findViewById(R.id.tv_last_update);
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView_2 = findViewById(R.id.recyclerview_future_weather);

    }

    @Override
    protected void initData() {
        mPresenter.getWeatherNow();

        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR,
                new int[]{
                        getResources().getColor(R.color.weather),
                        getResources().getColor(R.color.weather1),
                        getResources().getColor(R.color.weather2),
                        getResources().getColor(R.color.weather3),
                        getResources().getColor(R.color.weather4),
                        getResources().getColor(R.color.weather5),
                        getResources().getColor(R.color.weather6),
                });
        setStatusBarColor(R.color.weather2);
        mBg.setBackground(gradientDrawable);
        mToolBar.setBackgroundColor(getResources().getColor(R.color.transparent));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView_2.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RecyclerAdapter<HourlyBase>(R.layout.item_weather_hour, hourlyList) {
            @Override
            protected void onBindHolder(RecyclerHolder holder, HourlyBase model, int position) {
                String time = model.getTime();
                holder.text(R.id.tv_time, time.substring(5, time.length()));
                holder.text(R.id.tv_tmp, model.getTmp() + "°");
            }
        };
        mRecyclerView.setAdapter(mAdapter);
        mForecastAdapter = new RecyclerAdapter<ForecastBase>(R.layout.item_weather_forecast, daily_forecast) {
            @Override
            protected void onBindHolder(RecyclerHolder holder, ForecastBase model, int position) {
                holder.text(R.id.tv_time, model.getDate().substring(5, model.getDate().length()));
                String tmp_max = model.getTmp_max();
                String tmp_min = model.getTmp_min();
                holder.text(R.id.tv_tmp, tmp_max + "°" + "/" + tmp_min + "°");
            }
        };
        mRecyclerView_2.setAdapter(mForecastAdapter);

    }

    @Override
    protected boolean isNeedToBeSubscriber() {
        return false;
    }

    @Override
    public void setPresenter(WeatherPresenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 获取逐小时预报
     *
     * @param hourlyList
     */
    public void setHourlyList(List<Hourly> hourlyList) {
        Hourly hourly = hourlyList.get(0);
        this.hourlyList.addAll(hourly.getHourly());
        //上次更新时间
        Update update = hourly.getUpdate();
        String loc = update.getLoc();
        mLastUpdateTime.setText(String.format("上次更新时间：%s", loc));
        String location = hourly.getBasic().getLocation();
        mTitle.setText(location);
        mAdapter.notifyDataSetChanged();
        mPresenter.getWeatherForecast();
    }

    /**
     * 获取逐小时预报失败
     *
     * @param errorMsg
     */
    public void getHourlyListFail(String errorMsg) {
        ToastUtil.showApp(errorMsg);
    }

    /**
     * 获取实况天气
     *
     * @param nowList
     */
    public void setWeatherNow(List<Now> nowList) {
        Now now = nowList.get(0);
        NowBase nowBase = now.getNow();
        String tmp = nowBase.getTmp();
        mTmp.setText(tmp);
        mWeatherState.setText(nowBase.getCond_txt());
        mPresenter.getWeatherHourly();
    }

    /**
     * 获取实况天气失败
     *
     * @param errorMsg
     */
    public void getWeatherNowFail(String errorMsg) {
        ToastUtil.showApp(errorMsg);
    }

    /**
     * 获取未来天气情况
     *
     * @param list
     */
    public void setWeatherForecast(List<Forecast> list) {
        List<ForecastBase> daily_forecast = list.get(0).getDaily_forecast();
        this.daily_forecast.addAll(daily_forecast);
        mForecastAdapter.notifyDataSetChanged();
    }

    /**
     * 获取未来天气失败
     *
     * @param errorMsg
     */
    public void getWeatherFail(String errorMsg) {
        ToastUtil.showApp(errorMsg);
    }
}
