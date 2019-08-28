package com.example.myapp.myapp.component.life.viewholder;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.myapp.component.life.entity.JokeBean;
import com.example.myapp.myapp.di.glide.GlideContext;
import com.example.myapp.myapp.ui.view.CircleImageView;

public class TextRVHolder extends BaseViewHolder implements View.OnClickListener {


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
    private TextView tv_all;
    private int allLinesHeight = -1;
    public static final int MAXLINES = Integer.MAX_VALUE;
    private boolean isExpand;
    private ValueAnimator mValueAnimator;
    private int _5LinesHeight;
    public static final int ANIM_TIME = 200;

    public TextRVHolder(@NonNull View itemView, Context context) {
        super(itemView, context);
    }

    public static int getLayout() {
        return R.layout.item_joke_text;
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
        tv_all = itemView.findViewById(R.id.tv_all);
        tv_all.setOnClickListener(this);

    }

    @Override
    public void initData() {
        allLinesHeight = -1;
        tv_all.setText("显示全部");
        tv_all.setVisibility(View.GONE);
        isExpand=false;
//        content.setMaxLines(3);
//        content.post(new Runnable() {
//            @Override
//            public void run() {
//                int lineCount = content.getLineCount();
//                if (lineCount > 3) {
//                    content.setMaxLines(3);
//                    tv_all.setVisibility(View.VISIBLE);
//                } else {
//                    tv_all.setVisibility(View.GONE);
//                }
//            }
//        });
    }

    public void setData(JokeBean.DataBean dataBean) {
        if (!TextUtils.isEmpty(dataBean.header)) {
            GlideContext.loadCommon(mContext, dataBean.header, icon,R.mipmap.icon_head2);
        }
        if (!TextUtils.isEmpty(dataBean.username)) {
            userName.setText(dataBean.username);
        }
        if (!TextUtils.isEmpty(dataBean.text)) {
            content.setText(dataBean.text);
        } else {
            content.setText("暂无数据");
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
                GlideContext.loadCommon(mContext, dataBean.top_commentsHeader, icon_comment,R.mipmap.icon_head2);
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


    /**
     * 执行展开关闭动画
     */
    private void excuteAnim() {
//        content.measure(0, 0);
        if (allLinesHeight == -1) {
            allLinesHeight = getAllLinesHeight();
            _5LinesHeight = content.getHeight();
        }
        if (isExpand) {
            mValueAnimator = ValueAnimator.ofInt(allLinesHeight, _5LinesHeight);
        } else {
            mValueAnimator = ValueAnimator.ofInt(_5LinesHeight, allLinesHeight);
        }
        isExpand = !isExpand;
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                content.getLayoutParams().height = animatedValue;
                content.requestLayout();
            }
        });
        mValueAnimator.setDuration(ANIM_TIME);
        mValueAnimator.start();
        if (isExpand) {
            tv_all.setText("收起");
            content.setMaxLines(Integer.MAX_VALUE);
        } else {
            tv_all.setText("显示全部");
            content.setMaxLines(MAXLINES);
        }
    }


    public int getAllLinesHeight() {
        content.setMaxLines(Integer.MAX_VALUE);//去除最大行限制
        //创建一个精确的宽度测量规格
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(content.getWidth(), View.MeasureSpec.EXACTLY);
        content.measure(widthMeasureSpec, 0);
        return content.getMeasuredHeight();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_all:
                excuteAnim();
                break;
        }
    }
}
