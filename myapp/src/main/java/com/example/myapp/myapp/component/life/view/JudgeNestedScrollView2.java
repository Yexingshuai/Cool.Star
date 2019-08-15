package com.example.myapp.myapp.component.life.view;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @Created SiberiaDante
 * @Describe：
 * @CreateTime: 2017/12/17
 * @UpDateTime:
 * @Email: 2654828081@qq.com
 * @GitHub: https://github.com/SiberiaDante
 */

public class JudgeNestedScrollView2 extends NestedScrollView {
    private boolean isNeedScroll = true;

    public JudgeNestedScrollView2(Context context) {
        super(context, null);
    }

    public JudgeNestedScrollView2(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public JudgeNestedScrollView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private float mLastXIntercept = 0f;
    private float mLastYIntercept = 0f;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isNeedScroll) { // 滑动距离 < widget列表高度
            boolean intercepted = false;
            float x = ev.getX();
            float y = ev.getY();
            int action = ev.getAction() & MotionEvent.ACTION_MASK;
            switch (action) {
                case MotionEvent.ACTION_DOWN: {
                    intercepted = false;
                    //初始化mActivePointerId
                    super.onInterceptTouchEvent(ev);
                    break;
                }
                case MotionEvent.ACTION_MOVE: {
                    //横坐标位移增量
                    float deltaX = x - mLastXIntercept;
                    //纵坐标位移增量
                    float deltaY = y - mLastYIntercept;
                    if (Math.abs(deltaX) + 10 < Math.abs(deltaY)) {  //因为三星s8手机点击事件滑动过于灵敏，这里加10
                        intercepted = true;
                    } else {
                        intercepted = false;
                    }
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    intercepted = false;
                    break;
                }
            }
            mLastXIntercept = x;
            mLastYIntercept = y;
            return intercepted;
        } else { // 滑动距离 > widget列表高度
            return false;
        }
    }

    /*
    该方法用来处理NestedddScrollView是否拦截滑动事件
     */
    public void setNeedScroll(boolean isNeedScroll) {
        this.isNeedScroll = isNeedScroll;
    }
}
