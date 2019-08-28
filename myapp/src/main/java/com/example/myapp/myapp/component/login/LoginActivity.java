package com.example.myapp.myapp.component.login;

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
import com.example.myapp.myapp.data.bean.RegisterResponse;
import com.example.myapp.myapp.data.source.login.LoginRepository;
import com.example.myapp.myapp.utils.ToastUtil;
import com.github.ybq.android.spinkit.SpinKitView;

/**
 * Created by yexing on 2018/8/7.
 */

public class LoginActivity extends BaseActivity implements LoginContract.View {

    private EditText mAccountEditext;  //账号
    private EditText mPasswordEdittext;//密码
    private ImageView mDelAccount;
    private ImageView mDelPsd;
    private Button mLogin;  //登录
    private Button mSignIn; //注册
    private LoginContract.Presenter mPresenter;
    private SpinKitView kitView;
    private ImageView icon_back;

    @Override
    public int inflateContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        new LoginPresenter(new LoginRepository(), this); //传递引用

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
        kitView = getView(R.id.spin_kit);
        icon_back = getView(R.id.icon_back);
        setCommonClickListener(icon_back);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean isNeedToBeSubscriber() {
        return false;
    }


    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void loginSuccess(RegisterResponse response) {
        ToastUtil.showApp("登录成功!");
        finish();
    }

    @Override
    public void loginFail(String errorMsg) {
        ToastUtil.showApp(errorMsg);
    }

    @Override
    public void registerSuccess(RegisterResponse response) {
        ToastUtil.showApp("注册成功!");
        finish();
    }

    @Override
    public void registerFail(String errorMsg) {
        ToastUtil.showApp(errorMsg);
    }

    @Override
    public void showLoading() {
        kitView.setVisibility(View.VISIBLE);
        mSignIn.setEnabled(false);
        mLogin.setEnabled(false);
    }

    @Override
    public void hideLoading() {
        kitView.setVisibility(View.INVISIBLE);
        mSignIn.setEnabled(true);
        mLogin.setEnabled(true);
    }

    @Override
    protected void onClickImpl(View view) {
        switch (view.getId()) {
            case R.id.iv_account_delete:
                mAccountEditext.setText("");
                break;
            case R.id.iv_password_delete:
                mPasswordEdittext.setText("");
                //可以用键盘和用户做交互:指定输入法窗口中的回车键的功能
//                mPasswordEdittext.setImeOptions(0);
                break;
            case R.id.bt_login:
                String loginAccount = mAccountEditext.getText().toString().trim();
                String loginPassword = mPasswordEdittext.getText().toString().trim();
                mPresenter.login(loginAccount, loginPassword);
                break;
            case R.id.bt_sign_in:
                String account = mAccountEditext.getText().toString().trim();
                String password = mPasswordEdittext.getText().toString().trim();
                mPresenter.register(account, password, password);
                break;
            case R.id.icon_back:
                finish();
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
