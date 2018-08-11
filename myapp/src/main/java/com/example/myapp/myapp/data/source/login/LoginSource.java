package com.example.myapp.myapp.data.source.login;

import com.example.myapp.myapp.data.http.HttpContext;

/**
 * Created by yexing on 2018/8/8.
 */

public interface LoginSource {

    void register(String account, String password, String repassword, HttpContext.Response response);

    void login(String account, String password, HttpContext.Response response);
}
