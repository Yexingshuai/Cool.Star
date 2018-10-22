package com.example.myapp.myapp.data.source.login;

import com.example.myapp.myapp.data.api.WandroidApi;
import com.example.myapp.myapp.data.http.HttpContext;

/**
 * Created by yexing on 2018/8/8.
 */

public class LoginRepository implements LoginSource {
    @Override
    public void register(String account, String password, String repassword, HttpContext.Response response) {
        HttpContext httpContext = new HttpContext();
        WandroidApi wandroidApi = httpContext.createApi(WandroidApi.class);
        httpContext.execute(wandroidApi.register(account, password, repassword), response);
    }

    @Override
    public void login(String account, String password, HttpContext.Response response) {
        HttpContext httpContext = new HttpContext();
        WandroidApi wandroidApi = httpContext.createApi(WandroidApi.class);
        httpContext.execute(wandroidApi.login(account, password), response);
    }
}
