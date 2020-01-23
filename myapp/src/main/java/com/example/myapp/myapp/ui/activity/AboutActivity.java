package com.example.myapp.myapp.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.myapp.R;
import com.example.myapp.myapp.base.BaseActivity;
import com.example.myapp.myapp.ui.helper.UiHelper;
import com.example.myapp.myapp.utils.PermissonUtil;
import com.example.myapp.myapp.utils.ToastUtil;
import com.example.myapp.myapp.utils.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class AboutActivity extends BaseActivity {


    private ImageView mHeadView;
    private Toolbar mToolBar;
    private String gitHubUrl = "https://github.com/Yexingshuai/Cool.Star";
    private String imgUrl = "http://guolin.tech/api/bing_pic";
    private Button mWebHome;
    private Button mFeedBack;
    private ImageView iv_download;

    @Override
    public int inflateContentView() {
        return R.layout.activity_about;
    }


    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        showExplodeActivityTransition();
        mHeadView = getView(R.id.iv);
        mToolBar = getView(R.id.toolbar);
        mWebHome = getView(R.id.btn_web_home);
        mFeedBack = getView(R.id.btn_feedback);
        iv_download = getView(R.id.iv_download);
        setCommonClickListener(iv_download);
        setCommonClickListener(mWebHome);
        setCommonClickListener(mFeedBack);
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationIcon(R.mipmap.icon_back);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @Override
    protected void initData() {
        OkGo.get(imgUrl)
                .tag(this)
                .cacheMode(CacheMode.DEFAULT)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Glide.with(AboutActivity.this).load(s).into(mHeadView);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                    }
                });

    }

    private void saveToGallery(Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "CoolStar");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            ToastUtil.showApp("图片保存在" + appDir.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(this.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + appDir.getAbsolutePath())));
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
            case R.id.iv_download:
                PermissonUtil.requestPermisson(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, "存储空间", new PermissonUtil.PermissonListener() {
                    @Override
                    public void onPermissonGranted() {
                        saveToGallery(Utils.convertViewToBitmap(mHeadView));
                    }

                    @Override
                    public void onPermissonDenied() {

                    }
                });
                break;
            default:
                break;
        }
    }

    private void feedBack() {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "YeXingFreedom@163.com", null));
        intent.putExtra(Intent.EXTRA_EMAIL, "");
        intent.putExtra(Intent.EXTRA_SUBJECT, "反馈");
        startActivity(Intent.createChooser(intent, "反馈"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelAll();
    }


}
