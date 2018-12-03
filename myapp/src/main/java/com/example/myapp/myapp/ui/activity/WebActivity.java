package com.example.myapp.myapp.ui.activity;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseActivity;
import com.example.myapp.myapp.common.AppFlag;
import com.example.myapp.myapp.ui.helper.GuidanceHelper;
import com.example.myapp.myapp.ui.helper.UMengShareHelper;
import com.example.myapp.myapp.utils.PermissonUtil;
import com.example.myapp.myapp.utils.ToastUtil;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.umeng.socialize.UMShareAPI;

/**
 * Created by yexing on 2018/3/30.
 */

public class WebActivity extends BaseActivity {


    private WebView mWebView;
    private ProgressBar pg_web;
    private String title;
    private int[] imageResource = {R.drawable.weixin2, R.drawable.qq, R.drawable.umeng_socialize_sina, R.drawable.umeng_socialize_fav, R.drawable.umeng_socialize_qzone, R.drawable.umeng_socialize_evernote};
    private int[] des = {R.string.share_wechat, R.string.share_qq, R.string.share_sina, R.string.share_wechat_fav, R.string.share_qzone, R.string.share_evernote};
    private String webUrl;
    public static final String TITLE = "title";
    public static final String WEBURL = "weburl";
    private BoomMenuButton bmb;


    @Override
    public int inflateContentView() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        pg_web = findViewById(R.id.pg_web);
        mWebView = findViewById(R.id.web);
        bmb = findViewById(R.id.bmb);
    }

    @Override
    protected void initData() {
        //执行引导动画
        GuidanceHelper.guide(this, bmb, "bmb", "点击我分享给朋友哦！\n 还可以拖动我！");
        Bundle extras = getIntent().getExtras();
        webUrl = (String) extras.getString(WEBURL);
        for (int i = 0; i < bmb.getButtonPlaceEnum().buttonNumber(); i++)
            bmb.addBuilder(new TextInsideCircleButton.Builder()
                    .normalImageRes(imageResource[i])
                    .normalTextRes(des[i])
                    .pieceColor(Color.WHITE)
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            checkPermisson(index);
                        }
                    })
            );

        bmb.setDraggable(true);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setBuiltInZoomControls(true);// 显示缩放按钮
        webSettings.setUseWideViewPort(true);// 支持双击缩放功能
        webSettings.setJavaScriptEnabled(true);// 支持JavaScript
//
//        webSettings.setSupportMultipleWindows(true);
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        webSettings.setLoadWithOverviewMode(true);
//        webSettings.setLoadsImagesAutomatically(true);
//        webSettings.setSupportZoom(true);
//        webSettings.setDefaultTextEncodingName("utf-8");
//        webSettings.setAllowContentAccess(true);

//        WebSettings webSettings = mWebView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setAllowContentAccess(true);
//        webSettings.setAppCacheEnabled(false);
//        webSettings.setBuiltInZoomControls(false);
//        webSettings.setUseWideViewPort(true);
//        webSettings.setLoadWithOverviewMode(true);
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        mWebView.setWebViewClient(new WebViewClient() {

            /**
             * 多页面在同一个WebView中打开，就是不新建activity或者调用系统浏览器打开
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });


        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
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
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String tit) {
                super.onReceivedTitle(view, tit);
                title = tit;
                setActionTitle(tit);
            }

            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                WebView.HitTestResult result = view.getHitTestResult();
                String data = result.getExtra();
                view.loadUrl(data);
                return true;
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
            }

            @Override
            public void onGeolocationPermissionsHidePrompt() {
                super.onGeolocationPermissionsHidePrompt();
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);//注意个函数，第二个参数就是是否同意定位权限，第三个是是否希望内核记住
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }


        });


        mWebView.loadUrl(webUrl);
    }


    /**
     * 检查权限，否则qq分享失败
     *
     * @return
     */
    private void checkPermisson(final int index) {

        PermissonUtil.requestPermisson(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, "存储空间", new PermissonUtil.PermissonListener() {
            @Override
            public void onPermissonGranted() {
                UMengShareHelper.getInstance().initialize(WebActivity.this).shareWithWeb(index, title, webUrl).setShareCallbackListener(new UMengShareHelper.ShareCallbackListener() {
                    @Override
                    public void shareError(String errorMsg) {
                        ToastUtil.showApp(errorMsg);
                    }
                });
            }

            @Override
            public void onPermissonDenied() {

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
        AppFlag.isFromNews = false;
        UMShareAPI.get(this).release();  //防止内存泄露
        UMengShareHelper.release();
        super.onDestroy();
//        System.exit(0);
    }


    //设置返回键动作（防止按返回键直接退出程序)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO 自动生成的方法存根
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack() && !AppFlag.isFromNews) {//当webview不是处于第一页面时，返回上一个页面
                mWebView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
