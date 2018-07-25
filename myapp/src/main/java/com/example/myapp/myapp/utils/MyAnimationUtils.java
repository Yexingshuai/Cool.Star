package com.example.myapp.myapp.utils;

import android.animation.Animator;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.view.ViewAnimationUtils;

/**
 * Created by  on 2018/4/16.
 */

public class MyAnimationUtils {

    /**
     * Activity跳转实现元素共享平移效果
     * @param mCtx  环境
     * @param view  执行view
     * @param sharedElementName  共享动画元素名称
     * @return
     */
    public static Bundle makeSceneTransition(Activity mCtx,View view,String sharedElementName) {
        Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(mCtx, view, sharedElementName).toBundle();
        return bundle;
    }

    /**
     * <p>
     *     点击产生圆形扩散效果。
     * </p>
     * @param view     执行view
     * @param centerX    执行view X轴执行坐标
     * @param centerY      执行view Y轴执行坐标
     * @param startRadius  开始时动画扩散半径
     * @param endRadius      结束时动画扩散半径
     * @return
     */
    public static Animator makeCircularReveal(View view,int centerX,int centerY,float startRadius,float endRadius) {
        Animator animator = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);
        return animator;
    }
}
