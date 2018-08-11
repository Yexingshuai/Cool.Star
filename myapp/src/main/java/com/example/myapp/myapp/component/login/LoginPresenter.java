package com.example.myapp.myapp.component.login;

import android.text.TextUtils;

import com.example.myapp.myapp.data.bean.RegisterResponse;
import com.example.myapp.myapp.data.http.HttpContext;
import com.example.myapp.myapp.data.source.login.LoginSource;
import com.example.myapp.myapp.utils.ToastUtil;
import com.example.myapp.myapp.utils.Tools;

/**
 * Created by yexing on 2018/8/8.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginSource mSource;
    private LoginContract.View mView;

    public LoginPresenter(LoginSource source, LoginContract.View view) {

        mSource = source;
        mView = view;
        mView.setPresenter(this);
    }


    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void login(String userName, String password) {
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
            ToastUtil.showApp("账号或密码为空！");
            return;
        }

        mSource.login(userName, password, new HttpContext.Response<RegisterResponse>() {
            @Override
            public void success(RegisterResponse result) {
                int errorCode = result.getErrorCode();
                if (errorCode == 0) {
                    mView.loginSuccess(result);
                } else {
                    error(result.getErrorMsg());
                }
            }

            @Override
            public void start() {
                super.start();
                mView.showLoading();
            }

            @Override
            public void stop() {
                super.stop();
                mView.hideLoading();
            }

            @Override
            public void error(String error) {
                super.error(error);
                mView.loginFail(error);
            }
        });
    }

    @Override
    public void register(String userName, String password, String repassword) {
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
            ToastUtil.showApp("账号或密码为空！");
            return;
        }
        userName = Tools.urlEncode(userName);

        mSource.register(userName, password, repassword, new HttpContext.Response<RegisterResponse>() {
            @Override
            public void success(RegisterResponse result) {
                int errorCode = result.getErrorCode();
                if (errorCode == 0) {
                    mView.registerSuccess(result);
                } else {
                    error(result.getErrorMsg());
                }
            }

            @Override
            public void start() {
                super.start();
                mView.showLoading();
            }

            @Override
            public void stop() {
                super.stop();
                mView.hideLoading();
            }

            @Override
            public void error(String error) {
                super.error(error);
                mView.registerFail(error);
            }
        });

    }
}
