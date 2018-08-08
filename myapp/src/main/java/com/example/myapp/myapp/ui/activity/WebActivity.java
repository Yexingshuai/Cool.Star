package com.example.myapp.myapp.ui.activity;

import android.graphics.Color;
import android.graphics.Rect;
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
import android.widget.Toast;

import com.example.myapp.R;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.SocializeUtils;

import java.util.ArrayList;

/**
 * Created by yexing on 2018/3/30.
 */

public class WebActivity extends AppCompatActivity {

    private TextView tv_web_title;
    private WebView web;
    private ProgressBar pg_web;
    private String title;
    private int[] imageResource = {R.drawable.weixin2, R.drawable.qq, R.drawable.umeng_socialize_sina, R.drawable.umeng_socialize_fav, R.drawable.umeng_socialize_qzone, R.drawable.umeng_socialize_evernote};
    private int[] des = {R.string.share_wechat, R.string.share_qq, R.string.share_sina, R.string.share_wechat_fav, R.string.share_qzone, R.string.share_evernote};
    public ArrayList<SnsPlatform> platforms = new ArrayList<SnsPlatform>();
    private String webUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        init();
        initData();

    }

    private void share(int index) {
        UMWeb web = new UMWeb(webUrl);
        web.setTitle("快来学习啦！");
        web.setThumb(new UMImage(this, R.mipmap.meizi));  //分享图片
        web.setDescription(title);
        new ShareAction(WebActivity.this).withMedia(web)
                .setPlatform(platforms.get(index).mPlatform)
                .setCallback(shareListener).share();
    }


    private UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            Toast.makeText(WebActivity.this, "准备分享!", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(WebActivity.this, "成功了", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(WebActivity.this, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(WebActivity.this, "取消了", Toast.LENGTH_LONG).show();

        }
    };

    private void initData() {
        platforms.add(SHARE_MEDIA.WEIXIN.toSnsPlatform());//微信
        platforms.add(SHARE_MEDIA.QQ.toSnsPlatform());  //qq
        platforms.add(SHARE_MEDIA.SINA.toSnsPlatform()); //新浪
        platforms.add(SHARE_MEDIA.WEIXIN_FAVORITE.toSnsPlatform());//微信收藏
        platforms.add(SHARE_MEDIA.QZONE.toSnsPlatform());
        platforms.add(SHARE_MEDIA.EVERNOTE.toSnsPlatform());

    }

    private void init() {
        Bundle extras = getIntent().getExtras();
        webUrl = (String) extras.getString("webUrl");
        title = (String) extras.getString("title");
        ImageView iv_back = findViewById(R.id.iv_back);
        BoomMenuButton bmb = findViewById(R.id.bmb);
        for (int i = 0; i < bmb.getButtonPlaceEnum().buttonNumber(); i++)
            bmb.addBuilder(new TextInsideCircleButton.Builder()
                    .normalImageRes(imageResource[i])
                    .normalTextRes(des[i])
                    .pieceColor(Color.WHITE)
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            // When the boom-button corresponding this builder is clicked.
                            share(index);
                        }
                    })
            );
        bmb.setDraggable(true);

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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();  //防止内存泄露
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
