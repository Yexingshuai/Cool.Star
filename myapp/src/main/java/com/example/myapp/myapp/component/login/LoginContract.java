package com.example.myapp.myapp.component.login;

import com.example.myapp.myapp.base.BasePresenter;
import com.example.myapp.myapp.base.BaseView;
import com.example.myapp.myapp.data.bean.RegisterResponse;

/**
 * Created by yexing on 2018/8/8.
 */

public interface LoginContract {

    interface Presenter extends BasePresenter {
        void login(String userName, String password);

        void register(String userName, String password, String repassword);
    }

    interface View extends BaseView<Presenter> {

        void loginSuccess(RegisterResponse response);

        void loginFail(String errorMsg);

        void registerSuccess(RegisterResponse response);

        void registerFail(String errorMsg);

        void showLoading();

        void hideLoading();
    }

}
