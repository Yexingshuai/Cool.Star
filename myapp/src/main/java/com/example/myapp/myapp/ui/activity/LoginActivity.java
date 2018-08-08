package com.example.myapp.myapp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseActivity;

/**
 * Created by YEXING on 2018/8/7.
 */

public class LoginActivity extends BaseActivity {

    private EditText mAccountEditext;  //账号
    private EditText mPasswordEdittext;//密码
    private ImageView mDelAccount;
    private ImageView mDelPsd;
    private Button mLogin;  //登录
    private Button mSignIn; //注册

    @Override
    public int inflateContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        mAccountEditext = getView(R.id.et_account);
        mAccountEditext.addTextChangedListener(new MyAccountWatcher());
        mPasswordEdittext = getView(R.id.et_password);
        mPasswordEdittext.addTextChangedListener(new MyPasswordWatcher());
        mDelAccount = getView(R.id.iv_account_delete);
        setCommonClickListener(mDelAccount);
        mDelPsd = getView(R.id.iv_password_delete);
        setCommonClickListener(mDelPsd);
        mLogin = getView(R.id.bt_login);
        setCommonClickListener(mLogin);
        mSignIn = getView(R.id.bt_sign_in);
        setCommonClickListener(mSignIn);


    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean isNeedToBeSubscriber() {
        return false;
    }

    @Override
    protected void onClickImpl(View view) {
        switch (view.getId()) {
            case R.id.iv_account_delete:
                mAccountEditext.setText("");
                break;
            case R.id.iv_password_delete:
                mPasswordEdittext.setText("");
                break;
            case R.id.bt_login:
                break;
            case R.id.bt_sign_in:
                break;
        }
    }

    class MyAccountWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 0) {
                mDelAccount.setVisibility(View.VISIBLE);
            } else {
                mDelAccount.setVisibility(View.INVISIBLE);
            }
        }
    }

    class MyPasswordWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 0) {
                mDelPsd.setVisibility(View.VISIBLE);
            } else {
                mDelPsd.setVisibility(View.INVISIBLE);
            }
        }
    }

}
