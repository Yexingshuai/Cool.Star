package com.example.myapp.myapp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapp.R;

/**
 * Created by yexing on 2018/3/30.
 */

public class WebActivity extends AppCompatActivity {

    private TextView tv_web_title;
    private WebView web;
    private ProgressBar pg_web;
    private String title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        init();

    }

    private void init() {
        Bundle extras = getIntent().getExtras();
        String webUrl = (String) extras.getString("webUrl");
        title = (String) extras.getString("title");
        ImageView iv_back = findViewById(R.id.iv_back);

        tv_web_title = findViewById(R.id.tv_web_title);
        pg_web = findViewById(R.id.pg_web);
        web = findViewById(R.id.web);
        WebSettings settings = web.getSettings();
        settings.setBuiltInZoomControls(true);// 显示缩放按钮
        settings.setUseWideViewPort(true);// 支持双击缩放功能
        settings.setJavaScriptEnabled(true);// 支持JavaScript
        // 监听Webview加载数据完成
        web.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // 当网页加载完成后，回调
//                tv_web_title.setText(title);
                super.onPageFinished(view, url);
            }
        });
        web.loadUrl(webUrl);


        web.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress >= 100) {
                    pg_web.setVisibility(View.GONE);
                } else {
                    if (pg_web.getVisibility() == View.GONE) {
                        pg_web.setVisibility(View.GONE);
                    } else {
                        pg_web.setVisibility(View.VISIBLE);
                    }
                    pg_web.setProgress(newProgress);
                }

            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                tv_web_title.setText(title);
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    //设置返回键动作（防止按返回键直接退出程序)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO 自动生成的方法存根
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (web.canGoBack()) {//当webview不是处于第一页面时，返回上一个页面
                web.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
