package base;

import android.app.Application;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.logging.Level;

import common.InitializeConfig;
import http.AppUrl;
import http.HttpUtil;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by daixiankade on 2018/3/28.
 */

public class app extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        InitializeConfig.init(this);
    }
}
