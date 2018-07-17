package com.example.myapp.myapp.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.example.myapp.R;


/**
 * 状态布局，封装了4种状态：正在加载、加载失败、加载为空、正常界面
 */

public class StateLayout extends FrameLayout {

    private View loadingView;
    private View failView;
    private View emptyView;
    private View contentView;

    public StateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 不要在构造方法中去做findViewById的事情，这是获取不到View的
     * 当StateLayout被填充器填充结束之后会调用
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        loadingView = findViewById(R.id.loadingView);
        failView = findViewById(R.id.failView);
        emptyView = findViewById(R.id.emptyView);
        showLoadingView();  // 默认显示loadingView
    }

    public void showLoadingView() {
        showView(loadingView);
    }

    public void showFailView() {
        showView(failView);
    }

    public void showEmptyView() {
        showView(emptyView);
    }

    /**
     * 显示正常界面的View
     */
    public void showContentView() {
        showView(contentView);
    }

    /**
     * 显示指定的View，并且隐藏其他的View
     *
     * @param view
     */
    private void showView(View view) {
        // getChildCount()获取到StateLayout的直接的子孩子
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.setVisibility(view == child ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 设置一个正常界面的View，可以是一个View，也可以是一个布局id
     */
    public void setConentView(Object viewOrLayoutId) {
        if (viewOrLayoutId == null) {
            throw new IllegalArgumentException("viewOrLayoutId参数不能为null");
        }

        if (viewOrLayoutId instanceof View) {
            contentView = (View) viewOrLayoutId;
        } else if (viewOrLayoutId instanceof Integer) {
            // 如果是一个布局id
            int layoutId = (int) viewOrLayoutId;
            // 创建View的时候需要上下文的时候尽量用Activity类型的上下文，
            // 不要用Application的上下文，因为这种上下文有时候显示View会有奇怪效果
            contentView = View.inflate(getContext(), layoutId, null);
        } else {
            throw new IllegalArgumentException("viewOrLayoutId参数只能是View或者布局id");
        }

        contentView.setVisibility(GONE);    // 默认显示LoadingView
        addView(contentView);
    }
}
