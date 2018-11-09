package com.example.myapp.myapp.di.retrofit;

import android.util.Log;

import com.example.myapp.myapp.base.MyApp;
import com.example.myapp.myapp.common.AppFlag;
import com.example.myapp.myapp.component.login.helper.LoginContext;
import com.example.myapp.myapp.utils.PreferencesUtils;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yexing on 2018/8/9.
 */

public class ReceivedCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder builder = chain.request().newBuilder();

        ArrayList<String> userCookieInfo = LoginContext.getInstance().getUserCookieInfo();
        if (userCookieInfo != null) {
            builder.addHeader("Cookie", userCookieInfo.get(0));
            builder.addHeader("Cookie", userCookieInfo.get(1));
            builder.addHeader("Cookie", userCookieInfo.get(2));
        }
        return chain.proceed(builder.build());

    }
}
