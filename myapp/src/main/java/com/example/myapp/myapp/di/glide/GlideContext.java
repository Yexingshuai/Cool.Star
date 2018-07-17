package com.example.myapp.myapp.di.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


/**
 * @author pd_liu on 2018/3/8.
 *         <p>
 *         图片加载框架
 *         </p>
 *         <p>
 *         fallback Drawable 在请求的url/model为 null 时展示。
 *         设计 fallback Drawable 的主要目的是允许用户指示 null 是否为可接受的正常情况。
 *         例如，一个 null 的个人资料 url 可能暗示这个用户没有设置头像，因此应该使用默认头像。然而，null 也可能表明这个元数据根本就是不合法的，或者取不到。
 *         默认情况下Glide将 null 作为错误处理，所以可以接受 null 的应用应当显式地设置一个 fallback Drawable 。
 *         </p>
 *         <p>
 *         Document: https://muyangmin.github.io/glide-docs-cn/doc/transformations.html
 *         </p>
 *         <p>
 *         TODO:进一步完善中
 *         与项目原有的glide 冲突
 *         </p>
 */

public class GlideContext {

    /**
     * 常用的加载图片
     *
     * @param context   context
     * @param url       image url
     * @param imageView into view.
     */
    public static void loadCommon(Context context, String url, ImageView imageView) {
//        GlideApp.with(context)
//                .load(url)
//                .placeholder(new ColorDrawable(Color.GRAY))//占位符
//                .error(new ColorDrawable(Color.RED))//错误符
//                .fallback(new ColorDrawable(Color.BLUE))//后备回调符
//                .into(imageView);
        Glide.with(context)
                .load(url)
                .into(imageView);
    }

    public static void loadCommon(Context context, String url, ImageView imageView, int placeholder) {
        Glide.with(context)
                .load(url)
                .placeholder(placeholder)
                .into(imageView);
    }

    /**
     * 常用的加载图片(图片地址为完整地址)
     *
     * @param context   context
     * @param url       image url
     * @param imageView into view.
     */
    public static void loadWithCompleteUrl(Context context, String url, ImageView imageView) {
//        GlideApp.with(context)
//                .load(url)
//                .placeholder(new ColorDrawable(Color.GRAY))//占位符
//                .error(new ColorDrawable(Color.RED))//错误符
//                .fallback(new ColorDrawable(Color.BLUE))//后备回调符
//                .into(imageView);
    }

    /**
     * 加载Gif Image
     *
     * @param context   context
     * @param url       git url
     * @param imageView into view
     */
    public static void loadGif(Context context, String url, ImageView imageView) {
//        GlideApp.with(context)
//                .asGif()
//                .load(url)
//                .placeholder(new ColorDrawable(Color.GRAY))//占位符
//                .error(new ColorDrawable(Color.RED))//错误符
//                .fallback(new ColorDrawable(Color.BLUE))//后备回调符
//                .into(imageView);
        Glide.with(context)
                .load(url)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    public static void loadGif(Context context, int resId, ImageView imageView) {
//        GlideApp.with(context)
//                .asGif()
//                .load(resId)
//                .placeholder(new ColorDrawable(Color.GRAY))//占位符
//                .error(new ColorDrawable(Color.RED))//错误符
//                .fallback(new ColorDrawable(Color.BLUE))//后备回调符
//                .into(imageView);
        Glide.with(context)
                .load(resId)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }
}
