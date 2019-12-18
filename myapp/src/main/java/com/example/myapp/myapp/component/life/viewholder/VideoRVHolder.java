package com.example.myapp.myapp.component.life.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.myapp.R;
import com.example.myapp.myapp.component.life.entity.JokeBean;
import com.example.myapp.myapp.di.glide.GlideContext;
import com.example.myapp.myapp.ui.view.CircleImageView;

import cn.jzvd.JzvdStd;


public class VideoRVHolder extends BaseViewHolder {

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
    private JzvdStd jzvdStd;

    public VideoRVHolder(@NonNull View itemView, Context context) {
        super(itemView, context);
    }

    public static int getLayout() {
        return R.layout.item_joke_video;
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
        jzvdStd = itemView.findViewById(R.id.jz_video);
    }

    @Override
    public void initData() {

    }

    public void setData(JokeBean.DataBean dataBean) {
        if (!TextUtils.isEmpty(dataBean.header)) {
            GlideContext.loadCommon(mContext, dataBean.header, icon, R.mipmap.icon_head2);
        }
        if (!TextUtils.isEmpty(dataBean.thumbnail)) {
//            Glide.with(mContext).load(dataBean.thumbnail).asBitmap().listener(new RequestListener() {
//                @Override
//                public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
//                    img_content.setVisibility(View.GONE);
//                    img_default.setVisibility(View.GONE);
//                    return false;
//                }
//
//                @Override
//                public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
//                    img_content.setVisibility(View.VISIBLE);
//                    img_default.setVisibility(View.GONE);
//                    return false;
//                }
//
//            }).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img_content);
            if (!TextUtils.isEmpty(dataBean.video)) {
                jzvdStd.setUp(dataBean.video, dataBean.text);
                GlideContext.loadCommon(mContext, dataBean.thumbnail, jzvdStd.thumbImageView);
                jzvdStd.positionInList=getAdapterPosition();
            }

        }
        if (!TextUtils.isEmpty(dataBean.name)) {
            userName.setText(dataBean.name);
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
}
