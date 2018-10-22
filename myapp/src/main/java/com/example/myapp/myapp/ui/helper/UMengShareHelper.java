package com.example.myapp.myapp.ui.helper;

import android.app.Activity;
import android.content.Context;

import com.example.myapp.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;

import java.util.ArrayList;

/**
 * Created by yexing on 2018/8/15.
 * <p>
 * 友盟分享
 * </p>
 */

public class UMengShareHelper {

    private static UMengShareHelper shareHelper;
    private ArrayList<SnsPlatform> platforms;
    private Context mCtx;
    private ShareCallbackListener listener;


    private UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable e) {
            listener.shareError(e.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {

        }
    };


    public static UMengShareHelper getInstance() {
        if (shareHelper == null) {
            shareHelper = new UMengShareHelper();
        }
        return shareHelper;
    }


    public UMengShareHelper initialize(Context context) {
        mCtx = context;
        configShareInfo();
        return this;
    }

    /**
     * 配置分享信息
     */
    private void configShareInfo() {
        platforms = new ArrayList<SnsPlatform>();
        if (platforms.size() == 0) {
            platforms.add(SHARE_MEDIA.WEIXIN.toSnsPlatform());//微信
            platforms.add(SHARE_MEDIA.QQ.toSnsPlatform());  //qq
            platforms.add(SHARE_MEDIA.SINA.toSnsPlatform()); //新浪
            platforms.add(SHARE_MEDIA.WEIXIN_FAVORITE.toSnsPlatform());//微信收藏
            platforms.add(SHARE_MEDIA.QZONE.toSnsPlatform());
            platforms.add(SHARE_MEDIA.EVERNOTE.toSnsPlatform());
        }
    }

    /**
     * 分享网址
     *
     * @param index 分享平台索引
     * @param title 分享标题
     * @param url   分享链接
     */
    public UMengShareHelper shareWithWeb(int index, String title, String url) {
        UMWeb web = new UMWeb(url);
        web.setTitle("Cool.Star");
        web.setThumb(new UMImage(mCtx, R.mipmap.meizi));  //分享图片
        web.setDescription(title);
        new ShareAction((Activity) mCtx).withMedia(web)
                .setPlatform(platforms.get(index).mPlatform)
                .setCallback(shareListener).share();
        return shareHelper;
    }

    /**
     * 释放内存
     */
    public static void release() {
        shareHelper = null;
    }

    public void setShareCallbackListener(ShareCallbackListener listener) {
        this.listener = listener;
    }


    public interface ShareCallbackListener {
        void shareError(String errorMsg);
    }
}
