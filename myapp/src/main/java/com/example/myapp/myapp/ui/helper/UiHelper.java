package com.example.myapp.myapp.ui.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapp.myapp.component.movie.detail.FilmDetailActivity;
import com.example.myapp.myapp.ui.activity.WebActivity;
import com.example.myapp.myapp.utils.MyAnimationUtils;

/**
 * Created by yexing on 2018/8/14.
 * <p>
 * Activity跳转工具类
 * </p>
 */

public class UiHelper {


    public UiHelper() {
        throw new UnsupportedOperationException("UiHelper不许被实例化！");
    }

    /**
     * 普通界面跳转
     *
     * @param activity
     * @param clazz
     */
    public static void skipToOtherActivity(Activity activity, Class<? extends Activity> clazz) {
        Intent intent = new Intent();
        intent.setClass(activity, clazz);
        activity.startActivity(intent);
        activity.finish();
    }


    /**
     * 普通界面跳转不需要finsh
     *
     * @param context
     * @param clazz
     */
    public static void skipActivityNofinish(Context context, Class<? extends Activity> clazz) {
        Intent intent = new Intent();
        intent.setClass(context, clazz);
        context.startActivity(intent);
    }


    /**
     * 跳转WebActivity
     *
     * @param context
     * @param title    Web标题
     * @param url      Web链接
     */
    public static void skipWebActivity(Context context, String title, String url) {
        Intent intent = new Intent();
        intent.setClass(context, WebActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(WebActivity.TITLE, title);
        bundle.putString(WebActivity.WEBURL, url);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * 跳转FilmDetailActivity
     *
     * @param context
     * @param view     元素共享动画view
     * @param id       电影名称id
     */
    public static void skipFilmActivity(Context context, View view, String id) {
        Intent intent = new Intent();
        Bundle bannerBundle = MyAnimationUtils.makeSceneTransition((Activity) context, view, "fileIv");
        intent.setClass(context, FilmDetailActivity.class);
        intent.putExtra(FilmDetailActivity.MOVIEID, id);
        context.startActivity(intent, bannerBundle);
    }
}
