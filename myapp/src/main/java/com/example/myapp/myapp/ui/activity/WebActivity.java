package com.example.myapp.myapp.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseActivity;
import com.example.myapp.myapp.ui.helper.UMengShareHelper;
import com.example.myapp.myapp.utils.ToastUtil;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.shareboard.SnsPlatform;

import java.util.ArrayList;

/**
 * Created by yexing on 2018/3/30.
 */

public class WebActivity extends BaseActivity {


    private WebView mWebView;
    private ProgressBar pg_web;
    private String title;
    private int[] imageResource = {R.drawable.weixin2, R.drawable.qq, R.drawable.umeng_socialize_sina, R.drawable.umeng_socialize_fav, R.drawable.umeng_socialize_qzone, R.drawable.umeng_socialize_evernote};
    private int[] des = {R.string.share_wechat, R.string.share_qq, R.string.share_sina, R.string.share_wechat_fav, R.string.share_qzone, R.string.share_evernote};
    public ArrayList<SnsPlatform> platforms = new ArrayList<SnsPlatform>();
    private String webUrl;
    public static final String TITLE = "title";
    public static final String WEBURL = "weburl";


    @Override
    public int inflateContentView() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        pg_web = findViewById(R.id.pg_web);
        mWebView = findViewById(R.id.web);
    }

    @Override
    protected void initData() {
        Bundle extras = getIntent().getExtras();
        webUrl = (String) extras.getString(WEBURL);
        BoomMenuButton bmb = findViewById(R.id.bmb);
        for (int i = 0; i < bmb.getButtonPlaceEnum().buttonNumber(); i++)
            bmb.addBuilder(new TextInsideCircleButton.Builder()
                    .normalImageRes(imageResource[i])
                    .normalTextRes(des[i])
                    .pieceColor(Color.WHITE)
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            UMengShareHelper.getInstance().initialize(WebActivity.this).shareWithWeb(index, title, webUrl).setShareCallbackListener(new UMengShareHelper.ShareCallbackListener() {
                                @Override
                                public void shareError(String errorMsg) {
                                    ToastUtil.showApp(errorMsg);
                                }
                            });

                        }
                    })
            );

        bmb.setDraggable(true);
        WebSettings settings = mWebView.getSettings();
        settings.setBuiltInZoomControls(true);// 显示缩放按钮
        settings.setUseWideViewPort(true);// 支持双击缩放功能
        settings.setJavaScriptEnabled(true);// 支持JavaScript
        // 监听Webview加载数据完成
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // 当网页加载完成后，回调
                super.onPageFinished(view, url);
            }
        });
        mWebView.loadUrl(webUrl);


        mWebView.setWebChromeClient(new WebChromeClient() {
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
            public void onReceivedTitle(WebView view, String tit) {
                super.onReceivedTitle(view, tit);
                title = tit;
                setActionTitle(tit);
            }
        });
    }

    @Override
    protected boolean isNeedToBeSubscriber() {
        return false;
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            ViewParent parent = mWebView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mWebView);
            }
            mWebView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            mWebView.getSettings().setJavaScriptEnabled(false);
            mWebView.clearHistory();
            mWebView.clearView();
            mWebView.removeAllViews();
            mWebView.destroy();
        }

        UMShareAPI.get(this).release();  //防止内存泄露
        UMengShareHelper.release();
        super.onDestroy();
    }


    //设置返回键动作（防止按返回键直接退出程序)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO 自动生成的方法存根
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {//当webview不是处于第一页面时，返回上一个页面
                mWebView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
