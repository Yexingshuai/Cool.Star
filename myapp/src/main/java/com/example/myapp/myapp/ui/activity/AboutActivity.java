package com.example.myapp.myapp.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseActivity;
import com.example.myapp.myapp.di.glide.GlideContext;
import com.example.myapp.myapp.ui.helper.UiHelper;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Response;

public class AboutActivity extends BaseActivity {


    private ImageView mHeadView;
    private Toolbar mToolBar;
    public static final String gitHubUrl = "https://github.com/Yexingshuai/Cool.Star";
    private Button mWebHome;
    private Button mFeedBack;

    @Override
    public int inflateContentView() {
        return R.layout.activity_about;
    }


    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        mHeadView = getView(R.id.iv);
        mToolBar = getView(R.id.toolbar);
        mWebHome = getView(R.id.btn_web_home);
        setCommonClickListener(mWebHome);
        mFeedBack = getView(R.id.btn_feedback);
        setCommonClickListener(mFeedBack);
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationIcon(R.mipmap.icon_back);

    }

    @Override
    protected void initData() {
        OkGo.get("http://guolin.tech/api/bing_pic")
                .tag(this)
                .cacheMode(CacheMode.DEFAULT)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        GlideContext.loadCommon(AboutActivity.this, s, mHeadView);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                    }
                });

    }

    @Override
    protected boolean isNeedToBeSubscriber() {
        return false;
    }


    @Override
    protected void onClickImpl(View view) {
        super.onClickImpl(view);
        switch (view.getId()) {
            case R.id.btn_web_home:
                UiHelper.skipWebActivity(AboutActivity.this, "", gitHubUrl);
                break;
            case R.id.btn_feedback:
                feedBack();
                break;
            default:
                break;
        }
    }

    private void feedBack() {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "YeXingFreedom@163.com", null));
        intent.putExtra(Intent.EXTRA_EMAIL, "me@liyuyu.cn");
        intent.putExtra(Intent.EXTRA_SUBJECT, "反馈");
        startActivity(Intent.createChooser(intent, "反馈"));
    }
}
