package com.example.myapp.myapp.component.life.viewholder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.example.myapp.R;
import com.example.myapp.myapp.component.life.entity.JokeBean;
import com.example.myapp.myapp.di.glide.GlideContext;
import com.example.myapp.myapp.ui.view.CircleImageView;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.XPopupImageLoader;

import java.io.File;

public class GifRVHolder extends BaseViewHolder implements View.OnClickListener {

    private CircleImageView icon;
    private CircleImageView icon_comment;
    private LinearLayout commnentsRoot;
    private TextView userName;
    private TextView content;
    private TextView tv_up;
    private TextView tv_down;
    private TextView tv_time;
    private TextView tv_commenter_name;
    private TextView tv_commenter_text;
    private ImageView img_content;
    private View img_default;


    public GifRVHolder(@NonNull View itemView, Context context) {
        super(itemView, context);
    }

    public static int getLayout() {
        return R.layout.item_joke_gif;
    }

    @Override
    public void initView() {
        icon = itemView.findViewById(R.id.icon);
        userName = itemView.findViewById(R.id.tv_username);
        content = itemView.findViewById(R.id.tv_content);
        tv_up = itemView.findViewById(R.id.tv_up);
        tv_down = itemView.findViewById(R.id.tv_down);
        tv_time = itemView.findViewById(R.id.tv_time);
        icon_comment = itemView.findViewById(R.id.icon_comment);
        commnentsRoot = itemView.findViewById(R.id.ll_wonderful_commnets);
        tv_commenter_name = itemView.findViewById(R.id.tv_commenter_name);
        tv_commenter_text = itemView.findViewById(R.id.tv_commenter_text);
        img_content = itemView.findViewById(R.id.img_content);
        img_default = itemView.findViewById(R.id.img_default);
        img_content.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    private String imgUrl;

    public void setData(JokeBean.DataBean dataBean) {
        if (!TextUtils.isEmpty(dataBean.header)) {
            GlideContext.loadCommon(mContext, dataBean.header, icon, R.mipmap.icon_head2);
        }
        if (!TextUtils.isEmpty(dataBean.gif)) {
            imgUrl = dataBean.gif;
//            GlideContext.loadCommon(mContext, dataBean.image, img_content);
//            Glide.with(mContext).load(dataBean.gif).asBitmap().listener(new RequestListener<String, Bitmap>() {
//                @Override
//                public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
//                    img_content.setVisibility(View.GONE);
//                    img_default.setVisibility(View.GONE);
//                    return false;
//                }
//
//                @Override
//                public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                    img_content.setVisibility(View.VISIBLE);
//                    img_default.setVisibility(View.GONE);
//                    return false;
//                }
//
//
//            }).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img_content);

            Glide.with(mContext).load(dataBean.gif).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    img_content.setVisibility(View.GONE);
                    img_default.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    img_content.setVisibility(View.VISIBLE);
                    img_default.setVisibility(View.GONE);
                    return false;
                }
            }).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img_content);

        }
        if (!TextUtils.isEmpty(dataBean.username)) {
            userName.setText(dataBean.username);
        }
        if (!TextUtils.isEmpty(dataBean.text)) {
            content.setText(dataBean.text);
        } else {
            content.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(dataBean.up)) {
            tv_up.setText(dataBean.up);
        }
        if (!TextUtils.isEmpty(dataBean.down)) {
            tv_down.setText(dataBean.down);
        }
        if (!TextUtils.isEmpty(dataBean.passtime)) {
            tv_time.setText(dataBean.passtime);
        }
        if (!TextUtils.isEmpty(dataBean.top_commentsContent)) {
            commnentsRoot.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(dataBean.top_commentsHeader)) {
                GlideContext.loadCommon(mContext, dataBean.top_commentsHeader, icon_comment, R.mipmap.icon_head2);
            }
            if (!TextUtils.isEmpty(dataBean.top_commentsName)) {
                tv_commenter_name.setText(dataBean.top_commentsName + "：");
            }
            if (!TextUtils.isEmpty(dataBean.top_commentsContent)) {
                tv_commenter_text.setText(dataBean.top_commentsContent);
            }

        } else {
            commnentsRoot.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_content:
                if (TextUtils.isEmpty(imgUrl)) {
                    return;
                }
//                new XPopup.Builder(mContext)
//                        .asImageViewer(img_content, imgUrl, true, -1, -1, 50, false, new ImageLoader())
//                        .show();

                new XPopup.Builder(mContext)
                        .asImageViewer(img_content, imgUrl, new ImageLoader())
                        .show();
                break;
        }
    }

    public class ImageLoader implements XPopupImageLoader {
        @Override
        public void loadImage(int position, @NonNull Object url, @NonNull ImageView imageView) {
            //必须指定Target.SIZE_ORIGINAL，否则无法拿到原图，就无法享用天衣无缝的动画
            Glide.with(mContext).load(url).into(imageView);

        }

        @Override
        public File getImageFile(@NonNull Context context, @NonNull Object uri) {

            return null;
        }
    }
}
