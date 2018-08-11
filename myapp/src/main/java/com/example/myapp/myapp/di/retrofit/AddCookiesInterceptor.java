package com.example.myapp.myapp.di.retrofit;

import android.util.Log;

import com.example.myapp.myapp.base.MyApp;
import com.example.myapp.myapp.common.AppFlag;
import com.example.myapp.myapp.component.login.helper.LoginContext;
import com.example.myapp.myapp.utils.PreferencesUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by yexing on 2018/8/9.
 */

public class AddCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Response response = chain.proceed(chain.request());
        List<String> cookies = response.headers("Set-Cookie");

        if (LoginContext.getInstance().getUserCookieInfo() == null & cookies.size() > 2 && cookies != null) {
            LoginContext.getInstance().saveUserCookieInfo(cookies);
        }
        return response;
    }
}
